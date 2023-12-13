package com.vmg.ibo.core.service.user;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.constant.AuthConstant;
import com.vmg.ibo.core.constant.MailMessageConstant;
import com.vmg.ibo.core.constant.UserConstant;
import com.vmg.ibo.core.model.customer.PersonalCustomer;
import com.vmg.ibo.core.model.dto.ChangePasswordRequest;
import com.vmg.ibo.core.model.dto.ProfileResponse;
import com.vmg.ibo.core.model.dto.UserDTO;
import com.vmg.ibo.core.model.dto.filter.UserFilter;
import com.vmg.ibo.core.model.entity.Permission;
import com.vmg.ibo.core.model.entity.Role;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.model.entity.UserDetail;
import com.vmg.ibo.core.repository.IPermissionRepository;
import com.vmg.ibo.core.repository.IRoleRepository;
import com.vmg.ibo.core.repository.IUserRepository;
import com.vmg.ibo.core.service.mail.IMailService;
import com.vmg.ibo.core.service.userDetail.IUserDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService extends BaseService implements IUserService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IPermissionRepository permissionRepository;
    private final IMailService mailService;

    private IUserDetailService userDetailService;

    @Value("${cms.url}")
    private String cmsUrl;

    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder, IPermissionRepository permissionRepository, IMailService mailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionRepository = permissionRepository;
        this.mailService = mailService;
    }

    @Override
    public User registerUser(String email) {
        User user = new User();
        user.setEmail(email);
        String password = AuthConstant.DEFAULT_PASSWORD.getValue();
        user.setStatus((Integer) UserConstant.ENABLE.getValue());
        user.setChannelId((Integer) UserConstant.CHANNEL_ADMIN.getValue());
        user.setChannelName((String) UserConstant.CHANNEL_ADMIN_STR.getValue());
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedBy("me");
        user.setCreatedByUserId(1L);
        user.setCreatedAt(new Date());
        user.setIsResetPass(true);
        user = userRepository.save(user);
        mailService.sendFromSystem(message -> message.to(email)
                .subject(MailMessageConstant.CREATE_ACCOUNT_SUBJECT)
                .text(String.format(MailMessageConstant.CREATE_ACCOUNT_TEXT, email, password, cmsUrl))
                .build());
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Page<UserDTO> findAllUsers(UserFilter userFilter) {
        String username = userFilter.getContactName();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageable = handlePaging(userFilter, sort);
        return userRepository.findAllUser(username, pageable).map(this::mapToDTO);
    }

    @Override
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "user.error.notFound");
        }
        UserDTO userDTO = mapToDTO(user);
        if (userDTO != null) {
            List<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
            userDTO.setRoleIds(roleIds);
        }
        return userDTO;
    }

    @Override
    @Transactional
    public User create(UserDTO userDTO) {
        writeLog("CREATE_USER", "Thêm mới người dùng hệ thống", userDTO, null, null, null, "SYSTEM", this.getClass());
        String password = AuthConstant.DEFAULT_PASSWORD.getValue();
        User user = mapToEntity(userDTO);
        List<Long> userIds = userDTO.getRoleIds();
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(userIds));
        User currentUser = getCurrentUser();
        user.setRoles(roles);
        user.setStatus((Integer) UserConstant.ENABLE.getValue());
        user.setChannelId((Integer) UserConstant.CHANNEL_ADMIN.getValue());
        user.setChannelName((String) UserConstant.CHANNEL_ADMIN_STR.getValue());
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedBy(currentUser.getUsername());
        user.setCreatedByUserId(currentUser.getId());
        user.setCreatedAt(new Date());
        user.setIsResetPass(true);
        user = userRepository.save(user);
        mailService.sendFromSystem(message -> message.to(userDTO.getEmail())
                .subject(MailMessageConstant.CREATE_ACCOUNT_SUBJECT)
                .text(String.format(MailMessageConstant.CREATE_ACCOUNT_TEXT, userDTO.getUsername(), password, cmsUrl))
                .build());
        return user;
    }

    @Override
    public User createPersonalCustomer(PersonalCustomer personalCustomer) {
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());

        User user = new User();
        user.setName(personalCustomer.getName());
        user.setPhone(personalCustomer.getPhone());
        user.setId(idUser);
        user.setCreatedAt(new Date());
        user = userRepository.save(user);
        UserDetail userDetail = new UserDetail();
        userDetail.setAddress(personalCustomer.getAddress());
        userDetail.setDescription(personalCustomer.getDescription());
        userDetail.setIdUser(user.getId());
        userDetail.setCINumber(personalCustomer.getCINumber());
        userDetailService.create(userDetail);
        return user;
    }

    @Override
    @Transactional
    public User update(Long id, UserDTO userDTO) {
        writeLog("UPDATE_USER", "Chỉnh sửa người dùng hệ thống", userDTO, null, null, null, "SYSTEM", this.getClass());
        User user = mapToEntity(userDTO);
        User oldUser = userRepository.findById(id).orElse(null);
        if (oldUser == null) {
            throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "user.error.notFound");
        }
        User currentUser = getCurrentUser();
        List<Long> roleIds = userDTO.getRoleIds();
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(roleIds));
        oldUser.setRoles(roles);
        oldUser.setUsername(user.getUsername());
        oldUser.setStatus(user.getStatus());
        oldUser.setUsername(user.getUsername());
        oldUser.setUpdatedBy(currentUser.getUsername());
        oldUser.setUpdatedByUserId(currentUser.getId());
        oldUser.setUpdatedAt(new Date());
        return userRepository.save(oldUser);
    }

    @Override
    @Transactional
    public List<User> deleteByIds(UserDTO userDTO) {
        writeLog("DELETE_USER", "Xóa người dùng hệ thống", userDTO, null, null, null, "SYSTEM", this.getClass());
        List<Long> ids = reformatIdsFromRequest(userDTO);
        List<User> oldUsers = userRepository.findAllById(ids);
        if (oldUsers.isEmpty()) {
            throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "user.error.notFound");
        }
        userRepository.deleteAllByIdInBatch(ids);
        return oldUsers;
    }

    @Override
    @Transactional
    public List<User> changeStatusByIds(UserDTO userDTO) {
        writeLog("CHANGE_STATUS_USER", "Cập nhật trạng thái người dùng hệ thống", userDTO, null, null, null, "SYSTEM", this.getClass());
        List<Long> ids = reformatIdsFromRequest(userDTO);
        List<User> oldUsers = userRepository.findAllById(ids);
        if (oldUsers.isEmpty()) {
            throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "user.error.notFound");
        }
        oldUsers.forEach(user -> user.setStatus((Integer) (user.getStatus().equals(UserConstant.ENABLE.getValue()) ? UserConstant.LOCKED.getValue() : UserConstant.ENABLE.getValue())));
        return userRepository.saveAll(oldUsers);
    }

    @Override
    public User changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = getCurrentUser();
        String password = user.getPassword().trim();
        if (changePasswordRequest.isValid(passwordEncoder, password)) {
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword().trim()));
            user.setIsResetPass(false);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User lockOrUnlockUser(Integer status, Long id) {
        User user = userRepository.findById(id).get();
        user.setStatus(status);
        return userRepository.save(user);
    }

    @Override
    public ProfileResponse getCurrentUserInfo() {
        User user = getCurrentUser();
        List<Permission> permissions = permissionRepository.findPermissionsByUserId(user.getId());
        List<String> permissionCodes = permissions.stream().map(permission -> permission.getCode().toLowerCase()).collect(Collectors.toList());
        ProfileResponse profileResponse = mapUserToProfileResponse(user);
        profileResponse.setPermissions(permissionCodes);
        return profileResponse;
    }

    private List<Long> reformatIdsFromRequest(UserDTO userDTO) {
        return Arrays.stream(userDTO.getIds().split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    private User mapToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setStatus(userDTO.getStatus());
        return user;
    }


    private UserDTO mapToDTO(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
    }

    private ProfileResponse mapUserToProfileResponse(User user) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, ProfileResponse.class);
    }
}

package com.vmg.ibo.core.service.user;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.constant.AuthConstant;
import com.vmg.ibo.core.constant.MailMessageConstant;
import com.vmg.ibo.core.constant.UserConstant;
import com.vmg.ibo.core.model.dto.UserAddDto;
import com.vmg.ibo.customer.model.CodeAndEmail;
import com.vmg.ibo.customer.model.ForgotPass;
import com.vmg.ibo.customer.model.UserDetail;
import com.vmg.ibo.customer.model.customer.BusinessCustomer;
import com.vmg.ibo.customer.model.customer.FileUpload;
import com.vmg.ibo.customer.model.customer.PersonalCustomer;
import com.vmg.ibo.customer.model.customer.RegisterModel;
import com.vmg.ibo.core.model.dto.ChangePasswordRequest;
import com.vmg.ibo.core.model.dto.ProfileResponse;
import com.vmg.ibo.core.model.dto.UserDTO;
import com.vmg.ibo.core.model.dto.filter.UserFilter;
import com.vmg.ibo.core.model.entity.*;
import com.vmg.ibo.core.repository.IPermissionRepository;
import com.vmg.ibo.core.repository.IRoleRepository;
import com.vmg.ibo.customer.repository.IUserDetailRepository;
import com.vmg.ibo.core.repository.IUserRepository;
import com.vmg.ibo.customer.service.code_and_email.ICodeAndEmailService;
import com.vmg.ibo.customer.service.fileUpload.FileUploadService;
import com.vmg.ibo.core.service.mail.IMailService;
import com.vmg.ibo.customer.service.userDetail.IUserDetailService;
import com.vmg.ibo.customer.model.DataModel;
import com.vmg.ibo.financial_report.model.dto.FinancialReportDTO;
import com.vmg.ibo.financial_report.model.entity.FinancialReport;
import com.vmg.ibo.financial_report.service.IFinancialReportService;
import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.repository.IFormRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
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

    @Autowired
    private IUserDetailService userDetailService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private ICodeAndEmailService codeAndEmailService;

    @Autowired
    private IUserDetailRepository userDetailRepository;

    @Autowired
    private IFormRepository formRepository;

    @Autowired
    private IFinancialReportService financialReportService;

    @Value("${upload.path}")
    private String fileUpload;

    @Value("${cms.url}")
    private String cmsUrl;

    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder, IPermissionRepository permissionRepository, IMailService mailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.permissionRepository = permissionRepository;
        this.mailService = mailService;
    }

    private static String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }
        return randomString.toString();
    }

    @Override
    public User FindUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User registerUser(RegisterModel registerModel) {
        String codeValid = generateRandomCode();
        CodeAndEmail codeAndEmail = new CodeAndEmail();
        codeAndEmail.setCode(codeValid);
        codeAndEmail.setEmail(registerModel.getEmail());
        codeAndEmailService.saveCodeAndEmail(codeAndEmail);
        User user = new User();
        user.setEmail(registerModel.getEmail());
        List<String> listUserName = userDetailRepository.getAllUsername();
        int maxNumberUserName = listUserName.stream()
                .map(s -> Integer.parseInt(s.substring(8)))
                .max(Comparator.naturalOrder()).orElse(0) + 1;
        String username = "username" + String.valueOf(maxNumberUserName);
        user.setUsername(username);
        String password = registerModel.getPassword();
        user.setStatus((Integer) UserConstant.ENABLE.getValue());
        user.setChannelId((Integer) UserConstant.USER_CONSTANT.getValue());
        user.setChannelName((String) UserConstant.CHANNEL_USER_STR.getValue());
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedBy("me");
        user.setActive(false);
        user.setInfo(false);
        user.setCreatedByUserId(1L);
        user.setCreatedAt(new Date());
        user.setIsResetPass(true);
        Optional<Role> role = roleRepository.findById(2L);
        if (role.isPresent()) {
            user.setRoles(Collections.singleton(role.get()));
        }
        user = userRepository.save(user);
        List<String> listUserCode = userDetailRepository.getAllCustomerCode();
        int maxNumber = listUserCode.stream()
                .map(s -> Integer.parseInt(s.substring(3)))
                .max(Comparator.naturalOrder()).orElse(0) + 1;
        String userCode = DataModel.USER_CODE + String.valueOf(maxNumber);
        UserDetail userDetail = new UserDetail();
        userDetail.setCustomerCode(userCode);
        userDetail.setContactName("");
        userDetail.setIdUser(user.getId());
        userDetailService.create(userDetail);
        mailService.sendFromSystem(message -> message.to(registerModel.getEmail())
                .subject(MailMessageConstant.CREATE_ACCOUNT_SUBJECT)
                .text("Quý khách vui lòng truy cập theo link: " + cmsUrl + "/active-user?code=" + codeValid + " để xác thực tài khoản email. Cảm ơn quý khách đã tin tưởng sử dụng dịch vụ IB Online của HMG")
                .build());
        return user;
    }

    @Override
    public void forgotPassword(String email) {
        String codeValid = generateRandomCode();
        CodeAndEmail codeAndEmail = new CodeAndEmail();
        codeAndEmail.setCode(codeValid);
        codeAndEmail.setEmail(email);
        codeAndEmailService.saveCodeAndEmail(codeAndEmail);
        mailService.sendFromSystem(message -> message.to(email)
                .subject(MailMessageConstant.FORGOT_PASSWORD_SUBJECT)
                .text("Vui lòng truy cập vào đường link sau để đổi mật khẩu: " + cmsUrl + "/change-password?code=" + codeValid
                        + " . Đường link này có hiệu lực trong 10 phút")
                .build());
    }

    @Override
    public int isValidEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email.trim());
        if (user.isPresent()) {
            if (StringUtils.isEmpty(email) || user != null) {
                return 1;
            }
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            if (!email.matches(emailRegex)) {
                return 2;
            }
        }
        return 0;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElse(null);
    }

    @Override
    public Page<UserDTO> findAllUsers(UserFilter userFilter) {
        String email = userFilter.getEmail();
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageable = handlePaging(userFilter, sort);
        return userRepository.findAllUser(email, pageable).map(this::mapToDTO);
    }

    @Override
    public User activeUser(User user) {
        mailService.sendFromSystem(message -> message.to(user.getEmail())
                .subject(MailMessageConstant.WELCOME_SUBJECT)
                .text("Tài khoản của bạn đã được kích hoạt thành công. Cảm ơn quý khách đã tin tưởng sử dụng dịch vụ IB Online của HMG")
                .build());
        return userRepository.save(user);
    }

    @Override
    public boolean isExistEmail(String email) {
        List<String> listEmail = codeAndEmailService.findAllEmail();
        boolean isExist = false;
        for (int i = 0; i < listEmail.size(); i++) {
            if (listEmail.get(i).equals(email)) {
                isExist = true;
            }
            if (isExist) {
                break;
            }
        }
        return isExist;
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
            UserDetail userDetail = userDetailService.findByIdUser(user.getId());
            if (userDetail != null) {
                userDTO.setUserDetail(userDetail);
                if (userDetail.getIsCustomerPersonal() != null && !userDetail.getIsCustomerPersonal()) {
                    List<FinancialReportDTO> financialReports = financialReportService.findAll(user);
                    userDTO.setReports(financialReports);
                }
            }
        }
        return userDTO;
    }

    @Override
    public UserDTO findByUserDetail() {
        User user = userRepository.findById(getCurrentUser().getId()).orElse(null);
        if (user == null) {
            throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "user.error.notFound");
        }
        UserDTO userDTO = mapToDTO(user);
        if (userDTO != null) {
            List<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
            userDTO.setRoleIds(roleIds);
            UserDetail userDetail = userDetailService.findByIdUser(user.getId());
            if (userDetail != null) {
                userDTO.setUserDetail(userDetail);
                if (userDetail.getIsCustomerPersonal() != null && !userDetail.getIsCustomerPersonal()) {
                    List<FinancialReportDTO> financialReports = financialReportService.findAll(user);
                    userDTO.setReports(financialReports);
                }
            }
        }
        return userDTO;
    }

    @Override
    @Transactional
    public User create(UserAddDto userDTO) {
        writeLog("CREATE_USER", "Thêm mới người dùng hệ thống", userDTO, null, null, null, "SYSTEM", this.getClass());
        String password = AuthConstant.DEFAULT_PASSWORD.getValue();
        User user = mapToEntity(userDTO);
        for (int i = 0; i < userDTO.getRoleIds().size(); i++) {
            if (userDTO.getRoleIds().get(i) == 1) {
                throw new WebServiceException(HttpStatus.NOT_FOUND.value(), "Không tìm thấy vai trò này");
            }
        }
        List<Long> userIds = userDTO.getRoleIds();
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(userIds));
        User currentUser = getCurrentUser();
        List<String> listUserName = userDetailRepository.getAllUsername();
        int maxNumberUserName = listUserName.stream()
                .map(s -> Integer.parseInt(s.substring(8)))
                .max(Comparator.naturalOrder()).orElse(0) + 1;
        String username = "username" + String.valueOf(maxNumberUserName);
        user.setUsername(username);
        user.setEmail(userDTO.getEmail());
        user.setRoles(roles);
        user.setStatus(1);
        user.setActive(true);
        user.setInfo(false);
        user.setChannelId((Integer) UserConstant.CHANNEL_ADMIN.getValue());
        user.setChannelName((String) UserConstant.CHANNEL_ADMIN_STR.getValue());
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedBy(currentUser.getUsername());
        user.setCreatedByUserId(currentUser.getId());
        user.setCreatedAt(new Date());
        user.setIsResetPass(true);
        user = userRepository.save(user);
        mailService.sendFromSystem(message -> message.to(userDTO.getEmail())
                .subject(MailMessageConstant.CREATE_ACCOUNT_SUBJECT_FOR_ADMIN)
                .text("Xin chào " + userDTO.getName() + ",\n " +
                        "Bạn đã được đăng ký vào hệ thống IBOnline. Vui lòng đăng nhập với thông tin dưới đây:\n " +
                        "1. Username: " + userDTO.getEmail() + "\n " +
                        "2. Password: " + AuthConstant.DEFAULT_PASSWORD.getValue() + "\n" +
                        "Vui lòng truy cập vào link này để đăng nhập: " + cmsUrl)
                .build());
        return user;
    }

    @Override
    public User createPersonalCustomer(PersonalCustomer personalCustomer) {
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
        List<UserDetail> userDetailList = userDetailService.findAll();
        UserDetail userDetail1 = userDetailService.findByIdUser(idUser);
        boolean foundCINumber = false;

        for (int i = 0; i < userDetailList.size(); i++) {
            UserDetail currentUserDetail = userDetailList.get(i);

            if (currentUserDetail.getCINumber() != null && currentUserDetail.getCINumber().equals(personalCustomer.getCinumber())) {
                if (userDetail1.getCINumber() != null && userDetail1.getCINumber().equals(personalCustomer.getCinumber())) {
                    foundCINumber = true;
                } else {
                    throw new WebServiceException(200, 409, "Số căn cước công dân đã tồn tại");
                }
            }
        }

        if (!foundCINumber && userDetail1.getCINumber() != null && userDetail1.getCINumber().equals(personalCustomer.getCinumber())) {
            throw new WebServiceException(200, 409, "Số căn cước công dân đã tồn tại");
        }
        User user = userRepository.findById(idUser).orElse(null);
        assert user != null;
        user.setInfo(true);
        user.setName(personalCustomer.getName());
        user.setStatus((Integer) UserConstant.ENABLE.getValue());
        user.setPhone(personalCustomer.getPhone());
        user.setCreatedAt(new Date());
        userRepository.save(user);
        UserDetail userDetail = userDetailService.findByIdUser(idUser);
        if (userDetail == null) {
            userDetail = new UserDetail();
        }
        userDetail.setAddress(personalCustomer.getAddress());
        userDetail.setDescription(personalCustomer.getDescription());
        userDetail.setIdUser(idUser);
        userDetail.setCapitalSize(personalCustomer.getCapitalSize());
        userDetail.setIsCustomerPersonal(true);
        userDetail.setContactName(personalCustomer.getName());
        userDetail.setCINumber(personalCustomer.getCinumber());
        userDetailService.create(userDetail);
        return user;
    }

    @Override
    @Transactional
    public User createBusinessCustomer(BusinessCustomer businessCustomer) {
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
        User user = userRepository.findById(idUser).orElse(null);
        assert user != null;
        user.setInfo(true);
        user.setName(businessCustomer.getBusinessName());
        user.setPhone(businessCustomer.getPhone());
        user.setStatus((Integer) UserConstant.ENABLE.getValue());
        user.setCreatedAt(new Date());
        userRepository.save(user);
        UserDetail userDetail = userDetailService.findByIdUser(idUser);
        if (userDetail == null) {
            userDetail = new UserDetail();
        }
        userDetail.setAddress(businessCustomer.getAddress());
        userDetail.setDescription(businessCustomer.getDescription());
        userDetail.setIdUser(idUser);
        userDetail.setCapitalSize(businessCustomer.getCapitalSize());
        userDetail.setIsCustomerPersonal(false);
        userDetail.setContactName(businessCustomer.getContactName());
        userDetail.setCodeReg(businessCustomer.getCodeReg());
        userDetail.setCodeTax(businessCustomer.getCodeTax());
        userDetail.setBusinessName(businessCustomer.getBusinessName());
        userDetail.setMainBusiness(businessCustomer.getMainBusiness());
        userDetail.setTitle(businessCustomer.getTitle());
        userDetailService.create(userDetail);
        FinancialReport financialReport = financialReportService.findByQuarterAndYearAndUser(businessCustomer.getQuarter(), businessCustomer.getYear(), user);
        boolean isExist = true;
        if (financialReport == null) {
            financialReport = new FinancialReport();
            isExist = false;
        }
        financialReport.setRevenue(businessCustomer.getMostRecentYearRevenue());
        financialReport.setProfit(businessCustomer.getMostRecentYearProfit());
        financialReport.setAsset(businessCustomer.getPropertyStructure());
        financialReport.setDebt(businessCustomer.getDebtStructure());
        financialReport.setYear(businessCustomer.getYear());
        financialReport.setQuarter(businessCustomer.getQuarter());
        financialReport.setUser(user);
        financialReport = financialReportService.save(financialReport);
        if (businessCustomer.getFiles() != null) {
            if (isExist) {
                fileUploadService.deleteAllByFinancialReport(financialReport);
            }
            for (int i = 0; i < businessCustomer.getFiles().size(); i++) {
                FileUpload fileUpload1 = new FileUpload();
                MultipartFile file = businessCustomer.getFiles().get(i);
                String fileName = file.getOriginalFilename();
                try {
                    FileCopyUtils.copy(file.getBytes(), new File(this.fileUpload + fileName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileUpload1.setFile(fileName);
                fileUpload1.setIdUser(idUser);
                fileUpload1.setFinancialReport(financialReport);
                fileUploadService.saveFile(fileUpload1);
            }
        }
        return user;
    }

    @Override
    public User updateBusinessCustomer(BusinessCustomer businessCustomer) {
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
        User user = userRepository.findById(idUser).orElse(null);
        assert user != null;
        user.setInfo(true);
        user.setName(businessCustomer.getBusinessName());
        user.setPhone(businessCustomer.getPhone());
        user.setStatus((Integer) UserConstant.ENABLE.getValue());
        user.setCreatedAt(new Date());
        userRepository.save(user);
        UserDetail userDetail = userDetailService.findByIdUser(idUser);
        if (userDetail == null) {
            userDetail = new UserDetail();
        }
        userDetail.setAddress(businessCustomer.getAddress());
        userDetail.setDescription(businessCustomer.getDescription());
        userDetail.setIdUser(idUser);
        userDetail.setCapitalSize(businessCustomer.getCapitalSize());
        userDetail.setIsCustomerPersonal(false);
        userDetail.setContactName(businessCustomer.getContactName());
        userDetail.setCodeReg(businessCustomer.getCodeReg());
        userDetail.setCodeTax(businessCustomer.getCodeTax());
        userDetail.setBusinessName(businessCustomer.getBusinessName());
        userDetail.setMainBusiness(businessCustomer.getMainBusiness());
        userDetail.setTitle(businessCustomer.getTitle());
        userDetailService.create(userDetail);
        return user;
    }

    @Override
    @Transactional
    public User update(Long id, UserAddDto userDTO) {
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
        oldUser.setPhone(user.getPhone());
        oldUser.setName(user.getName());
        oldUser.setStatus(user.getStatus());
        oldUser.setUpdatedBy(currentUser.getUsername());
        oldUser.setUpdatedByUserId(currentUser.getId());
        oldUser.setUpdatedAt(new Date());
        return userRepository.save(oldUser);
    }

    @Override
    @Transactional
    public List<User> deleteByIds(UserAddDto userDTO) {
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
    public List<User> changeStatusByIds(UserAddDto userDTO) {
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
    public User changePasswordBeForgot(ForgotPass forgotPass) {
        CodeAndEmail codeAndEmail = codeAndEmailService.findByCode(forgotPass.getCode());
        User user = findByEmail(codeAndEmail.getEmail());
        user.setPassword(passwordEncoder.encode(forgotPass.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User lockOrUnlockUser(Integer status, Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setStatus(status);
            return userRepository.save(user.get());
        } else {
            throw new WebServiceException(HttpStatus.OK.value(), "user.error.notFound");
        }
    }

    @Override
    public void checkChannel(User user) {
        if (user.getChannelId() == 0) {
            throw new WebServiceException(HttpStatus.CONFLICT.value(), "Access denied");
        }
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

    private List<Long> reformatIdsFromRequest(UserAddDto userDTO) {
        return Arrays.stream(userDTO.getIds().split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    private User mapToEntity(UserAddDto userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
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

    private String generateRandomCode() {
        String randomString = generateRandomString();
        List<String> listCode = codeAndEmailService.findAllCode();
        while (listCode.contains(randomString)) {
            randomString = generateRandomString();
        }
        return randomString;
    }

    @Override
    public User registerUserByGoogle(RegisterModel registerModel) {
        String codeValid = generateRandomCode();
        CodeAndEmail codeAndEmail = new CodeAndEmail();
        codeAndEmail.setCode(codeValid);
        codeAndEmail.setEmail(registerModel.getEmail());
        codeAndEmailService.saveCodeAndEmail(codeAndEmail);
        User user = new User();
        user.setEmail(registerModel.getEmail());
        List<String> listUserName = userDetailRepository.getAllUsername();
        int maxNumberUserName = listUserName.stream()
                .map(s -> Integer.parseInt(s.substring(8)))
                .max(Comparator.naturalOrder()).orElse(0) + 1;
        String username = "username" + String.valueOf(maxNumberUserName);
        user.setUsername(username);
        String password = registerModel.getPassword();
        user.setStatus((Integer) UserConstant.ENABLE.getValue());
        user.setChannelId((Integer) UserConstant.USER_CONSTANT.getValue());
        user.setChannelName((String) UserConstant.CHANNEL_USER_STR.getValue());
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedBy("google");
        user.setActive(true);
        user.setCreatedByUserId(1L);
        user.setCreatedAt(new Date());
        user.setIsResetPass(true);
        Role role = roleRepository.findById(2L).get();
        user.setRoles(Collections.singleton(role));
        user = userRepository.save(user);
        return user;
    }
}

package com.vmg.ibo.customer.service.userDetail;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.customer.model.UserDetailWithUserDTO;
import com.vmg.ibo.core.model.dto.filter.UserFilter;
import com.vmg.ibo.customer.model.UserDetail;
import com.vmg.ibo.customer.model.UserDetailWithUserDTOImpl;
import com.vmg.ibo.customer.model.customer.FileUpload;
import com.vmg.ibo.customer.repository.IUserDetailRepository;
import com.vmg.ibo.customer.service.fileUpload.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("newUserDetailService")
public class UserDetailService extends BaseService implements IUserDetailService {
    @Autowired
    private IUserDetailRepository userDetailRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public Page<UserDetailWithUserDTO> findAllUser(UserFilter userFilter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        String contactName = escapeSpecialCharacters(userFilter.getQuery());
        PageRequest pageable = handlePaging(userFilter, sort);
        if(userFilter.getFromCapitalSize() == null) {
            userFilter.setFromCapitalSize(0L);
        }
        if(userFilter.getToCapitalSize() == null) {
            userFilter.setToCapitalSize(Long.MAX_VALUE);
        }
        return userDetailRepository.findAllUser(pageable, userFilter.getUsername(), userFilter.getQuery(), userFilter.getIsCustomerPersonal(), userFilter.getStatus(), userFilter.getFromCapitalSize(), userFilter.getToCapitalSize());
    }

    private String escapeSpecialCharacters(String input) {
        return input.replace("%", "\\%");
    }

    @Override
    public UserDetailWithUserDTOImpl findUserById(Long id) {
        UserDetailWithUserDTO userDetailWithUserDTO = userDetailRepository.findUserById(id);
        UserDetailWithUserDTOImpl userDetailWithUserDTOImpl;
        if (!userDetailWithUserDTO.getIsCustomerPersonal()) {
            List<FileUpload> fileUploadList = fileUploadService.findByIdUser(id);
            userDetailWithUserDTOImpl = new UserDetailWithUserDTOImpl(userDetailWithUserDTO, fileUploadList);
        } else {
            userDetailWithUserDTOImpl = new UserDetailWithUserDTOImpl(userDetailWithUserDTO);
        }
        return userDetailWithUserDTOImpl;
    }

    @Override
    public Optional<UserDetail> findById() {
        return userDetailRepository.findById(getCurrentUser().getId());
    }

    @Override
    public UserDetail create(UserDetail userDetail) {
        return userDetailRepository.save(userDetail);
    }

    @Override
    public UserDetail findByIdUser(Long id) {
        return userDetailRepository.findByIdUser(id);
    }
}

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

@Service("newUserDetailService")
public class UserDetailService extends BaseService implements IUserDetailService {
    @Autowired
    private IUserDetailRepository userDetailRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public Page<UserDetailWithUserDTO> findAllUser(UserFilter userFilter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageable = handlePaging(userFilter, sort);
        return userDetailRepository.findAllUser(pageable, userFilter.getContactName(), userFilter.getIsCustomerPersonal(), userFilter.getStatus(), userFilter.getFromCapitalSize(), userFilter.getToCapitalSize());
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
    public UserDetail create(UserDetail userDetail) {
        return userDetailRepository.save(userDetail);
    }

    @Override
    public UserDetail findByIdUser(Long id) {
        return userDetailRepository.findByIdUser(id);
    }
}

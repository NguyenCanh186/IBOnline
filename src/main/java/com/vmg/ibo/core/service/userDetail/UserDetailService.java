package com.vmg.ibo.core.service.userDetail;
import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.dto.UserDetailWithUserDTO;
import com.vmg.ibo.core.model.dto.filter.UserFilter;
import com.vmg.ibo.core.model.entity.UserDetail;
import com.vmg.ibo.core.repository.IUserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("newUserDetailService")
public class UserDetailService extends BaseService implements IUserDetailService {
    @Autowired
    private IUserDetailRepository userDetailRepository;

    @Override
    public Page<UserDetailWithUserDTO> findAllUser(UserFilter userFilter) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageable = handlePaging(userFilter, sort);
        return userDetailRepository.findAllUser(pageable, userFilter.getContactName(), userFilter.getIsCustomerPersonal(), userFilter.getStatus(), userFilter.getFromCapitalSize(), userFilter.getToCapitalSize());
    }

    @Override
    public UserDetailWithUserDTO findUserById(Long id) {
        return userDetailRepository.findUserById(id);
    }

    @Override
    public UserDetail create(UserDetail userDetail) {
        return userDetailRepository.save(userDetail);
    }
}

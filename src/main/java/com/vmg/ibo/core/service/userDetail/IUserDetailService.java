package com.vmg.ibo.core.service.userDetail;

import com.vmg.ibo.core.model.dto.UserDetailWithUserDTO;
import com.vmg.ibo.core.model.dto.filter.UserFilter;
import com.vmg.ibo.core.model.entity.UserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserDetailService {
    Page<UserDetailWithUserDTO> findAllUser(UserFilter userFilter);

    UserDetailWithUserDTO findUserById(Long id);

    UserDetail create(UserDetail userDetail);
}

package com.vmg.ibo.customer.service.userDetail;

import com.vmg.ibo.customer.model.UserDetailWithUserDTO;
import com.vmg.ibo.core.model.dto.filter.UserFilter;
import com.vmg.ibo.customer.model.UserDetail;
import com.vmg.ibo.customer.model.UserDetailWithUserDTOImpl;
import org.springframework.data.domain.Page;

public interface IUserDetailService {
    Page<UserDetailWithUserDTO> findAllUser(UserFilter userFilter);

    UserDetailWithUserDTOImpl findUserById(Long id);

    UserDetail create(UserDetail userDetail);

    UserDetail findByIdUser(Long id);
}

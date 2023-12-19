package com.vmg.ibo.core.service.user;

import com.vmg.ibo.core.model.customer.BusinessCustomer;
import com.vmg.ibo.core.model.customer.PersonalCustomer;
import com.vmg.ibo.core.model.customer.RegisterModel;
import com.vmg.ibo.core.model.dto.ChangePasswordRequest;
import com.vmg.ibo.core.model.dto.ProfileResponse;
import com.vmg.ibo.core.model.dto.UserDTO;
import com.vmg.ibo.core.model.dto.filter.UserFilter;
import com.vmg.ibo.core.model.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {
    User registerUser(RegisterModel registerModel);
    boolean isValidEmail(String email);
    User findByUsername(String username);

    User findByEmail(String email);

    Page<UserDTO> findAllUsers(UserFilter userFilter);

    User activeUser(User user);

    UserDTO findById(Long id);

    User create(UserDTO userDTO);

    User createPersonalCustomer(PersonalCustomer personalCustomer);

    User createBusinessCustomer(BusinessCustomer businessCustomer);

    User update(Long id, UserDTO userDTO);

    List<User> deleteByIds(UserDTO userDTO);

    List<User> changeStatusByIds(UserDTO userDTO);

    ProfileResponse getCurrentUserInfo();

    User changePassword(ChangePasswordRequest changePasswordRequest);

    User lockOrUnlockUser(Integer status, Long id);
}

package com.vmg.ibo.core.service.user;

import com.vmg.ibo.core.model.dto.*;
import com.vmg.ibo.customer.model.customer.BusinessCustomer;
import com.vmg.ibo.customer.model.customer.PersonalCustomer;
import com.vmg.ibo.customer.model.customer.RegisterModel;
import com.vmg.ibo.core.model.dto.filter.UserFilter;
import com.vmg.ibo.customer.model.ForgotPass;
import com.vmg.ibo.core.model.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {
    User FindUserById(Long id);
    User registerUser(RegisterModel registerModel);
    User registerUserByGoogle(RegisterModel registerModel);

    void forgotPassword(String email);
    int isValidEmail(String email);
    User findByUsername(String username);

    User findByEmail(String email);

    Page<UserDTO> findAllUsers(UserFilter userFilter);

    User activeUser(User user);

    boolean isExistEmail(String email);

    UserDTO findById(Long id);
    UserDTO findByUserDetail();

    User create(UserAddDto userDTO);

    User createPersonalCustomer(PersonalCustomer personalCustomer);

    User createBusinessCustomer(BusinessCustomer businessCustomer);
    User updateBusinessCustomer(BusinessCustomer businessCustomer);

    User update(Long id, UserAddDto userDTO);

    List<User> deleteByIds(UserAddDto userDTO);

    List<User> changeStatusByIds(UserAddDto userDTO);

    ProfileResponse getCurrentUserInfo();

    User changePassword(ChangePasswordRequest changePasswordRequest);

    User changePasswordBeForgot(ForgotPass forgotPass);

    User lockOrUnlockUser(Integer status, Long id);

    void checkChannel(User user);

    CheckPasswordResponse checkNewPassword(String newPassword);
}

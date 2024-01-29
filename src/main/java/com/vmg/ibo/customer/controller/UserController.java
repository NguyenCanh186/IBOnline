package com.vmg.ibo.customer.controller;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.core.model.dto.UserAddDto;
import com.vmg.ibo.core.model.dto.UserChangeStatusDto;
import com.vmg.ibo.customer.model.customer.BusinessCustomer;
import com.vmg.ibo.customer.model.customer.PersonalCustomer;
import com.vmg.ibo.core.model.dto.UserDTO;
import com.vmg.ibo.core.model.dto.filter.UserFilter;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.customer.service.userDetail.IUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UserController extends BaseService {
    @Autowired
    private IUserService userService;

    @Autowired
    private IUserDetailService userDetailService;

    @Value("${upload.path}")
    private String fileUpload;

    @GetMapping
    @PreAuthorize("hasAuthority('user-list')")
    public Result<?> findAllUsers(UserFilter userFilter) {
        return Result.success(userService.findAllUsers(userFilter));
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('customer-list')")
    public Result<?> findAllUsersWithPaging(@RequestBody UserFilter userFilter) {
        return Result.success(userDetailService.findAllUser(userFilter));
    }

    @PostMapping("/create-personal-customer")
    public Result<?> createUser(@Validated(Insert.class) @RequestBody PersonalCustomer personalCustomer) {
        return Result.success(userService.createPersonalCustomer(personalCustomer));
    }

    @PostMapping("/create-business-customer")
    public Result<?> createUser(@Validated(Insert.class) @ModelAttribute BusinessCustomer businessCustomer) {
        return Result.success(userService.createBusinessCustomer(businessCustomer));
    }

    @PostMapping("/update-business-customer")
    public Result<?> updateUser(@Validated(Update.class) @ModelAttribute BusinessCustomer businessCustomer) {
        return Result.success(userService.updateBusinessCustomer(businessCustomer));
    }
    @GetMapping("/systemUser/{id}")
    @PreAuthorize("hasAuthority('user-get')")
    public Result<?> getUserById(@PathVariable Long id) {
        return Result.success(userDetailService.findUserById(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user-get')")
    public Result<?> getUserSystemById(@PathVariable Long id) {
        return Result.success(userService.findById(id));
    }

    @GetMapping("/customer/{id}")
    @PreAuthorize("hasAuthority('customer-detail')")
    public Result<?> getCustomerById(@PathVariable Long id) {
        return Result.success(userService.findById(id));
    }

    @GetMapping("/get-detail")
    public Result<?> getDetailUser() {
        return Result.success(userService.findByUserDetail());
    }

    @GetMapping("/check-info")
    public Result<?> checkInfo() {
        if (!userDetailService.isInfo()) {
            return Result.error(404, "Không tìm thấy thông tin người dùng");
        }
        return Result.result(200, "Người dùng đã nhập thông tin cá nhân", null);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user-edit')")
    public Result<?> editUser(@PathVariable Long id, @Validated(Update.class) @RequestBody UserAddDto userDTO) {
        return Result.success(userService.update(id, userDTO));
    }

    @PutMapping("/{id}/lockOrUnlock")
    @PreAuthorize("hasAuthority('customer-update-status')")
    public Result<?> lockOrUnlock(@PathVariable Long id,@Validated(Update.class) @RequestBody UserChangeStatusDto userChangeStatusDto) {
        if (userChangeStatusDto.getStatus() == null) {
            return Result.error(409, "Trường status không được để trống");
        }
        if (userChangeStatusDto.getStatus() != 0 && userChangeStatusDto.getStatus() != 1 && userChangeStatusDto.getStatus() != 2) {
            return Result.error(409, "Trường status không hợp lệ");
        }
        return Result.success(userService.lockOrUnlockUser(userChangeStatusDto.getStatus(), id));
    }

    @PutMapping("/change-status")
    @PreAuthorize("hasAuthority('user-change-status')")
    public Result<?> changeStatusUser(@RequestBody UserAddDto userDTO) {
        return Result.success(userService.changeStatusByIds(userDTO));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user-add')")
    public Result<?> createUser(@Validated(Insert.class) @RequestBody UserAddDto userDTO) {
        if (userService.isValidEmail(userDTO.getEmail()) == 1) {
            return Result.error(409, "Email đã được đăng ký");
        }
        if (userService.isValidEmail(userDTO.getEmail()) == 2) {
            return Result.error(409, "Email đã không đúng định dạng");
        }
        return Result.success(userService.create(userDTO));
    }
}

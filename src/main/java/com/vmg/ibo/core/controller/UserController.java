package com.vmg.ibo.core.controller;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.core.model.customer.BusinessCustomer;
import com.vmg.ibo.core.model.customer.PersonalCustomer;
import com.vmg.ibo.core.model.dto.UserDTO;
import com.vmg.ibo.core.model.dto.filter.UserFilter;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.core.service.userDetail.IUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {
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
    @PreAuthorize("hasAuthority('user-list')")
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
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user-get')")
    public Result<?> getUserById(@PathVariable Long id) {
        return Result.success(userDetailService.findUserById(id));
    }

    @GetMapping("/systemUser/{id}")
    @PreAuthorize("hasAuthority('user-get')")
    public Result<?> getUserSystemById(@PathVariable Long id) {
        return Result.success(userService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user-edit')")
    public Result<?> editUser(@PathVariable Long id, @Validated(Update.class) @RequestBody UserDTO userDTO) {
        return Result.success(userService.update(id, userDTO));
    }

    @PutMapping("/lockOrUnlock")
    @PreAuthorize("hasAuthority('user-edit')")
    public Result<?> lockOrUnlock(@RequestBody UserDTO userDTO) {
        Long id = userDTO.getId();
        Integer status = userDTO.getStatus();
        return Result.success(userService.lockOrUnlockUser(status, id));
    }

    @PutMapping("/change-status")
    @PreAuthorize("hasAuthority('user-change-status')")
    public Result<?> changeStatusUser(@RequestBody UserDTO userDTO) {
        return Result.success(userService.changeStatusByIds(userDTO));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user-add')")
    public Result<?> createUser(@Validated(Insert.class) @RequestBody UserDTO userDTO) {
        if (!userService.isValidEmail(userDTO.getEmail())) {
            return Result.error(400, "Email đã được đăng ký");
        }
        return Result.success(userService.create(userDTO));
    }
}

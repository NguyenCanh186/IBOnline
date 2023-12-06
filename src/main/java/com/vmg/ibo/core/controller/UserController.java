package com.vmg.ibo.core.controller;

import com.vmg.ibo.core.action.Delete;
import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.core.model.dto.UserDTO;
import com.vmg.ibo.core.model.dto.filter.UserFilter;
import com.vmg.ibo.core.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('user-list')")
    public Result<?> findAllUsersWithPaging(UserFilter userFilter) {
        return Result.success(userService.findAllUsers(userFilter));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user-get')")
    public Result<?> getUserById(@PathVariable Long id) {
        return Result.success(userService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user-add')")
    public Result<?> createUser(@Validated(Insert.class) @RequestBody UserDTO userDTO) {
        return Result.success(userService.create(userDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user-edit')")
    public Result<?> editUser(@PathVariable Long id, @Validated(Update.class) @RequestBody UserDTO userDTO) {
        return Result.success(userService.update(id, userDTO));
    }

    @PutMapping("/change-status")
    @PreAuthorize("hasAuthority('user-change-status')")
    public Result<?> changeStatusUser(@RequestBody UserDTO userDTO) {
        return Result.success(userService.changeStatusByIds(userDTO));
    }
}

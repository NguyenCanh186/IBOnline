package com.vmg.ibo.core.controller;

import com.vmg.ibo.core.action.Delete;
import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.core.model.dto.ChangePasswordRequest;
import com.vmg.ibo.core.model.dto.JwtDTO;
import com.vmg.ibo.core.model.dto.UserDTO;
import com.vmg.ibo.core.model.dto.filter.UserFilter;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.core.service.userDetail.IUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IUserDetailService userDetailService;

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('user-list')")
    public Result<?> findAllUsersWithPaging(@RequestBody UserFilter userFilter) {
        return Result.success(userDetailService.findAllUser(userFilter));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user-get')")
    public Result<?> getUserById(@PathVariable Long id) {
        return Result.success(userDetailService.findUserById(id));
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
}

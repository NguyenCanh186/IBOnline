package com.vmg.ibo.core.controller;

import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.model.dto.ChangePasswordRequest;
import com.vmg.ibo.core.model.dto.JwtDTO;
import com.vmg.ibo.core.model.dto.LoginDTO;
import com.vmg.ibo.core.model.entity.Permission;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.service.permission.IPermissionService;
import com.vmg.ibo.core.service.security.JwtService;
import com.vmg.ibo.core.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/auth")
@Slf4j
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IPermissionService permissionService;

    @PostMapping("/login")
    public Result<?> login(@Validated @RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (LockedException lockedException) {
            throw new WebServiceException(HttpStatus.BAD_REQUEST.value(), "login.error.user-locked");
        } catch (Exception e) {
            throw new WebServiceException(HttpStatus.BAD_REQUEST.value(), "login.error.bad-credentials");
        }
        User currentUser = userService.findByEmail(loginDTO.getEmail());
        String jwt = jwtService.generateJwtToken(loginDTO.getEmail());
        return Result.success(new JwtDTO(jwt, currentUser.getId(),
                loginDTO.getEmail(),
                null));
    }

    @PostMapping("/logout")
    public Result<?> logout(Authentication authentication) {
        authentication.getName();
        return Result.success();
    }
    @GetMapping("/permissions")
    public Result<?> getCurrentUserPermissions() {
        List<Permission> permissions = permissionService.getRecursivePermissionsByCurrentUser(null);
        return Result.success(permissions);
    }

    @GetMapping("/profile")
    public Result<?> getCurrentUser() {
        return Result.success(userService.getCurrentUserInfo());
    }

    @PutMapping("/change-password")
    public Result<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        User user = userService.changePassword(changePasswordRequest);
        if (user == null) {
            throw new WebServiceException(HttpStatus.BAD_REQUEST.value(), "change-password.error.bad-credentials");
        }
        return Result.success("Thay đổi mật khẩu thành công");
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getUsername(), changePasswordRequest.getNewPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtService.generateJwtToken(user.getUsername());
//        return Result.success(new JwtDTO(jwt, user.getId(),
//                user.getUsername(),
//                null));
    }
}

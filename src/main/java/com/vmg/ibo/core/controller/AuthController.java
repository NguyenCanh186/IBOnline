package com.vmg.ibo.core.controller;

import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.model.dto.ChangePasswordRequest;
import com.vmg.ibo.core.model.dto.JwtDTO;
import com.vmg.ibo.core.model.dto.LoginDTO;
import com.vmg.ibo.core.model.entity.Permission;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.payload.OauthGoogleRequest;
import com.vmg.ibo.core.service.google.IGoogleAuthService;
import com.vmg.ibo.core.service.permission.IPermissionService;
import com.vmg.ibo.core.service.security.JwtService;
import com.vmg.ibo.core.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    
    @Value("${google.authorization.uri}")
    private String authorizationUri;

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    @Value("${google.response.type}")
    private String responseType;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IGoogleAuthService googleAuthService;

    @PostMapping("/login")
    public Result<?> login(@Validated @RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (LockedException lockedException) {
            throw new WebServiceException(HttpStatus.OK.value(),409, "login.error.user-locked");
        } catch (Exception e) {
            throw new WebServiceException(HttpStatus.OK.value(),409, "login.error.bad-credentials");
        }
        User currentUser = userService.findByEmail(loginDTO.getEmail());
        if (!currentUser.isActive()) {
            return Result.result(409, "Vui lòng kích hoạt tài khoản", null);
        }
        String jwt = jwtService.generateJwtToken(loginDTO.getEmail());
        return Result.success(new JwtDTO(jwt, currentUser.getId(),
                loginDTO.getEmail(),
                null, currentUser.getChannelId()));
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
            throw new WebServiceException(HttpStatus.OK.value(),409, "change-password.error.bad-credentials");
        }
        return Result.success("Thay đổi mật khẩu thành công");
    }

    @PostMapping("/check-new-password")
    public Result<?> checkNewPassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return Result.success(userService.checkNewPassword(changePasswordRequest.getNewPassword()));
    }

    @PostMapping("/google/get-token")
    public Result<?> oauthGoogle(@Validated @RequestBody OauthGoogleRequest oauthGoogleRequest) {
        
        return Result.success(googleAuthService.authenByGoogle(oauthGoogleRequest.getCode(), oauthGoogleRequest.getRedirectUri()));
    }

    @GetMapping("/google/authorization")
    public Result<?> getUrlGoogle() {
        String scope = "profile email";
        String googleAuthorizationUrl =
                authorizationUri
                        + "?client_id="
                        + clientId
                        + "&redirect_uri="
                        + redirectUri
                        + "&scope="
                        + scope
                        + "&response_type="
                        + responseType;

        return Result.success(googleAuthorizationUrl);
    }

    @GetMapping("/google/callback")
    public Result<?> getUrlGoogle(@RequestParam("code") String authorizationCode) {
        return Result.success(googleAuthService.authenByGoogle(authorizationCode, null));
    }
    

}

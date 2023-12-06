package com.vmg.ibo.core.utils;

import com.vmg.ibo.core.service.security.CustomUserDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class SecurityUtils {

    public static String username() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static boolean isLogged() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    public static Collection<? extends GrantedAuthority> getAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    public static Long userId() {
        CustomUserDetail userDetail;
        try {
             userDetail = (CustomUserDetail) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            return userDetail.getUser().getId();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isSuperAdmin() {
        return false;
    }

    public static List<String> getPermissions() {

        return null;
    }

    public static UserDetails getUserDetail() {
        UserDetails loggedUser;
        try {
            loggedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
        return loggedUser;
    }
}

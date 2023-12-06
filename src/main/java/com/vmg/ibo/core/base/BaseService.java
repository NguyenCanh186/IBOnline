package com.vmg.ibo.core.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmg.ibo.core.model.entity.LogAction;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.repository.IUserRepository;
import com.vmg.ibo.core.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public abstract class BaseService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ObjectMapper mapper;

    protected User getCurrentUser() {
        User user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String username = userDetails.getUsername();
                user = userRepository.findByUsername(username);
            }
        }
        return user;
    }

    protected boolean hasPermission(String permissionCode) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                authorities = userDetails.getAuthorities();
            }
        }
        for (GrantedAuthority permission : authorities) {
            if (permission.getAuthority().equals(permissionCode)) {
                return true;
            }
        }
        return false;
    }

    protected PageRequest handlePaging(BaseFilter baseFilter, Sort sort) {
        if (baseFilter.getPage() <= 0) {
            baseFilter.setPage(1);
        }
        int page = baseFilter.getPage() - 1;
        int limit = baseFilter.getLimit();
        PageRequest pageable = PageRequest.of(page, limit);
        if (sort != null) {
            pageable = PageRequest.of(page, limit, sort);
        }
        return pageable;
    }

    protected void writeLog(String actionType, String actionDescription, Object actionData, Integer impactLevel, Object originalValue, String changedValue, String affectTo, Class<?> clazz) {
        try {
            LogAction logAction = new LogAction();
            logAction.setActionType(actionType);
            logAction.setActionDescription(actionDescription);
            if (actionData != null) {
                logAction.setActionData(mapper.writeValueAsString(actionData));
            }
            logAction.setImpactLevel(impactLevel);
            if (originalValue != null) {
                logAction.setOriginalValue(mapper.writeValueAsString(originalValue));
            }
            logAction.setChangedValue(changedValue);
            logAction.setAffectTo(affectTo);
            LoggerUtils.log(logAction, clazz);
        } catch (JsonProcessingException e) {
            return;
        }
    }
}

package com.vmg.ibo.core.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", "Tài khoản không được phân quyền");
        errors.put("code", HttpStatus.FORBIDDEN.value());
        errors.put("redirect", null);
        errors.put("data", null);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(errors);
        response.getOutputStream().write(json.getBytes());
    }
}

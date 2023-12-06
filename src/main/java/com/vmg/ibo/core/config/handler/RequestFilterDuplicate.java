package com.vmg.ibo.core.config.handler;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class RequestFilterDuplicate implements Filter {

    public static boolean isValid(String json) {
        boolean retValue = true;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
            JsonFactory factory = mapper.getFactory();
            JsonParser parser = factory.createParser(json);
            JsonNode jsonObj = mapper.readTree(parser);
            if (jsonObj != null) {
                System.out.println(jsonObj.toString());
            }
        } catch (IOException ioe) {
            retValue = false;
        }
        return retValue;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        RequestWrapper wrapper = new RequestWrapper((HttpServletRequest) servletRequest);
        String requestBody = IOUtils.toString(wrapper.getInputStream(), StandardCharsets.UTF_8);
        String method = ((HttpServletRequest) servletRequest).getMethod();
        if ((method.equals("POST") || method.equals("PUT")) && !Objects.isNull(requestBody) && !isValid(requestBody)) {
            setErrorResponse(HttpStatus.CONFLICT, (HttpServletResponse) servletResponse, "Dữ liệu nhập không đúng định dạng");
            return;
        }
        chain.doFilter(wrapper, servletResponse);
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", message);
        errors.put("code", status.value());
        errors.put("redirect", null);
        errors.put("data", null);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(errors);
        response.getOutputStream().write(json.getBytes());
    }

}
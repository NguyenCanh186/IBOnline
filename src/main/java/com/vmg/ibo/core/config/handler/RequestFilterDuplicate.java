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
        chain.doFilter(wrapper, servletResponse);
    }

}
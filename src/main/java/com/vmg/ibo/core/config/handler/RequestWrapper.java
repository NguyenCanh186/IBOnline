package com.vmg.ibo.core.config.handler;

import java.io.IOException;


import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {
 
    private byte[] body;
 
    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
 
        this.body = StreamUtils.copyToByteArray(request.getInputStream());
    }
 
    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStreamWrapper(this.body);
 
    }
 
}
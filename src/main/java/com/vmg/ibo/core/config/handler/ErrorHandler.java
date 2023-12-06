package com.vmg.ibo.core.config.handler;

import java.util.HashMap;

public class ErrorHandler extends HashMap<String, String> {
    private static final long serialVersionUID = 1L;

	private ErrorHandler() {
    
    }
    
    public static ErrorHandler get() {
        return new ErrorHandler();
    }
    
    public boolean isPass() {
        return this.size() == 0;
    }
    
    public String getFirstErrorMessage() {
        if (keySet().isEmpty()) {
            return "";
        } else {
            return get(keySet().toArray()[0]);
        }
    }
    
    public String getError() {
        return get("ERROR");
    }
    
    public String[] getAllErrorMessage() {
        return values().toArray(new String[]{});
    }
    
    public ErrorHandler putError(String message) {
        put("ERROR", message);
        return this;
    }
}

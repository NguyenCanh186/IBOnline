package com.vmg.ibo.core.config.exception;

import java.util.Map;

public class ServiceValidationException extends Exception {
    private final Map<String, String> errors;

    public ServiceValidationException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}

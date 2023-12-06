package com.vmg.ibo.core.config.exception;

public class DuplicateEntityException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public DuplicateEntityException(String message) {
        super(message);
    }
}

package com.vmg.ibo.core.constant;

public enum HttpStatusConstant {
    SUCCESS(200),
    CREATED(201),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    PERMISSION_DENIED(403),
    NOT_FOUND(404),
    CONFLICT(409),
    PRECONDITION_FAILED(412),
    VERIFICATION_FAILED(499);

    private final int value;
    HttpStatusConstant(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}

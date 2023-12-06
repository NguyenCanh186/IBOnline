package com.vmg.ibo.core.constant;

public enum UserConstant {
    NOT_VERIFY(0),
    ENABLE(1),
    LOCKED(2),
    CHANNEL_ADMIN(0),
    CHANNEL_ADMIN_STR("SYSTEM"),
    CHANNEL_MERCHANT(1),
    CHANNEL_MERCHANT_STR("MERCHANT");

    private final Object value;

    UserConstant(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}

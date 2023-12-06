package com.vmg.ibo.core.constant;

public enum RequestConstant {
    DEFAULT_CHARSET_NAME("UTF-8"),
    BLANK(""),
    DOT("."),
    SEPARATOR("/");

    private final String value;
    RequestConstant(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}

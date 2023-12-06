package com.vmg.ibo.core.constant;

public enum OptionConstant {
    PERMISSION_GROUP("permission_group");
    private final String value;
    OptionConstant(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}

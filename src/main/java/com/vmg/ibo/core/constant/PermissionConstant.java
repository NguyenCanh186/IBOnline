package com.vmg.ibo.core.constant;

public enum PermissionConstant {
    NO_CHILD(0);

    private final int value;
    PermissionConstant(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}

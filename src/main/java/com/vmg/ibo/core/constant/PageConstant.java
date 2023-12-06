package com.vmg.ibo.core.constant;

public enum PageConstant {
    DEFAULT_LIMIT(20),
    DEFAULT_PAGE(1);

    private final int value;
    PageConstant(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}

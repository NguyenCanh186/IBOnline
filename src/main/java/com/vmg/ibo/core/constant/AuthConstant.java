package com.vmg.ibo.core.constant;

import lombok.Getter;

@Getter
public enum AuthConstant {
    DEFAULT_PASSWORD("Vmg@123456");
    private final String value;
    AuthConstant(String value) {
        this.value = value;
    }
}

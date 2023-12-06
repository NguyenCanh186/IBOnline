package com.vmg.ibo.core.constant;

import lombok.Getter;

@Getter
public enum RoleConstant {
    ENABLE(1),
    LOCKED(2),
    MERCHANT_NOT_DEFAULT(0),
    MERCHANT_DEFAULT (1);

    private final int value;
    RoleConstant(int value) {
        this.value = value;
    }
}

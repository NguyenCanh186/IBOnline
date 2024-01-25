package com.vmg.ibo.deal.constant;

import lombok.Getter;

@Getter
public enum DealConstant {
    PROCESSING("0"),
    SUCCESS("1"),
    FAIL("2"),
    ;

    private final String value;

    DealConstant(String value) {
        this.value = value;
    }
}

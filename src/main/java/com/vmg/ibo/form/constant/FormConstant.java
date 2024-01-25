package com.vmg.ibo.form.constant;

import lombok.Getter;

@Getter
public enum FormConstant {
    INIT("0"),
    PROCESSING("1"),
    SUCCESS("2"),
    FAIL("3"),
    ;

    private final String value;

    FormConstant(String value) {
        this.value = value;
    }
}

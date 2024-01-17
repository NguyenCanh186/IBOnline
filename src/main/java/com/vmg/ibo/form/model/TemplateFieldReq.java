package com.vmg.ibo.form.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TemplateFieldReq {
    private Long idTemplateField;
    private String value;
}

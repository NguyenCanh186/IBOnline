package com.vmg.ibo.form.dto;

import lombok.Data;

@Data
public class FormFieldDTO {
    private Long id;
    private Long templateFieldId;
    private String templateFieldName;
    private String value;

}

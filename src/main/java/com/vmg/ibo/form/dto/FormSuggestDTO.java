package com.vmg.ibo.form.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FormSuggestDTO {
    private Long id;
    private Date createdAt;
    private List<FormFieldDTO> formFields;
}

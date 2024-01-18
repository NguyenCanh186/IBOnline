package com.vmg.ibo.form.dto;

import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.entity.FormField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FormDTO {
    private Long id;
    private Date createdAt;
    private List<FormFieldDTO> formFields;
    private List<FormDTO> suggestLatest;
}

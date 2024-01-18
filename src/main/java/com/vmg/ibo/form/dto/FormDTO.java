package com.vmg.ibo.form.dto;

import com.vmg.ibo.core.model.dto.UserDTO;
import com.vmg.ibo.form.entity.Template;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FormDTO {
    private Long id;
    private Date createdAt;
    private TemplateDTO template;
    private UserDTO user;
    private List<FormFieldDTO> formFields;
    private List<FormSuggestDTO> suggestLatest;
}

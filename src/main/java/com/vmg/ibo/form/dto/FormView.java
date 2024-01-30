package com.vmg.ibo.form.dto;

import com.vmg.ibo.core.model.dto.UserDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FormView {
    private Long id;
    private Date createdAt;
    private TemplateDTO template;
    private List<FormFieldDTO> formFields;
    private String codeDemand;
}

package com.vmg.ibo.form.dto;

import com.vmg.ibo.form.entity.TemplateField;
import lombok.Data;

import java.util.List;

@Data
public class TemplateDTO {
    private Long id;
    private String name;
    private String slug;
    private String tag;
    private Integer type;
    private String image;
    private List<TemplateField> templateFields;
}

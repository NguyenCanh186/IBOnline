package com.vmg.ibo.form.dto;

import lombok.Data;

@Data
public class TemplateDTO {
    private Long id;
    private String name;
    private String slug;
    private String tag;
    private Integer type;
    private String image;
}

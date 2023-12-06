package com.vmg.ibo.core.model.dto;

import com.vmg.ibo.core.action.Delete;
import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OptionDTO {
    private Long id;
    @NotEmpty(message = "Id không được để trống!", groups = {Delete.class})
    private String ids;
    @NotEmpty(message = "Key không được để trống!", groups = {Insert.class, Update.class})
    private String key;
    @NotEmpty(message = "Value không được để trống!", groups = {Insert.class, Update.class})
    private String value;
    private String group;
    private String description;
}

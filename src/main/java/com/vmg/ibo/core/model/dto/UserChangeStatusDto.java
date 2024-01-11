package com.vmg.ibo.core.model.dto;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserChangeStatusDto {
    @NotNull(message = "Trạng thái bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Integer status;
}

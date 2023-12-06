package com.vmg.ibo.core.model.dto;

import com.vmg.ibo.core.action.Delete;
import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoleDTO {
    private Long id;
    @NotEmpty(message = "Id không được để trống!", groups = {Delete.class})
    private String ids;
    @NotEmpty(message = "Mã vai trò không được để trống!", groups = {Insert.class, Update.class})
    private String code;
    @NotEmpty(message = "Tên vai trò không được để trống!", groups = {Insert.class, Update.class})
    private String name;
    private String description;
    @NotNull(message = "Trạng thái bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Integer status;
    private Boolean merchantDefault;
    private List<Long> permissionIds;
}

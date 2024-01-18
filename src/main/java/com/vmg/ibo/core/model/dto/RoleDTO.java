package com.vmg.ibo.core.model.dto;

import com.vmg.ibo.core.action.Delete;
import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RoleDTO {
    private Long id;
    @NotEmpty(message = "Id không được để trống!", groups = {Delete.class})
    private String ids;
    @NotEmpty(message = "Mã vai trò không được để trống!", groups = {Insert.class, Update.class})
    @Size(max = 50, message = "Mã vai trò không được vượt quá 50 ký tự!", groups = {Insert.class, Update.class})
    private String code;
    @NotEmpty(message = "Tên vai trò không được để trống!", groups = {Insert.class, Update.class})
    @Size(max = 50, message = "Tên vai trò không được vượt quá 50 ký tự!", groups = {Insert.class, Update.class})
    private String name;
    @Size(max = 100, message = "Mô tả không được vượt quá 100 ký tự!", groups = {Insert.class, Update.class})
    private String description;
    @NotNull(message = "Trạng thái bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Integer status;
    private Boolean merchantDefault;
    private List<Long> permissionIds;
}

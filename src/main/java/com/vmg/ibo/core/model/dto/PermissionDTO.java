package com.vmg.ibo.core.model.dto;

import com.vmg.ibo.core.action.Delete;
import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class PermissionDTO {
    private Long id;
    @NotEmpty(message = "Id không được để trống!", groups = {Delete.class})
    private String ids;
    @NotEmpty(message = "Mã quyền không được để trống!", groups = {Insert.class, Update.class})
    private String code;
    @NotEmpty(message = "Tên quyền không được để trống!", groups = {Insert.class, Update.class})
    private String name;
    @NotEmpty(message = "Đường dẫn bắt buộc nhập", groups = {Insert.class, Update.class})
    private String slug;
    @NotNull(message = "Quyền ẩn bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Boolean hidden;
    private String componentName;
    private String icon;
    private Long parentId;
    private Boolean hasChild;
    @NotNull(message = "Thứ tự bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Integer order;
    private String description;
    private String groupId;
    @NotNull(message = "Trạng thái bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Integer status;
    private Date createdAt;
    private Long createdByUserId;
    private String createdBy;
    private String groupName;
}

package com.vmg.ibo.core.model.dto;

import com.vmg.ibo.core.action.Delete;
import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import com.vmg.ibo.customer.model.UserDetail;
import com.vmg.ibo.customer.model.customer.FileUpload;
import com.vmg.ibo.form.entity.Form;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserDTO {
    private Long id;
    @NotEmpty(message = "Id không được để trống!", groups = {Delete.class})
    private String ids;
    @NotNull(message = "Trạng thái bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Integer status;
    @NotNull(message = "Email bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String email;
    @NotNull(message = "Họ và tên bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String name;
    @NotNull(message = "Sđt bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String phone;
    private List<Long> roleIds;
    private List<RoleDTO> roles;
    private UserDetail userDetail;
    private List<FileUpload> files;
    private List<Form> forms;
}

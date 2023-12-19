package com.vmg.ibo.core.model.customer;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class RegisterModel {
    @NotNull(message = "Email bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String email;
    @NotNull(message = "Mật khẩu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String password;
    @NotNull(message = "Xác nhận mật khẩu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String confirmPassword;

    public RegisterModel(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public RegisterModel() {
    }
}

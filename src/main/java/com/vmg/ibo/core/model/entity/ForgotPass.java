package com.vmg.ibo.core.model.entity;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class ForgotPass {
    @NotNull(message = "Vui lòng nhập mã xác nhận", groups = {Insert.class, Update.class})
    private String code;
    @NotNull(message = "Vui lòng nhập mật khẩu", groups = {Insert.class, Update.class})
    private String password;
    @NotNull(message = "Vui lòng nhập lại mật khẩu", groups = {Insert.class, Update.class})
    private String confirmPassword;
    public ForgotPass() {
    }

    public ForgotPass(String code, String password, String confirmPassword) {
        this.code = code;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}

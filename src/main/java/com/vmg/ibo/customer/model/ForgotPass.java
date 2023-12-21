package com.vmg.ibo.customer.model;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class ForgotPass {
    @NotNull(message = "Vui lòng nhập mã xác nhận", groups = {Insert.class, Update.class})
    private String code;
    @NotNull(message = "Vui lòng nhập mật khẩu", groups = {Insert.class, Update.class})
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]{8,}$",
            message = "Mật khẩu phải có ít nhất 8 ký tự, bao gồm ít nhất một chữ thường, một chữ hoa, một số, và một ký tự đặc biệt.", groups = {Insert.class, Update.class})
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

package com.vmg.ibo.customer.model.customer;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterModel {
    @NotNull(message = "Email bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 64, message = "Tên email không dài quá 64 ký tự", groups = {Insert.class, Update.class})
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$",
            message = "Sai định dạng email, vui lòng nhập lại.", groups = {Insert.class, Update.class})
    private String email;

    @NotNull(message = "Mật khẩu bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 128, message = "Bạn đã nhập quá 128 ký tự cho phép. Vui lòng nhập lại", groups = {Insert.class, Update.class})
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]{8,}$",
            message = "Mật khẩu phải có ít nhất 8 ký tự, bao gồm ít nhất một chữ thường, một chữ hoa, một số, và một ký tự đặc biệt.", groups = {Insert.class, Update.class})
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

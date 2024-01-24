package com.vmg.ibo.core.model.dto;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import com.vmg.ibo.core.config.exception.WebServiceException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Data
public class ChangePasswordRequest {

    @NotBlank(message = "{error.invalid-input}")
    private String oldPassword;

    @NotBlank(message = "{error.invalid-input}")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=])[A-Za-z\\d@#$%^&+=]{8,}$", message = "{user.error.new-password.wrong-format}")
    @Size(max = 128, message = "Bạn đã nhập quá 128 ký tự cho phép. Vui lòng nhập lại.", groups = {Insert.class, Update.class})
    private String newPassword;

    @NotBlank(message = "{error.invalid-input}")
    private String confirmPassword;

    private Boolean isRestPass;

    public boolean isValid(PasswordEncoder encoder, String password) {
        String newPass = this.newPassword.trim();
        if (Objects.isNull(this.isRestPass) || !this.isRestPass) {
            if (!encoder.matches(this.oldPassword.trim(), password)) {
                throw new WebServiceException(HttpStatus.OK.value(),409, "user.error.invalid-password");
            }
            if (Objects.nonNull(this.oldPassword) && newPass.equals(this.oldPassword.trim())) {
                throw new WebServiceException(HttpStatus.OK.value(),409, "user.error.invalid-password-2");
            }
        }
        if (!newPass.equals(this.confirmPassword.trim())) {
            throw new WebServiceException(HttpStatus.OK.value(),409, "user.error.invalid-confirm-password");
        }
        return true;
    }
}

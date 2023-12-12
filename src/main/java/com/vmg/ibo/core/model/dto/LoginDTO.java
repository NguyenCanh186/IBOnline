package com.vmg.ibo.core.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {
    @NotNull(message = "Email không được để trống")
    private String email;

    @NotNull(message = " không được để trống")
    private String password;

    public String getEmail() {
        return this.email.trim().toLowerCase();
    }
}

package com.vmg.ibo.core.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {
    @NotNull(message = "Tên đăng nhập không được để trống")
    private String username;

    @NotNull(message = " không được để trống")
    private String password;

    public String getUsername() {
        return this.username.trim().toLowerCase();
    }
}

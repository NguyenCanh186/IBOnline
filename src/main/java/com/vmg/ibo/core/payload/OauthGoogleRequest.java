package com.vmg.ibo.core.payload;

import java.util.Objects;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class OauthGoogleRequest {

    @NotEmpty private String code;

    @NotEmpty private String redirectUri;

    public void validate() {
        if (Objects.isNull(code) || Objects.isNull(redirectUri)) {
            throw new IllegalArgumentException("code or redirectUri is null");
        }
    }
}
package com.vmg.ibo.core.model.dto;

import com.vmg.ibo.core.model.entity.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ProfileResponse {
    private Long id;
    private String username;
    private String avatar;
    private Set<Role> roles;
    private Integer channelId;
    private Boolean isResetPass;
    private List<String> permissions;
}

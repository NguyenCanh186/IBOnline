package com.vmg.ibo.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtDTO {
    private String token;
    private Long id;
    private String username;
    private Map<String, Object> params;
}

package com.vmg.ibo.core.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class ExistedResponse {
    private boolean exists;
}

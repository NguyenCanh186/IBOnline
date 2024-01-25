package com.vmg.ibo.deal.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DealDTO {
    private Long id;
    @NotNull(message = "Trạng thái không được để trống")
    private Integer status;
}

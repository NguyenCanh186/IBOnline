package com.vmg.ibo.deal.model.dto;

import com.vmg.ibo.core.model.dto.UserDetailDTO;
import com.vmg.ibo.form.dto.FormDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DealDetail {
    private Long id;
    private CustomerInfo firstCustomerInfo;
    private CustomerInfo secondCustomerInfo;
}

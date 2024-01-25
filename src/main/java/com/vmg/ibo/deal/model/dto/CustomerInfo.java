package com.vmg.ibo.deal.model.dto;

import com.vmg.ibo.core.model.dto.UserDetailDTO;
import com.vmg.ibo.form.dto.FormDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerInfo {
    private String email;
    private String phone;
    private UserDetailDTO userAdditionalInfo;
    private FormDTO form;
    private Date connectionDate;
}

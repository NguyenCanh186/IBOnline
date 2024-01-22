package com.vmg.ibo.form.model;

import lombok.Data;

@Data
public class FormUpdateStatusReq {
    private Integer status;
    private Long partnerId;
}

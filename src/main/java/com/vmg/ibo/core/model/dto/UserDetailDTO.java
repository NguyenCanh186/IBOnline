package com.vmg.ibo.core.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDetailDTO {
    private String customerCode;
    private Boolean isCustomerPersonal;
    private String address;
    private String CINumber;
    private Date dateOfBirth;
    private String businessName;
    private String codeTax;
    private String codeReg;
    private String contactName;
    private String mainBusiness;
    private String title;
    private Long capitalSize;
    private String description;
}

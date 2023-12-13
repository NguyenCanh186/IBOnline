package com.vmg.ibo.core.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "SYS_USERS_DETAIL")
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private Long idUser;
}

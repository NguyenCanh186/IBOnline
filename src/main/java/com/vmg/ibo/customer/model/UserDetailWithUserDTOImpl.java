package com.vmg.ibo.customer.model;

import com.vmg.ibo.customer.model.customer.FileUpload;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class UserDetailWithUserDTOImpl {
    private Long id;
    private Date createdAt;
    private String customerCode;
    private Boolean isCustomerPersonal;
    private String address;
    private String ciNumber;
    private Date dateOfBirth;
    private String businessName;
    private String codeTax;
    private String codeReg;
    private String contactName;
    private String mainBusiness;
    private String title;
    private Long capitalSize;
    private String description;
    private Integer status;
    private String email;
    private String phone;
    private Long mostRecentYearRevenue;
    private Long mostRecentYearProfit;
    private Long propertyStructure;
    private Long debtStructure;
    private String name;

    private List<FileUpload> fileUploads;

    public UserDetailWithUserDTOImpl(UserDetailWithUserDTO userDetail, List<FileUpload> fileUploads) {
        this.id = userDetail.getId();
        this.createdAt = userDetail.getCreatedAt();
        this.customerCode = userDetail.getCustomerCode();
        this.isCustomerPersonal = userDetail.getIsCustomerPersonal();
        this.address = userDetail.getAddress();
        this.ciNumber = userDetail.getCINumber();
        this.dateOfBirth = userDetail.getDateOfBirth();
        this.businessName = userDetail.getBusinessName();
        this.codeTax = userDetail.getCodeTax();
        this.codeReg = userDetail.getCodeReg();
        this.contactName = userDetail.getContactName();
        this.mainBusiness = userDetail.getMainBusiness();
        this.title = userDetail.getTitle();
        this.capitalSize = userDetail.getCapitalSize();
        this.description = userDetail.getDescription();
        this.status = userDetail.getStatus();
        this.email = userDetail.getEmail();
        this.phone = userDetail.getPhone();
        this.mostRecentYearRevenue = userDetail.getMostRecentYearRevenue();
        this.mostRecentYearProfit = userDetail.getMostRecentYearProfit();
        this.propertyStructure = userDetail.getPropertyStructure();
        this.debtStructure = userDetail.getDebtStructure();
        this.name = userDetail.getName();
        this.fileUploads = fileUploads;
    }

    public UserDetailWithUserDTOImpl(UserDetailWithUserDTO userDetail) {
        this.id = userDetail.getId();
        this.createdAt = userDetail.getCreatedAt();
        this.customerCode = userDetail.getCustomerCode();
        this.isCustomerPersonal = userDetail.getIsCustomerPersonal();
        this.address = userDetail.getAddress();
        this.ciNumber = userDetail.getCINumber();
        this.dateOfBirth = userDetail.getDateOfBirth();
        this.businessName = userDetail.getBusinessName();
        this.codeTax = userDetail.getCodeTax();
        this.codeReg = userDetail.getCodeReg();
        this.contactName = userDetail.getContactName();
        this.mainBusiness = userDetail.getMainBusiness();
        this.title = userDetail.getTitle();
        this.capitalSize = userDetail.getCapitalSize();
        this.description = userDetail.getDescription();
        this.status = userDetail.getStatus();
        this.email = userDetail.getEmail();
        this.phone = userDetail.getPhone();
        this.mostRecentYearRevenue = userDetail.getMostRecentYearRevenue();
        this.mostRecentYearProfit = userDetail.getMostRecentYearProfit();
        this.propertyStructure = userDetail.getPropertyStructure();
        this.debtStructure = userDetail.getDebtStructure();
        this.name = userDetail.getName();
    }

    public UserDetailWithUserDTOImpl() {
    }
}

package com.vmg.ibo.customer.model;

import java.util.Date;

public interface UserDetailWithUserDTO {
    Long getId();
    Date getCreatedAt();
    String getCustomerCode();
    Boolean getIsCustomerPersonal();
    String getAddress();
    String getCINumber();
    Date getDateOfBirth();
    String getBusinessName();
    String getCodeTax();
    String getCodeReg();
    String getContactName();
    String getMainBusiness();
    String getTitle();
    Long getCapitalSize();
    String getDescription();
    Integer getStatus();
    String getEmail();
    String getPhone();
    Long getMostRecentYearRevenue();
    Long getMostRecentYearProfit();
    Long getPropertyStructure();
    Long getDebtStructure();
    String getName();
}

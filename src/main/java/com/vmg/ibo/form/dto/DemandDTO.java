package com.vmg.ibo.form.dto;

import java.util.Date;

public interface DemandDTO {
    int getFormId();
    String getCodeDemand();
    String getDemandName();
    String getDemandType();
    String getCustomerName();
    Date getCreatedDate();
    int getStatus();
}

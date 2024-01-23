package com.vmg.ibo.form.dto;

import java.util.Date;

public interface DemandDTO {
    Integer getId();
    String getCodeDemand();
    String getDemandName();
    Integer getDemandType();
    String getCustomerName();
    Date getCreatedAt();
    Integer getStatus();
}

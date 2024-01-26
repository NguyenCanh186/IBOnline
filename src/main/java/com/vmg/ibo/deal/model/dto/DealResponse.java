package com.vmg.ibo.deal.model.dto;

import java.util.Date;

public interface DealResponse {
    Long getId();
    String getCode();
    String getFirstCompany();
    String getSecondCompany();
    String getFirstContact();
    String getSecondContact();
    Boolean getFirstType();
    Boolean getSecondType();
    String getFirstForm();
    String getSecondForm();
    String getCoordinator();
    Date getConnectionDate();
    Integer getStatus();
}

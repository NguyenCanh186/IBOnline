package com.vmg.ibo.form.model;

import com.vmg.ibo.core.base.BaseFilter;
import lombok.Data;

import java.util.List;

@Data
public class DemandReq extends BaseFilter {
    private String demandName = "";
    private List<Integer> demandType;
    private String createdAt = "";
    private List<Integer> status;
    private String query = "";

    public String getDemandName() {
        return demandName.trim().toLowerCase().replace("%", "\\%");
    }

    public String getCreatedAt() {
        return createdAt.trim().toLowerCase().replace("%", "\\%");
    }

    public String getQuery() {
        return query.trim().toLowerCase().replace("%", "\\%");
    }
}

package com.vmg.ibo.deal.model.dto;

import com.vmg.ibo.core.base.BaseFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DealFilter extends BaseFilter {
    private String query="";
    private String firstForm="";
    private String secondForm="";
    private String coordinator="";
    private String date="";
    private Integer status;

    public String getQuery() {
        return query.trim().toLowerCase().replace("%", "\\%");
    }

    public String getFirstForm() {
        return firstForm.trim().toLowerCase().replace("%", "\\%");
    }

    public String getSecondForm() {
        return secondForm.trim().toLowerCase().replace("%", "\\%");
    }

    public String getCoordinator() {
        return coordinator.trim().toLowerCase().replace("%", "\\%");
    }

    public String getDate() {
        return date.trim().toLowerCase().replace("%", "\\%");
    }
}

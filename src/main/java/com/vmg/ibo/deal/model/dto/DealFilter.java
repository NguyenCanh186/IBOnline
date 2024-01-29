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
        return query.trim();
    }

    public String getFirstForm() {
        return firstForm.trim();
    }

    public String getSecondForm() {
        return secondForm.trim();
    }

    public String getCoordinator() {
        return coordinator.trim();
    }

    public String getDate() {
        return date.trim();
    }
}

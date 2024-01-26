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
}

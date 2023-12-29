package com.vmg.ibo.form_demand.model;

import lombok.Data;

import java.util.List;
@Data
public class DataSummaryTableRes {
    private String name;
    private DataSummaryTable dataSummaryTable;
    private List<DataSummaryTable> dataSummaryTableList;
    private Integer statusConnect;
}

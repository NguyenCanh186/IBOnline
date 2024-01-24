package com.vmg.ibo.form.model;

import lombok.Data;

import java.util.List;

@Data
public class DemandReq {
    private String demandName = "";
    private List<Integer> demandType;
    private String createdAt = "";
    private List<Integer> status;
    private String query = "";
}

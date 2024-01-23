package com.vmg.ibo.financial_report.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FinancialReportDTO {
    private Long id;
    private Long revenue;
    private Long profit;
    private Long asset;
    private Long debt;
    private Integer year;
    private Integer quarter;
    private List<MultipartFile> files;
}

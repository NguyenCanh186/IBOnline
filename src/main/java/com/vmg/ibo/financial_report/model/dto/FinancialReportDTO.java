package com.vmg.ibo.financial_report.model.dto;

import com.vmg.ibo.core.model.dto.FileUploadDto;
import com.vmg.ibo.customer.model.customer.FileUpload;
import lombok.Data;

import java.util.List;

@Data
public class FinancialReportDTO {
    private Long id;
    private String title;
    private Long revenue;
    private Long profit;
    private Long asset;
    private Long debt;
    private Integer year;
    private Integer quarter;
    private List<FileUploadDto> files;
}

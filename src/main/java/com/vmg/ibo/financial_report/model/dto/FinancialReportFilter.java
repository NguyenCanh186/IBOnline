package com.vmg.ibo.financial_report.model.dto;

import com.vmg.ibo.core.model.entity.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class FinancialReportFilter {
    private Integer year;
    private Integer quarter;
    private User user;
}

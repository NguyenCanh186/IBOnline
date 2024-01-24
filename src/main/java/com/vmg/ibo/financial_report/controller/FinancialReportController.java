package com.vmg.ibo.financial_report.controller;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.financial_report.model.dto.FinancialReportRequest;
import com.vmg.ibo.financial_report.service.IFinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/financial-report")
public class FinancialReportController extends BaseService {
    @Autowired
    private IFinancialReportService financialReportService;

    @PostMapping("/create")
    public Result<?> createFinancialReport(@Validated(Insert.class) @ModelAttribute FinancialReportRequest request) {
        return Result.success(financialReportService.create(request));
    }
}

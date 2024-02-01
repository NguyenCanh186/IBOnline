package com.vmg.ibo.financial_report.controller;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.core.service.user.IUserService;
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

    @Autowired
    private IUserService userService;

    @PostMapping("/create")
    public Result<?> createFinancialReport(@Validated(Insert.class) @ModelAttribute FinancialReportRequest request) {
        return Result.success(financialReportService.create(request));
    }

    @PostMapping("/check-quarter")
    public Result<?> checkQuarter(@Validated(Insert.class) @ModelAttribute FinancialReportRequest request) {
        if (request.getType() == 1 && !userService.checkQuarter(request)) {
            return Result.result(400, "Đã tồn tại báo cáo tài chính năm " + request.getYear(), false);
        } else if (request.getType() == 0 && !userService.checkQuarter(request)){
            return Result.result(400, "Đã tồn tại báo cáo tài chính quý " + request.getQuarter() + " năm " + request.getYear(), false);
        } else {
            return Result.result(200, "Ok", true);
        }
    }
}

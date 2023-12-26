package com.vmg.ibo.form_demand.controller;


import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.form_demand.model.financial_investment.FinancialInvestmentReq;
import com.vmg.ibo.form_demand.model.form_buy_fund_certificate_consulting.FormBuyFundCertificateConsultingReq;
import com.vmg.ibo.form_demand.service.FinancialInvestmentService;
import com.vmg.ibo.form_demand.service.FormBuyFundCertificateConsultingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/form")
public class FormController {
    @Autowired
    private FormBuyFundCertificateConsultingService formBuyFundCertificateConsultingService;

    @Autowired
    private FinancialInvestmentService financialInvestmentService;

    @PostMapping("/buy-fund-certificate-consulting")
    public Result<?> createFormBuyFundCertificateConsulting(@Validated(Insert.class) @RequestBody FormBuyFundCertificateConsultingReq formBuyFundCertificateConsultingReq){
        formBuyFundCertificateConsultingService.saveFormBuyFundCertificateConsulting(formBuyFundCertificateConsultingReq);
        return Result.success("Thêm mới thành công form mua dịch vụ tư vấn chứng chỉ quỹ");
    }
    @PostMapping("/financial-investment")
    public Result<?> createFinancialInvestment(@Validated(Insert.class) @RequestBody FinancialInvestmentReq financialInvestmentReq) {
        financialInvestmentService.createFinancialInvestment(financialInvestmentReq);
        return Result.success("Thêm mới thành công form đầu tư tài chính");
    }
}

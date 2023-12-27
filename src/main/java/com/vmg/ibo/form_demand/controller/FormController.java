package com.vmg.ibo.form_demand.controller;


import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.form_demand.model.buy_bond_consulting.BuyBondConsulting;
import com.vmg.ibo.form_demand.model.buy_bond_consulting.BuyBondConsultingReq;
import com.vmg.ibo.form_demand.model.buy_stock_consulting.BuyStockConsultingReq;
import com.vmg.ibo.form_demand.model.financial_investment.FinancialInvestmentReq;
import com.vmg.ibo.form_demand.model.form_buy_fund_certificate_consulting.FormBuyFundCertificateConsultingReq;
import com.vmg.ibo.form_demand.model.sell_​​bonds.SellBondsReq;
import com.vmg.ibo.form_demand.model.sell_​​fund_certificates.InvestmentPortfolioReq;
import com.vmg.ibo.form_demand.service.*;
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

    @Autowired
    private BuyBondConsultingService buyBondConsultingService;

    @Autowired
    private SellBondsService sellBondsService;

    @Autowired
    private BuyStockConsultingService buyStockConsultingService;

    @Autowired
    private InvestmentPortfolioService investmentPortfolioService;

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
    @PostMapping("/buy-bond-consulting")
    public Result<?> createBuyBondConsulting(@Validated(Insert.class) @RequestBody BuyBondConsultingReq buyBondConsultingReq){
        buyBondConsultingService.createBuyBondConsulting(buyBondConsultingReq);
        return Result.success("Thêm mới thành công form mua dịch vụ tư vấn trái phiếu");
    }

    @PostMapping("/sell-bonds")
    public Result<?> createSellBonds(@Validated(Insert.class) @RequestBody SellBondsReq sellBondsReq){
        sellBondsService.createSellBonds(sellBondsReq);
        return Result.success("Thêm mới thành công form bán trái phiếu");
    }
    @PostMapping("/buy-stock-consulting")
    public Result<?> createBuyStockConsulting(@Validated(Insert.class) @RequestBody BuyStockConsultingReq buyStockConsultingReq){
        buyStockConsultingService.createBuyStockConsulting(buyStockConsultingReq);
        return Result.success("Thêm mới thành công form mua dịch vụ tư vấn cổ phiếu");
    }
    @PostMapping("/investment-portfolio")
    public Result<?> createInvestmentPortfolio(@Validated(Insert.class) @RequestBody InvestmentPortfolioReq investmentPortfolioReq){
        investmentPortfolioService.createInvestmentPortfolio(investmentPortfolioReq);
        return Result.success("Thêm mới thành công form bán chứng chỉ quỹ");
    }
}

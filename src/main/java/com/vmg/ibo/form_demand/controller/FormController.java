package com.vmg.ibo.form_demand.controller;


import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.base.Result;
import com.vmg.ibo.form_demand.model.ConnectReq;
import com.vmg.ibo.form_demand.model.DemandFormReq;
import com.vmg.ibo.form_demand.model.buy_bond_consulting.BuyBondConsulting;
import com.vmg.ibo.form_demand.model.buy_bond_consulting.BuyBondConsultingReq;
import com.vmg.ibo.form_demand.model.buy_stock_consulting.BuyStockConsultingReq;
import com.vmg.ibo.form_demand.model.financial_investment.FinancialInvestmentReq;
import com.vmg.ibo.form_demand.model.form_buy_fund_certificate_consulting.FormBuyFundCertificateConsultingReq;
import com.vmg.ibo.form_demand.model.sell_stocks.SellStocksReq;
import com.vmg.ibo.form_demand.model.sell_​​bonds.SellBondsReq;
import com.vmg.ibo.form_demand.model.sell_​​fund_certificates.InvestmentPortfolioReq;
import com.vmg.ibo.form_demand.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private SellStocksService sellStocksService;

    @Autowired
    private DemandFormService demandFormService;

    @Autowired
    private DataSummaryTableService dataSummaryTableService;

    @PostMapping("/buy-fund-certificate-consulting")
    public Result<?> createFormBuyFundCertificateConsulting(@Validated(Insert.class) @RequestBody FormBuyFundCertificateConsultingReq formBuyFundCertificateConsultingReq){
        return Result.success("Thêm mới thành công form mua dịch vụ tư vấn chứng chỉ quỹ", formBuyFundCertificateConsultingService.saveFormBuyFundCertificateConsulting(formBuyFundCertificateConsultingReq));
    }
    @PostMapping("/financial-investment")
    public Result<?> createFinancialInvestment(@Validated(Insert.class) @RequestBody FinancialInvestmentReq financialInvestmentReq) {
        return Result.success("Thêm mới thành công form đầu tư tài chính", financialInvestmentService.createFinancialInvestment(financialInvestmentReq));
    }
    @PostMapping("/buy-bond-consulting")
    public Result<?> createBuyBondConsulting(@Validated(Insert.class) @RequestBody BuyBondConsultingReq buyBondConsultingReq){
        return Result.success("Thêm mới thành công form mua dịch vụ tư vấn trái phiếu", buyBondConsultingService.createBuyBondConsulting(buyBondConsultingReq));
    }

    @PostMapping("/sell-bonds")
    public Result<?> createSellBonds(@Validated(Insert.class) @RequestBody SellBondsReq sellBondsReq){
        return Result.success("Thêm mới thành công form bán trái phiếu", sellBondsService.createSellBonds(sellBondsReq));
    }
    @PostMapping("/buy-stock-consulting")
    public Result<?> createBuyStockConsulting(@Validated(Insert.class) @RequestBody BuyStockConsultingReq buyStockConsultingReq){
        return Result.success("Thêm mới thành công form mua dịch vụ tư vấn cổ phiếu", buyStockConsultingService.createBuyStockConsulting(buyStockConsultingReq));
    }
    @PostMapping("/investment-portfolio")
    public Result<?> createInvestmentPortfolio(@Validated(Insert.class) @RequestBody InvestmentPortfolioReq investmentPortfolioReq){;
        return Result.success("Thêm mới thành công form bán chứng chỉ quỹ", investmentPortfolioService.createInvestmentPortfolio(investmentPortfolioReq));
    }
    @PostMapping("/sell-stocks")
    public Result<?> createSellStocks(@Validated(Insert.class) @RequestBody SellStocksReq stocksReq){
        return Result.success("Thêm mới thành công form bán cổ phiếu", sellStocksService.createSellStocks(stocksReq));
    }

    @GetMapping("/find-data-table/{id}")
    public Result<?> findDataTable(@PathVariable("id") Long id){
        return Result.success("Lấy dữ liệu thành công", dataSummaryTableService.dataSummaryTableRes(id));
    }

    @PostMapping("/connect-partner")
    public Result<?> createDemandForm(@Validated(Insert.class) @RequestBody ConnectReq connectReq){
        return Result.success("Kết nối thành công", demandFormService.connect(connectReq));
    }

    @GetMapping("/demand-detail/{id}")
    public Result<?> demandFormDetail(@PathVariable("id") Long id){
        return Result.success("Lấy dữ liệu thành công", dataSummaryTableService.getDataSummaryTableResByIdDemandForm(id));
    }

}

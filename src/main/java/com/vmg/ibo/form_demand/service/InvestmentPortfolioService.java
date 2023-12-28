package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.demand.entity.Demand;
import com.vmg.ibo.demand.service.IDemandService;
import com.vmg.ibo.form_demand.model.DataSummaryTable;
import com.vmg.ibo.form_demand.model.sell_​​fund_certificates.InvestmentPortfolio;
import com.vmg.ibo.form_demand.model.sell_​​fund_certificates.InvestmentPortfolioReq;
import com.vmg.ibo.form_demand.model.sell_​​fund_certificates.SellFundCertificates;
import com.vmg.ibo.form_demand.repository.IInvestmentPortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InvestmentPortfolioService extends BaseService {
    @Autowired
    private IInvestmentPortfolioRepository investmentPortfolioRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDemandService demandService;
    @Autowired
    private SellFundCertificatesService sellFundCertificatesService;
    @Autowired
    private DataSummaryTableService dataSummaryTableService;
    public List<DataSummaryTable> createInvestmentPortfolio(InvestmentPortfolioReq investmentPortfolioReq) {
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
        User user = userService.FindUserById(idUser);
        Demand demand = demandService.getDemandById(5L);
        InvestmentPortfolio investmentPortfolio = new InvestmentPortfolio();
        investmentPortfolio.setCreatedAt(new Date());
        investmentPortfolio.setUpdatedAt(new Date());
        investmentPortfolio.setCreatedByUserId(idUser);
        investmentPortfolio.setCreatedBy(getCurrentUser().getUsername());
        investmentPortfolio.setDemand(demand);
        investmentPortfolio.setUser(user);
        investmentPortfolio.setFundName(investmentPortfolioReq.getFundName());
        investmentPortfolio.setFundCertificateCodeWantToSell(investmentPortfolioReq.getFundCertificateCodeWantToSell());
        investmentPortfolio.setNumberOfFundCertificateWantToSell(investmentPortfolioReq.getNumberOfFundCertificateWantToSell());
        investmentPortfolio.setDesiredSellingPriceOfFundCertificates(investmentPortfolioReq.getDesiredSellingPriceOfFundCertificates());
        investmentPortfolio.setDayTrading(investmentPortfolioReq.getDayTrading());
        InvestmentPortfolio investmentPortfolio1 = investmentPortfolioRepository.save(investmentPortfolio);
        List<SellFundCertificates> sellFundCertificatesList = new ArrayList<>();
        for (int i = 0; i < investmentPortfolioReq.getSellFundCertificates().size(); i++) {
            investmentPortfolioReq.getSellFundCertificates().get(i).setInvestmentPortfolio(investmentPortfolio1);
            SellFundCertificates sellFundCertificates = sellFundCertificatesService.save(investmentPortfolioReq.getSellFundCertificates().get(i));
            sellFundCertificatesList.add(sellFundCertificates);
        }
        investmentPortfolio1.setSellFundCertificates(sellFundCertificatesList);
        investmentPortfolio.setTags("Chứng chỉ quỹ");
        investmentPortfolioRepository.save(investmentPortfolio1);
        DataSummaryTable dataSummaryTable = new DataSummaryTable();
        dataSummaryTable.setTags("Chứng chỉ quỹ");
        dataSummaryTable.setIdChildTable(investmentPortfolio1.getId());
        dataSummaryTable.setDemandId(5L);
        dataSummaryTable.setDemandType(demand.getType());
        dataSummaryTable.setCreatedDate(new Date());
        dataSummaryTable.setIdCustomer(idUser);
        dataSummaryTable.setFundName(investmentPortfolioReq.getFundName());
        dataSummaryTable.setFundCertificateCodeWantToSell(investmentPortfolioReq.getFundCertificateCodeWantToSell());
        dataSummaryTable.setNumberOfFundCertificateWantToSell(investmentPortfolioReq.getNumberOfFundCertificateWantToSell());
        dataSummaryTable.setDesiredSellingPriceOfFundCertificates(investmentPortfolioReq.getDesiredSellingPriceOfFundCertificates());
        dataSummaryTable.setDayTrading(investmentPortfolioReq.getDayTrading());
        dataSummaryTableService.save(dataSummaryTable);
        return dataSummaryTableService.getListByFilter(demand.getType() == 1 ? 2 : 1, investmentPortfolio1.getTags(), idUser);
    }
    public InvestmentPortfolio findById(Long id) {
        return investmentPortfolioRepository.findById(id).orElse(null);
    }
}

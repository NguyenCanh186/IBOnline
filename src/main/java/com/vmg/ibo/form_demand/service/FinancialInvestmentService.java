package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.demand.entity.Demand;
import com.vmg.ibo.demand.service.IDemandService;
import com.vmg.ibo.form_demand.model.DataSummaryTable;
import com.vmg.ibo.form_demand.model.financial_investment.FinancialInvestment;
import com.vmg.ibo.form_demand.model.financial_investment.FinancialInvestmentReq;
import com.vmg.ibo.form_demand.repository.IFinancialInvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FinancialInvestmentService extends BaseService {
    @Autowired
    private IUserService userService;

    @Autowired
    private IDemandService demandService;

    @Autowired
    private IFinancialInvestmentRepository financialInvestmentRepository;

    @Autowired
    private DataSummaryTableService dataSummaryTableService;

    public void createFinancialInvestment(FinancialInvestmentReq financialInvestmentReq) {
        FinancialInvestment financialInvestment = new FinancialInvestment();
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
        User user = userService.FindUserById(idUser);
        Demand demand = demandService.getDemandById(1L);
        financialInvestment.setCreatedAt(new Date());
        financialInvestment.setUpdatedAt(new Date());
        financialInvestment.setCreatedByUserId(idUser);
        financialInvestment.setCreatedBy(getCurrentUser().getUsername());
        financialInvestment.setDemand(demand);
        financialInvestment.setUser(user);
        financialInvestment.setEstimatedInvestmentTime(financialInvestmentReq.getEstimatedInvestmentTime());
        financialInvestment.setTags("Trái phiếu; Cổ phiếu; Chứng chỉ quỹ");
        financialInvestment.setMinimumInvestmentCapital(financialInvestmentReq.getMinimumInvestmentCapital());
        financialInvestment.setExpectedProfitRate(financialInvestmentReq.getExpectedProfitRate());
        financialInvestment.setLevelOfRiskTolerance(financialInvestmentReq.getLevelOfRiskTolerance());
        financialInvestment.setInvestmentObjective(financialInvestmentReq.getInvestmentObjective());
        FinancialInvestment financialInvestment1 = financialInvestmentRepository.save(financialInvestment);

        DataSummaryTable dataSummaryTable = new DataSummaryTable();
        dataSummaryTable.setIdChildTable(financialInvestment1.getId());
        dataSummaryTable.setDemandId(1L);
        dataSummaryTable.setDemandType(demand.getType());
        dataSummaryTable.setCreatedDate(new Date());
        dataSummaryTable.setIdCustomer(idUser);
        dataSummaryTable.setTags("Trái phiếu; Cổ phiếu; Chứng chỉ quỹ");
        dataSummaryTable.setMinimumInvestmentCapital(financialInvestmentReq.getMinimumInvestmentCapital());
        dataSummaryTable.setExpectedProfitRate(financialInvestmentReq.getExpectedProfitRate());
        dataSummaryTable.setLevelOfRiskTolerance(financialInvestmentReq.getLevelOfRiskTolerance());
        dataSummaryTable.setInvestmentObjective(financialInvestmentReq.getInvestmentObjective());
        dataSummaryTable.setEstimatedInvestmentTime(financialInvestmentReq.getEstimatedInvestmentTime());

        dataSummaryTableService.save(dataSummaryTable);
    }

    public FinancialInvestment findById(Long id) {
        return financialInvestmentRepository.findById(id).orElse(null);
    }
}

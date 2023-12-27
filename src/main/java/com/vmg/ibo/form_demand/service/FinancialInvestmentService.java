package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.demand.entity.Demand;
import com.vmg.ibo.demand.service.IDemandService;
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

    public void createFinancialInvestment(FinancialInvestmentReq financialInvestmentReq) {
        FinancialInvestment financialInvestment = new FinancialInvestment();
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
        User user = userService.FindUserById(idUser);
        Demand demand = demandService.getDemandById(financialInvestmentReq.getIdDemand());
        financialInvestment.setCreatedAt(new Date());
        financialInvestment.setUpdatedAt(new Date());
        financialInvestment.setCreatedByUserId(idUser);
        financialInvestment.setCreatedBy(getCurrentUser().getUsername());
        financialInvestment.setDemand(demand);
        financialInvestment.setUser(user);
        financialInvestment.setEstimatedInvestmentTime(financialInvestmentReq.getEstimatedInvestmentTime());
        financialInvestment.setMinimumInvestmentCapital(financialInvestmentReq.getMinimumInvestmentCapital());
        financialInvestment.setExpectedProfitRate(financialInvestmentReq.getExpectedProfitRate());
        financialInvestment.setLevelOfRiskTolerance(financialInvestmentReq.getLevelOfRiskTolerance());
        financialInvestment.setInvestmentObjective(financialInvestmentReq.getInvestmentObjective());
        financialInvestmentRepository.save(financialInvestment);
    }

    public FinancialInvestment findById(Long id) {
        return financialInvestmentRepository.findById(id).orElse(null);
    }
}

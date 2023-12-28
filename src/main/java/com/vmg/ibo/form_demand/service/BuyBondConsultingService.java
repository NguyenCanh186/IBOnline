package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.demand.entity.Demand;
import com.vmg.ibo.demand.service.IDemandService;
import com.vmg.ibo.form_demand.model.DataSummaryTable;
import com.vmg.ibo.form_demand.model.buy_bond_consulting.BuyBondConsulting;
import com.vmg.ibo.form_demand.model.buy_bond_consulting.BuyBondConsultingReq;
import com.vmg.ibo.form_demand.repository.IBuyBondConsultingRepository;
import com.vmg.ibo.form_demand.repository.IFinancialInvestmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BuyBondConsultingService extends BaseService {
    @Autowired
    private IBuyBondConsultingRepository buyBondConsultingRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDemandService demandService;

    @Autowired
    private DataSummaryTableService dataSummaryTableService;
    public void createBuyBondConsulting(BuyBondConsultingReq buyBondConsultingReq) {
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
        User user = userService.FindUserById(idUser);
        Demand demand = demandService.getDemandById(4L);
        BuyBondConsulting buyBondConsulting = new BuyBondConsulting();
        buyBondConsulting.setDemand(demand);
        buyBondConsulting.setTags("Trái phiếu");
        buyBondConsulting.setUser(user);
        buyBondConsulting.setCreatedAt(new Date());
        buyBondConsulting.setUpdatedAt(new Date());
        buyBondConsulting.setCreatedByUserId(idUser);
        buyBondConsulting.setCreatedBy(getCurrentUser().getUsername());
        buyBondConsulting.setBondType(buyBondConsultingReq.getBondType());
        buyBondConsulting.setBondCode(buyBondConsultingReq.getBondCode());
        buyBondConsulting.setBondTerm(buyBondConsultingReq.getBondTerm());
        buyBondConsulting.setNumberOfBondsWantToSell(buyBondConsultingReq.getNumberOfBondsWantToSell());
        buyBondConsulting.setInterestRate(buyBondConsultingReq.getInterestRate());
        buyBondConsulting.setInterestPaymentPeriod(buyBondConsultingReq.getInterestPaymentPeriod());
        buyBondConsulting.setAskingPrice(buyBondConsultingReq.getAskingPrice());
        buyBondConsulting.setOfferingPurpose(buyBondConsultingReq.getOfferingPurpose());
        buyBondConsulting.setCollateral(buyBondConsultingReq.getCollateral());
        BuyBondConsulting buyBondConsulting1 = buyBondConsultingRepository.save(buyBondConsulting);

        DataSummaryTable dataSummaryTable = new DataSummaryTable();
        dataSummaryTable.setTags("Trái phiếu");
        dataSummaryTable.setIdChildTable(buyBondConsulting1.getId());
        dataSummaryTable.setDemandId(4L);
        dataSummaryTable.setDemandType(demand.getType());
        dataSummaryTable.setCreatedDate(new Date());
        dataSummaryTable.setIdCustomer(idUser);
        dataSummaryTable.setBondType(buyBondConsultingReq.getBondType());
        dataSummaryTable.setBondCode(buyBondConsultingReq.getBondCode());
        dataSummaryTable.setBondTerm(buyBondConsultingReq.getBondTerm());
        dataSummaryTable.setNumberOfBondsWantToSell(buyBondConsultingReq.getNumberOfBondsWantToSell());
        dataSummaryTable.setInterestRate(buyBondConsultingReq.getInterestRate());
        dataSummaryTable.setInterestPaymentPeriod(buyBondConsultingReq.getInterestPaymentPeriod());
        dataSummaryTable.setAskingPrice(buyBondConsultingReq.getAskingPrice());
        dataSummaryTable.setOfferingPurpose(buyBondConsultingReq.getOfferingPurpose());
        dataSummaryTable.setCollateral(buyBondConsultingReq.getCollateral());
        dataSummaryTableService.save(dataSummaryTable);
    }
    public BuyBondConsulting findById(Long id) {
        return buyBondConsultingRepository.findById(id).orElse(null);
    }
}

package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.demand.entity.Demand;
import com.vmg.ibo.demand.service.IDemandService;
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
    public void createBuyBondConsulting(BuyBondConsultingReq buyBondConsultingReq) {
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
        User user = userService.FindUserById(idUser);
        Demand demand = demandService.getDemandById(buyBondConsultingReq.getIdDemand());
        BuyBondConsulting buyBondConsulting = new BuyBondConsulting();
        buyBondConsulting.setDemand(demand);
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
        buyBondConsultingRepository.save(buyBondConsulting);
    }
    public BuyBondConsulting findById(Long id) {
        return buyBondConsultingRepository.findById(id).orElse(null);
    }
}

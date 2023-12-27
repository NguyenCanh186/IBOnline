package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.demand.entity.Demand;
import com.vmg.ibo.demand.service.IDemandService;
import com.vmg.ibo.form_demand.model.sell_​​bonds.SellBonds;
import com.vmg.ibo.form_demand.model.sell_​​bonds.SellBondsReq;
import com.vmg.ibo.form_demand.repository.ISellBondsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SellBondsService extends BaseService {
    @Autowired
    private ISellBondsRepository sellBondsRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDemandService demandService;
    public void createSellBonds(SellBondsReq sellBondsReq) {
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
        User user = userService.FindUserById(idUser);
        Demand demand = demandService.getDemandById(sellBondsReq.getIdDemand());
        SellBonds sellBonds = new SellBonds();
        sellBonds.setDemand(demand);
        sellBonds.setUser(user);
        sellBonds.setCreatedAt(new Date());
        sellBonds.setUpdatedAt(new Date());
        sellBonds.setCreatedByUserId(idUser);
        sellBonds.setCreatedBy(getCurrentUser().getUsername());
        sellBonds.setBondType(sellBondsReq.getBondType());
        sellBonds.setNameBond(sellBondsReq.getNameBond());
        sellBonds.setBondTerm(sellBondsReq.getBondTerm());
        sellBonds.setNumberOfBondsWantToSell(sellBondsReq.getNumberOfBondsWantToSell());
        sellBonds.setInterestRate(sellBondsReq.getInterestRate());
        sellBonds.setInterestPaymentPeriod(sellBondsReq.getInterestPaymentPeriod());
        sellBonds.setCertificateOfBondOfferingRegistration(sellBondsReq.getCertificateOfBondOfferingRegistration());
        sellBonds.setOfferingPurpose(sellBondsReq.getOfferingPurpose());
        sellBonds.setCollateral(sellBondsReq.getCollateral());
        sellBonds.setReleaseDate(sellBondsReq.getReleaseDate());
        sellBonds.setBondValue(sellBondsReq.getBondValue());
        sellBondsRepository.save(sellBonds);
    }
    public SellBonds findSellBondsById(Long id) {
        return sellBondsRepository.findById(id).orElse(null);
    }
}

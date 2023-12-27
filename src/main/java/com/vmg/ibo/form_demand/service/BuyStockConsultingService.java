package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.demand.entity.Demand;
import com.vmg.ibo.demand.service.IDemandService;
import com.vmg.ibo.form_demand.model.buy_bond_consulting.BuyBondConsulting;
import com.vmg.ibo.form_demand.model.buy_bond_consulting.BuyBondConsultingReq;
import com.vmg.ibo.form_demand.model.buy_stock_consulting.BuyStockConsulting;
import com.vmg.ibo.form_demand.model.buy_stock_consulting.BuyStockConsultingReq;
import com.vmg.ibo.form_demand.repository.IBuyStockConsultingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BuyStockConsultingService extends BaseService {
    @Autowired
    private IBuyStockConsultingRepository buyStockConsultingRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDemandService demandService;
    public void createBuyStockConsulting(BuyStockConsultingReq buyStockConsultingReq) {
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
        User user = userService.FindUserById(idUser);
        Demand demand = demandService.getDemandById(buyStockConsultingReq.getIdDemand());
        BuyStockConsulting buyStockConsulting = new BuyStockConsulting();
        buyStockConsulting.setDemand(demand);
        buyStockConsulting.setUser(user);
        buyStockConsulting.setCreatedAt(new Date());
        buyStockConsulting.setUpdatedAt(new Date());
        buyStockConsulting.setCreatedByUserId(idUser);
        buyStockConsulting.setCreatedBy(getCurrentUser().getUsername());
        buyStockConsulting.setStocks(buyStockConsultingReq.getStocks());
        buyStockConsulting.setStockType(buyStockConsulting.getStockType());
        buyStockConsulting.setNumberOfStocksWantToSell(buyStockConsultingReq.getNumberOfStocksWantToSell());
        buyStockConsulting.setAskingPrice(buyStockConsultingReq.getAskingPrice());
        buyStockConsulting.setTotalCapitalMobilizationValue(buyStockConsultingReq.getTotalCapitalMobilizationValue());
        buyStockConsulting.setTimeToRegisterToBuy(buyStockConsultingReq.getTimeToRegisterToBuy());
        buyStockConsulting.setTimeToPayForPurchase(buyStockConsultingReq.getTimeToPayForPurchase());
        buyStockConsultingRepository.save(buyStockConsulting);
    }

    public BuyStockConsulting findById(Long id) {
        return buyStockConsultingRepository.findById(id).get();
    }
}

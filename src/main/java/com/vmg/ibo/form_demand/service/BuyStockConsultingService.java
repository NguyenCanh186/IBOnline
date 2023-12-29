package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.demand.entity.Demand;
import com.vmg.ibo.demand.service.IDemandService;
import com.vmg.ibo.form_demand.model.DataSummaryTable;
import com.vmg.ibo.form_demand.model.DataSummaryTableRes;
import com.vmg.ibo.form_demand.model.DemandForm;
import com.vmg.ibo.form_demand.model.DemandFormReq;
import com.vmg.ibo.form_demand.model.buy_stock_consulting.BuyStockConsulting;
import com.vmg.ibo.form_demand.model.buy_stock_consulting.BuyStockConsultingReq;
import com.vmg.ibo.form_demand.repository.IBuyStockConsultingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BuyStockConsultingService extends BaseService {
    @Autowired
    private IBuyStockConsultingRepository buyStockConsultingRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDemandService demandService;
    @Autowired
    private DemandFormService demandFormService;
    @Autowired
    private DataSummaryTableService dataSummaryTableService;
    public DemandForm createBuyStockConsulting(BuyStockConsultingReq buyStockConsultingReq) {
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
        User user = userService.FindUserById(idUser);
        Demand demand = demandService.getDemandById(3L);
        BuyStockConsulting buyStockConsulting = new BuyStockConsulting();
        buyStockConsulting.setTags("Cổ phiếu");
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
        BuyStockConsulting buyStockConsulting1 = buyStockConsultingRepository.save(buyStockConsulting);
        DataSummaryTable dataSummaryTable = new DataSummaryTable();
        dataSummaryTable.setTags("Cổ phiếu");
        dataSummaryTable.setIdChildTable(buyStockConsulting1.getId());
        dataSummaryTable.setDemandId(3L);
        dataSummaryTable.setDemandType(demand.getType());
        dataSummaryTable.setCreatedDate(new Date());
        dataSummaryTable.setIdCustomer(idUser);
        dataSummaryTable.setStocks(buyStockConsultingReq.getStocks());
        dataSummaryTable.setStockType(buyStockConsulting.getStockType());
        dataSummaryTable.setNumberOfStocksWantToSell(buyStockConsultingReq.getNumberOfStocksWantToSell());
        dataSummaryTable.setAskingPrice(buyStockConsultingReq.getAskingPrice());
        dataSummaryTable.setTotalCapitalMobilizationValue(buyStockConsultingReq.getTotalCapitalMobilizationValue());
        dataSummaryTable.setTimeToRegisterToBuy(buyStockConsultingReq.getTimeToRegisterToBuy());
        dataSummaryTable.setStatus(1);
        dataSummaryTable.setTimeToPayForPurchase(buyStockConsultingReq.getTimeToPayForPurchase());
        DataSummaryTable dataSummaryTable1 = dataSummaryTableService.save(dataSummaryTable);
        DemandFormReq demandFormReq = new DemandFormReq();
        demandFormReq.setIdCustomer(dataSummaryTable1.getId());
        return demandFormService.save(demandFormReq);
    }

    public BuyStockConsulting findById(Long id) {
        return buyStockConsultingRepository.findById(id).get();
    }
}

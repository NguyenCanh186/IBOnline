package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.demand.entity.Demand;
import com.vmg.ibo.demand.service.IDemandService;
import com.vmg.ibo.form_demand.model.DataSummaryTable;
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
    private DataSummaryTableService dataSummaryTableService;
    public List<DataSummaryTable> createBuyStockConsulting(BuyStockConsultingReq buyStockConsultingReq) {
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
        dataSummaryTable.setTimeToPayForPurchase(buyStockConsultingReq.getTimeToPayForPurchase());
        dataSummaryTableService.save(dataSummaryTable);
        return dataSummaryTableService.getListByFilter(demand.getType() == 1 ? 2 : 1, buyStockConsulting1.getTags(), idUser);
    }

    public BuyStockConsulting findById(Long id) {
        return buyStockConsultingRepository.findById(id).get();
    }
}

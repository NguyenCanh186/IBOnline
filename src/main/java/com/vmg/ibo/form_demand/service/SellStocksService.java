package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.demand.entity.Demand;
import com.vmg.ibo.demand.service.IDemandService;
import com.vmg.ibo.form_demand.model.DataSummaryTable;
import com.vmg.ibo.form_demand.model.sell_stocks.SellStocks;
import com.vmg.ibo.form_demand.model.sell_stocks.SellStocksReq;
import com.vmg.ibo.form_demand.repository.ISellStocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SellStocksService extends BaseService {
    @Autowired
    private ISellStocksRepository sellStocksRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDemandService demandService;
    @Autowired
    private DataSummaryTableService dataSummaryTableService;

    public void createSellStocks(SellStocksReq sellStocksReq) {
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
        User user = userService.FindUserById(idUser);
        Demand demand = demandService.getDemandById(7L);
        SellStocks sellStocks = new SellStocks();
        sellStocks.setDemand(demand);
        sellStocks.setTags("Cổ phiếu");
        sellStocks.setUser(user);
        sellStocks.setCreatedAt(new Date());
        sellStocks.setUpdatedAt(new Date());
        sellStocks.setCreatedByUserId(idUser);
        sellStocks.setCreatedBy(getCurrentUser().getUsername());
        sellStocks.setStockCode(sellStocksReq.getStockCode());
        sellStocks.setStockName(sellStocksReq.getStockName());
        sellStocks.setStockType(sellStocksReq.getStockType());
        sellStocks.setNumberOfStocksWantToSell(sellStocksReq.getNumberOfStocksWantToSell());
        sellStocks.setAskingPrice(sellStocksReq.getAskingPrice());
        sellStocks.setMinimumNumberOfSharesRegisteredToBuy(sellStocksReq.getMinimumNumberOfSharesRegisteredToBuy());
        sellStocks.setMaximumNumberOfSharesRegisteredToBuy(sellStocksReq.getMaximumNumberOfSharesRegisteredToBuy());
        sellStocks.setEstimatedTransactionTime(sellStocksReq.getEstimatedTransactionTime());
        SellStocks sellStocks1 = sellStocksRepository.save(sellStocks);

        DataSummaryTable dataSummaryTable = new DataSummaryTable();
        dataSummaryTable.setTags("Cổ phiếu");
        dataSummaryTable.setIdChildTable(sellStocks1.getId());
        dataSummaryTable.setDemandId(7L);
        dataSummaryTable.setDemandType(demand.getType());
        dataSummaryTable.setCreatedDate(new Date());
        dataSummaryTable.setIdCustomer(idUser);
        dataSummaryTable.setStockCode(sellStocksReq.getStockCode());
        dataSummaryTable.setStockName(sellStocksReq.getStockName());
        dataSummaryTable.setStockType(sellStocksReq.getStockType());
        dataSummaryTable.setNumberOfStocksWantToSell(sellStocksReq.getNumberOfStocksWantToSell());
        dataSummaryTable.setAskingPrice(sellStocksReq.getAskingPrice());
        dataSummaryTable.setMinimumNumberOfSharesRegisteredToBuy(sellStocksReq.getMinimumNumberOfSharesRegisteredToBuy());
        dataSummaryTable.setMaximumNumberOfSharesRegisteredToBuy(sellStocksReq.getMaximumNumberOfSharesRegisteredToBuy());
        dataSummaryTable.setEstimatedTransactionTime(sellStocksReq.getEstimatedTransactionTime());
        dataSummaryTableService.save(dataSummaryTable);
    }

    public SellStocks findById(Long id) {
        return sellStocksRepository.findById(id).orElse(null);
    }
}

package com.vmg.ibo.form_demand.model.sell_stocks;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class SellStocksReq {
    @NotNull(message = "Mã cổ phiếu bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 50, message = "Mã cổ phiếu tối đa được phép là 50 ký tự", groups = {Insert.class, Update.class})
    private String stockCode;
    @NotNull(message = "Tên cổ phiếu bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 255, message = "Tên cổ phiếu tối đa được phép là 255 ký tự", groups = {Insert.class, Update.class})
    private String stockName;
    @NotNull(message = "Loại cổ phiếu bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 50, message = "Loại cổ phiếu tối đa được phép là 50 ký tự", groups = {Insert.class, Update.class})
    private String stockType;
    private Long numberOfStocksWantToSell;
    private Long askingPrice;
    private Long minimumNumberOfSharesRegisteredToBuy;
    private Long maximumNumberOfSharesRegisteredToBuy;
    private Date estimatedTransactionTime;

    private Long idDemand;

    public SellStocksReq() {
    }

    public SellStocksReq(String stockCode, String stockName, String stockType, Long numberOfStocksWantToSell, Long askingPrice, Long minimumNumberOfSharesRegisteredToBuy, Long maximumNumberOfSharesRegisteredToBuy, Date estimatedTransactionTime, Long idDemand) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.stockType = stockType;
        this.numberOfStocksWantToSell = numberOfStocksWantToSell;
        this.askingPrice = askingPrice;
        this.minimumNumberOfSharesRegisteredToBuy = minimumNumberOfSharesRegisteredToBuy;
        this.maximumNumberOfSharesRegisteredToBuy = maximumNumberOfSharesRegisteredToBuy;
        this.estimatedTransactionTime = estimatedTransactionTime;
        this.idDemand = idDemand;
    }
}

package com.vmg.ibo.form_demand.model.buy_stock_consulting;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import com.vmg.ibo.demand.entity.Demand;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
public class BuyStockConsultingReq {
    @NotNull(message = "Mã cổ phiếu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String stocks;
    @NotNull(message = "Loại cổ phiếu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String stockType;
    @NotNull(message = "Số lượng cổ phiếu muốn bán bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long numberOfStocksWantToSell;
    @NotNull(message = "Giá chào bán bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long askingPrice;
    @NotNull(message = "Tổng giá trị huy động vốn bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long totalCapitalMobilizationValue;
    @NotNull(message = "Thời gian đăng ký mua bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Date timeToRegisterToBuy;
    @NotNull(message = "Thời gian nộp tiền mua bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Date timeToPayForPurchase;
    @NotNull(message = "Id nhu cầu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long idDemand;
}

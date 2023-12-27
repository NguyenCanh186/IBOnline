package com.vmg.ibo.form_demand.model.sell_​​fund_certificates;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import com.vmg.ibo.demand.entity.Demand;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
@Data
public class InvestmentPortfolioReq {
    @NotNull(message = "Tên quỹ bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String fundName;
    @NotNull(message = "Mã chứng chỉ muốn bán bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String fundCertificateCodeWantToSell;
    @NotNull(message = "Số lượng chứng chỉ quỹ muốn bán bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long numberOfFundCertificateWantToSell;
    @NotNull(message = "Giá chứng chỉ quỹ mong muốn bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long desiredSellingPriceOfFundCertificates;
    @NotNull(message = "Ngày giao dịch bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Date dayTrading;
    private List<SellFundCertificates> sellFundCertificates;
    @NotNull(message = "Id nhu cầu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long idDemand;
}

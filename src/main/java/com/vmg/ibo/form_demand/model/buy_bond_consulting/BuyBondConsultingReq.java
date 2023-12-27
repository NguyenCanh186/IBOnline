package com.vmg.ibo.form_demand.model.buy_bond_consulting;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BuyBondConsultingReq {
    @NotNull(message = "Mã trái phiếu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String bondCode;
    @NotNull(message = "Loại trái phiếu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String bondType;
    @NotNull(message = "Số lượng trái phiếu muốn bán bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long numberOfBondsWantToSell;
    @NotNull(message = "Kỳ hạn trái phiếu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String bondTerm;
    @NotNull(message = "Lãi suất bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String interestRate;
    @NotNull(message = "Kỳ trả lãi bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String interestPaymentPeriod;
    @NotNull(message = "Giá chào bán bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String askingPrice;
    @NotNull(message = "Mục đích chào bán bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String offeringPurpose;
    @NotNull(message = "Tài sản đảm bảo bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String collateral;
    @NotNull(message = "Id nhu cầu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long idDemand;

    public BuyBondConsultingReq() {
    }

    public BuyBondConsultingReq(String bondCode, String bondType, Long numberOfBondsWantToSell, String bondTerm, String interestRate, String interestPaymentPeriod, String askingPrice, String offeringPurpose, String collateral, Long idDemand) {
        this.bondCode = bondCode;
        this.bondType = bondType;
        this.numberOfBondsWantToSell = numberOfBondsWantToSell;
        this.bondTerm = bondTerm;
        this.interestRate = interestRate;
        this.interestPaymentPeriod = interestPaymentPeriod;
        this.askingPrice = askingPrice;
        this.offeringPurpose = offeringPurpose;
        this.collateral = collateral;
        this.idDemand = idDemand;
    }
}

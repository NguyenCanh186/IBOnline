package com.vmg.ibo.form_demand.model.form_buy_fund_certificate_consulting;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FormBuyFundCertificateConsultingReq {
    @NotNull(message = "Tên quỹ bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String nameOfFund;
    @NotNull(message = "Số lượng CCQ đăng ký chào bán tối thiểu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long minimumNumberOfRegisteredForSale;
    @NotNull(message = "Mệnh giá CCQ bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long denominations;
    @NotNull(message = "Số lượng CCQ mua tối thiểu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long minimumNumberOfPurchased;
    @NotNull(message = "Hiệu lực đăng ký chào bán bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String validityOfRegistrationForOffering;
    @NotNull(message = "Lĩnh vực đầu tư của quỹ bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String fundInvestmentFields;
    @NotNull(message = "Mục tiêu đầu tư của quỹ bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String fundInvestmentObjective;
    @NotNull(message = "Chiến lược đầu tư của quỹ bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String fundInvestmentStrategy;
    @NotNull(message = "Lợi ích của quỹ bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String fundBenefits;
    @NotNull(message = "id nhu cầu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long idDemand;

    public FormBuyFundCertificateConsultingReq() {
    }

    public FormBuyFundCertificateConsultingReq(String nameOfFund, Long minimumNumberOfRegisteredForSale, Long denominations, Long minimumNumberOfPurchased, String validityOfRegistrationForOffering, String fundInvestmentFields, String fundInvestmentObjective, String fundInvestmentStrategy, String fundBenefits, Long idDemand) {
        this.nameOfFund = nameOfFund;
        this.minimumNumberOfRegisteredForSale = minimumNumberOfRegisteredForSale;
        this.denominations = denominations;
        this.minimumNumberOfPurchased = minimumNumberOfPurchased;
        this.validityOfRegistrationForOffering = validityOfRegistrationForOffering;
        this.fundInvestmentFields = fundInvestmentFields;
        this.fundInvestmentObjective = fundInvestmentObjective;
        this.fundInvestmentStrategy = fundInvestmentStrategy;
        this.fundBenefits = fundBenefits;
        this.idDemand = idDemand;
    }
}

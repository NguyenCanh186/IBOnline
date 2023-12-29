package com.vmg.ibo.form_demand.model.form_buy_fund_certificate_consulting;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class FormBuyFundCertificateConsultingReq {
    @NotNull(message = "Tên quỹ bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 225, message = "Tên quỹ tối đa được phép là 225 ký tự", groups = {Insert.class, Update.class})
    private String nameOfFund;
    @NotNull(message = "Số lượng CCQ đăng ký chào bán tối thiểu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long minimumNumberOfRegisteredForSale;
    @NotNull(message = "Mệnh giá CCQ bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long denominations;
    @NotNull(message = "Số lượng CCQ mua tối thiểu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long minimumNumberOfPurchased;
    @NotNull(message = "Hiệu lực đăng ký chào bán bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Date validityOfRegistrationForOfferingFrom;
    @NotNull(message = "Hiệu lực đăng ký chào bán bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Date validityOfRegistrationForOfferingTo;
    @NotNull(message = "Thời hạn nhận đăng ký mua/thanh toán bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Date deadlineForReceivingRegistrationPaymentForm;
    @NotNull(message = "Thời hạn nhận đăng ký mua/thanh toán bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Date deadlineForReceivingRegistrationPaymentTo;
    @NotNull(message = "Lĩnh vực đầu tư của quỹ bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 500, message = "Lĩnh vực đầu tư của quỹ tối đa được phép là 500 ký tự", groups = {Insert.class, Update.class})
    private String fundInvestmentFields;
    @NotNull(message = "Mục tiêu đầu tư của quỹ bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 500, message = "Mục tiêu đầu tư của quỹ tối đa được phép là 500 ký tự", groups = {Insert.class, Update.class})
    private String fundInvestmentObjective;
    @NotNull(message = "Chiến lược đầu tư của quỹ bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 100, message = "Chiến lược đầu tư của quỹ tối đa được phép là 100 ký tự", groups = {Insert.class, Update.class})
    private String fundInvestmentStrategy;
    @NotNull(message = "Lợi ích của quỹ bắt buộc nhập!", groups = {Insert.class, Update.class})
    @Size(max = 500, message = "Lợi ích của quỹ tối đa được phép là 500 ký tự", groups = {Insert.class, Update.class})
    private String fundBenefits;
    @NotNull(message = "id nhu cầu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long idDemand;

    public FormBuyFundCertificateConsultingReq() {
    }

    public FormBuyFundCertificateConsultingReq(String nameOfFund, Long minimumNumberOfRegisteredForSale, Long denominations, Long minimumNumberOfPurchased, Date validityOfRegistrationForOfferingFrom, Date validityOfRegistrationForOfferingTo, Date deadlineForReceivingRegistrationPaymentForm, Date deadlineForReceivingRegistrationPaymentTo, String fundInvestmentFields, String fundInvestmentObjective, String fundInvestmentStrategy, String fundBenefits, Long idDemand) {
        this.nameOfFund = nameOfFund;
        this.minimumNumberOfRegisteredForSale = minimumNumberOfRegisteredForSale;
        this.denominations = denominations;
        this.minimumNumberOfPurchased = minimumNumberOfPurchased;
        this.validityOfRegistrationForOfferingFrom = validityOfRegistrationForOfferingFrom;
        this.validityOfRegistrationForOfferingTo = validityOfRegistrationForOfferingTo;
        this.deadlineForReceivingRegistrationPaymentForm = deadlineForReceivingRegistrationPaymentForm;
        this.deadlineForReceivingRegistrationPaymentTo = deadlineForReceivingRegistrationPaymentTo;
        this.fundInvestmentFields = fundInvestmentFields;
        this.fundInvestmentObjective = fundInvestmentObjective;
        this.fundInvestmentStrategy = fundInvestmentStrategy;
        this.fundBenefits = fundBenefits;
        this.idDemand = idDemand;
    }
}

package com.vmg.ibo.form_demand.model.sell_​​bonds;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
public class SellBondsReq {
    @NotNull(message = "Tên trái phiếu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String nameBond;
    @NotNull(message = "Giấy chứng nhận đăng ký chào bán trái phiếu số bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String certificateOfBondOfferingRegistration;
    @NotNull(message = "Ngày phát hành bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Date releaseDate;
    @NotNull(message = "Loại trái phiếu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String bondType;
    @NotNull(message = "Mệnh giá trái phiếu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long bondValue;
    @NotNull(message = "Số lượng trái phiếu muốn bán bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long numberOfBondsWantToSell;
    @NotNull(message = "Kỳ hạn trái phiếu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String bondTerm;
    @NotNull(message = "Lãi suất bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String interestRate;
    @NotNull(message = "Kỳ trả lãi bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String interestPaymentPeriod;
    @NotNull(message = "Mục đích chào bán bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String offeringPurpose;
    @NotNull(message = "Tài sản đảm bảo bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String collateral;
    @NotNull(message = "Id nhu cầu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long idDemand;
}

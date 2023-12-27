package com.vmg.ibo.form_demand.model.financial_investment;

import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FinancialInvestmentReq {
    @NotNull(message = "Vốn đầu tư tối thiểu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long minimumInvestmentCapital;
    @NotNull(message = "Thời gian đầu tư dự kiến bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String estimatedInvestmentTime;
    @NotNull(message = "Lợi nhuận kỳ vọng bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long expectedProfitRate;
    @NotNull(message = "Mức độ chịu rủi ro bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String levelOfRiskTolerance;
    @NotNull(message = "Mục tiêu đầu tư bắt buộc nhập!", groups = {Insert.class, Update.class})
    private String investmentObjective;
    @NotNull(message = "Id nhu cầu bắt buộc nhập!", groups = {Insert.class, Update.class})
    private Long idDemand;

    public FinancialInvestmentReq() {
    }

    public FinancialInvestmentReq(Long minimumInvestmentCapital, String estimatedInvestmentTime, Long expectedProfitRate, String levelOfRiskTolerance, Long idDemand) {
        this.minimumInvestmentCapital = minimumInvestmentCapital;
        this.estimatedInvestmentTime = estimatedInvestmentTime;
        this.expectedProfitRate = expectedProfitRate;
        this.levelOfRiskTolerance = levelOfRiskTolerance;
        this.idDemand = idDemand;
    }
}

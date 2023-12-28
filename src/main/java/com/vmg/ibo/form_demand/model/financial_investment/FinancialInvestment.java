package com.vmg.ibo.form_demand.model.financial_investment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vmg.ibo.core.base.BaseEntity;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.demand.entity.Demand;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "FINANCIAL_INVESTMENT")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FinancialInvestment extends BaseEntity {
    private Long minimumInvestmentCapital;
    private String estimatedInvestmentTime;
    private Long expectedProfitRate;
    private String levelOfRiskTolerance;
    private String investmentObjective;
    private String tags;
    @OneToOne
    @JoinColumn(name = "idDemand")
    private Demand demand;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "idUser")
    private User user;
}

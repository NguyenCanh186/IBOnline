package com.vmg.ibo.demand.entity;

import com.vmg.ibo.core.base.BaseEntity;
import com.vmg.ibo.form_demand.model.financial_investment.FinancialInvestment;
import com.vmg.ibo.form_demand.model.form_buy_fund_certificate_consulting.FormBuyFundCertificateConsulting;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DEMANDS")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Demand extends BaseEntity {
    private String name;
    private Integer type;
//    @OneToOne(mappedBy = "demand")
//    private FinancialInvestment financialInvestment;
}

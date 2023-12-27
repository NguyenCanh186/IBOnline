package com.vmg.ibo.form_demand.model.form_buy_fund_certificate_consulting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vmg.ibo.core.base.BaseEntity;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.demand.entity.Demand;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "FORM_BUY_FUND_CERTIFICATE_CONSULTING_SERVICE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FormBuyFundCertificateConsulting extends BaseEntity {
    private String nameOfFund;
    private Long minimumNumberOfRegisteredForSale;
    private Long denominations;
    private Long minimumNumberOfPurchased;
    private Date validityOfRegistrationForOffering;
    private Date deadlineForReceivingRegistrationPayment;
    private String fundInvestmentFields;
    private String fundInvestmentObjective;
    private String fundInvestmentStrategy;
    private String fundBenefits;

    @OneToOne
    @JoinColumn(name = "idDemand")
    private Demand demand;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "idUser")
    private User user;
}

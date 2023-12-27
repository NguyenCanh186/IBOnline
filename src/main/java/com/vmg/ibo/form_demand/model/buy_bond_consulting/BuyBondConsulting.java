package com.vmg.ibo.form_demand.model.buy_bond_consulting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vmg.ibo.core.base.BaseEntity;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.demand.entity.Demand;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "BUY_BOND_CONSULTING")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BuyBondConsulting extends BaseEntity {
    private String bondCode;
    private String bondType;
    private Long numberOfBondsWantToSell;
    private String bondTerm;
    private String interestRate;
    private String interestPaymentPeriod;
    private String askingPrice;
    private String offeringPurpose;
    private String collateral;
    @OneToOne
    @JoinColumn(name = "idDemand")
    private Demand demand;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "idUser")
    private User user;
}

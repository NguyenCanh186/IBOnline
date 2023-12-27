package com.vmg.ibo.form_demand.model.sell_​​bonds;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vmg.ibo.core.action.Insert;
import com.vmg.ibo.core.action.Update;
import com.vmg.ibo.core.base.BaseEntity;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.demand.entity.Demand;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Entity
@Table(name = "SELL_BONDS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SellBonds extends BaseEntity {
    private String nameBond;
    private String certificateOfBondOfferingRegistration;
    private Date releaseDate;
    private String bondType;
    private Long bondValue;
    private Long numberOfBondsWantToSell;
    private String bondTerm;
    private String interestRate;
    private String interestPaymentPeriod;
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

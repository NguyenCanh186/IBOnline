package com.vmg.ibo.form_demand.model.sell_​​fund_certificates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vmg.ibo.core.base.BaseEntity;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.demand.entity.Demand;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "INVESTMENT_PORTFOLIO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InvestmentPortfolio extends BaseEntity {
    private String fundName;
    private String fundCertificateCodeWantToSell;
    private Long numberOfFundCertificateWantToSell;
    private Long desiredSellingPriceOfFundCertificates;
    private Date dayTrading;

    private String tags;
    @OneToMany(mappedBy = "investmentPortfolio", cascade = CascadeType.ALL)
    private List<SellFundCertificates> sellFundCertificates;
    @OneToOne
    @JoinColumn(name = "idDemand")
    private Demand demand;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "idUser")
    private User user;
}

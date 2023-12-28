package com.vmg.ibo.form_demand.model.buy_stock_consulting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vmg.ibo.core.base.BaseEntity;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.demand.entity.Demand;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "BUY_STOCK_CONSULTING")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BuyStockConsulting extends BaseEntity {
    private String stocks;
    private String stockType;
    private Long numberOfStocksWantToSell;
    private Long askingPrice;
    private Long totalCapitalMobilizationValue;
    private Date timeToRegisterToBuy;
    private Date timeToPayForPurchase;
    private String tags;
    @OneToOne
    @JoinColumn(name = "idDemand")
    private Demand demand;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "idUser")
    private User user;
}

package com.vmg.ibo.form_demand.model.sell_stocks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.DatabindException;
import com.vmg.ibo.core.base.BaseEntity;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.demand.entity.Demand;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "FORM_SELL_STOCKS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SellStocks extends BaseEntity {
    private String stockCode;
    private String stockName;
    private String stockType;
    private Long numberOfStocksWantToSell;
    private Long askingPrice;
    private Long minimumNumberOfSharesRegisteredToBuy;
    private Long maximumNumberOfSharesRegisteredToBuy;
    private Date estimatedTransactionTime;
    private String tags;

    @OneToOne
    @JoinColumn(name = "idDemand")
    private Demand demand;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "idUser")
    private User user;

}

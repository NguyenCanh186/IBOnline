package com.vmg.ibo.form_demand.model.sell_​​fund_certificates;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "SELL_FUND_CERTIFICATES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SellFundCertificates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long density;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "investment_portfolio_id")
    private InvestmentPortfolio investmentPortfolio;
}

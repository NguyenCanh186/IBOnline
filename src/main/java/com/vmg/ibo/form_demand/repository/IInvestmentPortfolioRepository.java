package com.vmg.ibo.form_demand.repository;

import com.vmg.ibo.form_demand.model.sell_​​fund_certificates.InvestmentPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IInvestmentPortfolioRepository extends JpaRepository<InvestmentPortfolio, Long> {

    @Query(value = "SELECT * " +
            "FROM investment_portfolio i " +
            "JOIN buy_bond_consulting b ON i.id_demand = b.id_demand AND i.tags = b.tags " +
            "JOIN form_buy_fund_certificate_consulting f ON i.id_demand = f.id_demand AND i.tags = f.tags " +
            "JOIN buy_stock_consulting bs ON i.id_demand = bs.id_demand AND i.tags = bs.tags " +
            "JOIN sell_bonds s ON i.id_demand = s.id_demand AND i.tags = s.tags " +
            "JOIN financial_investment fi ON i.id_demand = fi.id_demand AND i.tags = fi.tags " +
            "WHERE i.id_demand = ?1 AND i.tags = ?2", nativeQuery = true)
    List<Object[]> getAllDataFromTables(Long idDemand, String tags);

}

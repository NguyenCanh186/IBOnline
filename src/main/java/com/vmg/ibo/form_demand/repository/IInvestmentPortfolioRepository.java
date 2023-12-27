package com.vmg.ibo.form_demand.repository;

import com.vmg.ibo.form_demand.model.sell_​​fund_certificates.InvestmentPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvestmentPortfolioRepository extends JpaRepository<InvestmentPortfolio, Long> {
}

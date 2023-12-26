package com.vmg.ibo.form_demand.repository;

import com.vmg.ibo.form_demand.model.financial_investment.FinancialInvestment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFinancialInvestmentRepository extends JpaRepository<FinancialInvestment, Long> {
}

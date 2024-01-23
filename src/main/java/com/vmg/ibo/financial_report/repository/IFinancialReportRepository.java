package com.vmg.ibo.financial_report.repository;

import com.vmg.ibo.financial_report.model.entity.FinancialReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IFinancialReportRepository extends JpaRepository<FinancialReport, Long> {
}

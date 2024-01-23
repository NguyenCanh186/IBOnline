package com.vmg.ibo.financial_report.repository;

import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.financial_report.model.entity.FinancialReport;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IFinancialReportRepository extends JpaRepository<FinancialReport, Long> {
    List<FinancialReport> findAllByUser(User user, Sort sort);

    FinancialReport findByQuarterAndYearAndUser(Integer quarter, Integer year, User user);
}

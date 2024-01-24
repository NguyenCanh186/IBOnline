package com.vmg.ibo.financial_report.service;

import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.financial_report.model.dto.FinancialReportDTO;
import com.vmg.ibo.financial_report.model.dto.FinancialReportRequest;
import com.vmg.ibo.financial_report.model.entity.FinancialReport;

import java.util.List;

public interface IFinancialReportService {
    FinancialReportDTO create(FinancialReportRequest financialReportRequest);

    FinancialReport save(FinancialReport financialReport);

    List<FinancialReportDTO> findAll(User user);

    FinancialReport findByQuarterAndYearAndUser(Integer quarter, Integer year, User user);
}

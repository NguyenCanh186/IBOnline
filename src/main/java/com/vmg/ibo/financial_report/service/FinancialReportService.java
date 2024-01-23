package com.vmg.ibo.financial_report.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.financial_report.model.entity.FinancialReport;
import com.vmg.ibo.financial_report.repository.IFinancialReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FinancialReportService extends BaseService implements IFinancialReportService {
    @Autowired
    private IFinancialReportRepository financialReportRepository;

    @Override
    public FinancialReport save(FinancialReport financialReport) {
        financialReport.setCreatedAt(new Date());
        financialReport.setCreatedBy(getCurrentUser().getEmail());
        financialReport.setCreatedByUserId(getCurrentUser().getId());
        return financialReportRepository.save(financialReport);
    }
}

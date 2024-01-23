package com.vmg.ibo.financial_report.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.customer.model.customer.FileUpload;
import com.vmg.ibo.customer.repository.IFileUploadRepository;
import com.vmg.ibo.financial_report.model.dto.FinancialReportDTO;
import com.vmg.ibo.financial_report.model.entity.FinancialReport;
import com.vmg.ibo.financial_report.repository.IFinancialReportRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialReportService extends BaseService implements IFinancialReportService {
    @Autowired
    private IFinancialReportRepository financialReportRepository;
    @Autowired
    private IFileUploadRepository fileUploadRepository;

    @Override
    public FinancialReport save(FinancialReport financialReport) {
        FinancialReport current = financialReportRepository.findByQuarterAndYearAndUser(financialReport.getQuarter(), financialReport.getYear(), getCurrentUser());
        if (current != null) {
            current.setAsset(financialReport.getAsset());
            current.setProfit(financialReport.getProfit());
            current.setRevenue(financialReport.getRevenue());
            current.setDebt(financialReport.getDebt());
            financialReport.setUpdatedAt(new Date());
            financialReport.setUpdatedBy(getCurrentUser().getEmail());
            financialReport.setUpdatedByUserId(getCurrentUser().getId());
            return financialReportRepository.save(current);
        }
        financialReport.setCreatedAt(new Date());
        financialReport.setCreatedBy(getCurrentUser().getEmail());
        financialReport.setCreatedByUserId(getCurrentUser().getId());
        return financialReportRepository.save(financialReport);
    }

    @Override
    public List<FinancialReportDTO> findAll(User user) {
        Sort sort = Sort.by(Sort.Direction.DESC, "quarter", "year");
        return financialReportRepository.findAllByUser(user, sort).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private FinancialReportDTO mapToDTO(FinancialReport financialReport) {
        ModelMapper modelMapper = new ModelMapper();
        FinancialReportDTO financialReportDTO = modelMapper.map(financialReport, FinancialReportDTO.class);
        List<FileUpload> files = fileUploadRepository.findAllByFinancialReport(financialReport);
        financialReportDTO.setFiles(files);
        return financialReportDTO;
    }
}

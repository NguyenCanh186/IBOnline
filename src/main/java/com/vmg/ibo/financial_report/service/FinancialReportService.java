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
        if (financialReport.getId() != null) {
            financialReport.setUpdatedAt(new Date());
            financialReport.setUpdatedBy(getCurrentUser().getEmail());
            financialReport.setUpdatedByUserId(getCurrentUser().getId());
        } else  {
            financialReport.setCreatedAt(new Date());
            financialReport.setCreatedBy(getCurrentUser().getEmail());
            financialReport.setCreatedByUserId(getCurrentUser().getId());
        }
        return financialReportRepository.save(financialReport);
    }

    @Override
    public List<FinancialReportDTO> findAll(User user) {
        Sort sort = Sort.by(Sort.Direction.DESC, "quarter", "year");
        return financialReportRepository.findAllByUser(user, sort).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public FinancialReport findByQuarterAndYearAndUser(Integer quarter, Integer year, User user) {
        return financialReportRepository.findByQuarterAndYearAndUser(quarter, year, user);
    }

    private FinancialReportDTO mapToDTO(FinancialReport financialReport) {
        ModelMapper modelMapper = new ModelMapper();
        FinancialReportDTO financialReportDTO = modelMapper.map(financialReport, FinancialReportDTO.class);
        List<FileUpload> files = fileUploadRepository.findAllByFinancialReport(financialReport);
        financialReportDTO.setFiles(files);
        return financialReportDTO;
    }
}

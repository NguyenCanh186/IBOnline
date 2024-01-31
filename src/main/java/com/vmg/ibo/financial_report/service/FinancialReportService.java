package com.vmg.ibo.financial_report.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.config.exception.WebServiceException;
import com.vmg.ibo.core.model.dto.FileUploadDto;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.repository.IUserRepository;
import com.vmg.ibo.customer.model.customer.FileUpload;
import com.vmg.ibo.customer.repository.IFileUploadRepository;
import com.vmg.ibo.customer.service.fileUpload.IFileUploadService;
import com.vmg.ibo.financial_report.model.dto.FinancialReportDTO;
import com.vmg.ibo.financial_report.model.dto.FinancialReportRequest;
import com.vmg.ibo.financial_report.model.entity.FinancialReport;
import com.vmg.ibo.financial_report.repository.IFinancialReportRepository;
import com.vmg.ibo.form.dto.FormSuggestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinancialReportService extends BaseService implements IFinancialReportService {
    @Autowired
    private IFinancialReportRepository financialReportRepository;

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    private IFileUploadRepository fileUploadRepository;

    @Autowired
    private IUserRepository userRepository;

    @Value("${upload.path}")
    private String fileUpload;

    @Override
    @Transactional
    public FinancialReportDTO create(FinancialReportRequest financialReportRequest) {
        User user = getCurrentUser();
        if (user == null) {
            throw new WebServiceException(HttpStatus.OK.value(),HttpStatus.CONFLICT.value(), "Không tìm thấy người dùng hợp lệ");
        }
        FinancialReport financialReport = this.findByQuarterAndYearAndUser(financialReportRequest.getQuarter(), financialReportRequest.getYear(), user);
        boolean isExist = true;
        if (financialReport == null) {
            financialReport = new FinancialReport();
            isExist = false;
        }
        financialReport.setRevenue(financialReportRequest.getRevenue());
        financialReport.setProfit(financialReportRequest.getProfit());
        financialReport.setAsset(financialReportRequest.getAsset());
        financialReport.setDebt(financialReportRequest.getDebt());
        financialReport.setYear(financialReportRequest.getYear());
        if(financialReportRequest.getType() == 0) {
            if(financialReport.getQuarter() == null ){
                throw new WebServiceException(200, 409, "Vui lòng chọn quý");
            }
        }
        financialReport.setQuarter(financialReportRequest.getQuarter());
        financialReport.setUser(user);
        financialReport = this.save(financialReport);
        if (financialReportRequest.getFiles() != null) {
            if (isExist) {
                fileUploadService.deleteAllByFinancialReport(financialReport);
            }
            for (int i = 0; i < financialReportRequest.getFiles().size(); i++) {
                FileUpload fileUpload1 = new FileUpload();
                MultipartFile file = financialReportRequest.getFiles().get(i);
                String fileName = file.getOriginalFilename();
                try {
                    FileCopyUtils.copy(file.getBytes(), new File(this.fileUpload + fileName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileUpload1.setFile(fileName);
                fileUpload1.setIdUser(user.getId());
                fileUpload1.setFinancialReport(financialReport);
                fileUploadService.saveFile(fileUpload1);
            }
        }
        return mapToDTO(financialReport);
    }

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
        financialReportDTO.setFiles(files.stream().map(x -> modelMapper.map(x, FileUploadDto.class)).collect(Collectors.toList()));
        return financialReportDTO;
    }
}

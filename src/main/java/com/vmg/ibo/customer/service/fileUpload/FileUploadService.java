package com.vmg.ibo.customer.service.fileUpload;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.customer.model.customer.FileUpload;
import com.vmg.ibo.customer.repository.IFileUploadRepository;
import com.vmg.ibo.financial_report.model.entity.FinancialReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileUploadService extends BaseService implements IFileUploadService{

    @Autowired
    private IFileUploadRepository fileUploadRepository;
    @Override
    public void saveFile(FileUpload fileUpload) {
        fileUploadRepository.save(fileUpload);
    }

    @Override
    public List<FileUpload> findByIdUser(Long idUser) {
        return fileUploadRepository.findByIdUser(idUser);
    }

    @Override
    public void deleteAllByFinancialReport(FinancialReport financialReport) {
        fileUploadRepository.deleteAllByFinancialReport(financialReport);
    }
}

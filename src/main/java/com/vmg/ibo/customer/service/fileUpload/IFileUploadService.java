package com.vmg.ibo.customer.service.fileUpload;

import com.vmg.ibo.customer.model.customer.FileUpload;
import com.vmg.ibo.financial_report.model.entity.FinancialReport;
import jdk.internal.dynalink.linker.LinkerServices;

import java.util.List;

public interface IFileUploadService {
    void saveFile(FileUpload fileUpload);

    List<FileUpload> findByIdUser(Long idUser);

    void deleteAllByFinancialReport(FinancialReport financialReport);
}

package com.vmg.ibo.core.service.fileUpload;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.customer.FileUpload;
import com.vmg.ibo.core.repository.IFileUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileUploadService extends BaseService implements IFileUploadService{

    @Autowired
    private IFileUploadRepository fileUploadRepository;
    @Override
    public void saveFile(FileUpload fileUpload) {
        fileUploadRepository.save(fileUpload);
    }
}

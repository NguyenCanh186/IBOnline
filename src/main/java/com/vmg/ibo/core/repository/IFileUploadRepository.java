package com.vmg.ibo.core.repository;

import com.vmg.ibo.core.model.customer.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileUploadRepository extends JpaRepository<FileUpload, Long> {
}

package com.vmg.ibo.customer.repository;

import com.vmg.ibo.customer.model.customer.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFileUploadRepository extends JpaRepository<FileUpload, Long> {
}

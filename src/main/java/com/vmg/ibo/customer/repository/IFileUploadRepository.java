package com.vmg.ibo.customer.repository;

import com.vmg.ibo.customer.model.customer.FileUpload;
import com.vmg.ibo.financial_report.model.entity.FinancialReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFileUploadRepository extends JpaRepository<FileUpload, Long> {
    @Query(value = "select u from FileUpload u where u.idUser = :idUser")
    List<FileUpload> findByIdUser(@Param("idUser") Long idUser);

    List<FileUpload> findAllByFinancialReport(FinancialReport financialReport);

    void deleteAllByFinancialReport(FinancialReport financialReport);
}

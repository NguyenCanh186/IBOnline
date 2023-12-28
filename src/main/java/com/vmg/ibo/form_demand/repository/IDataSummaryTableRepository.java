package com.vmg.ibo.form_demand.repository;

import com.vmg.ibo.form_demand.model.DataSummaryTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDataSummaryTableRepository extends JpaRepository<DataSummaryTable, Long> {

    @Query(value = "SELECT d FROM DataSummaryTable d WHERE d.demandType = :demandType and d.tags LIKE %:tags% and d.idCustomer != :idUser order by d.createdDate desc")
    List<DataSummaryTable> getListByFilter(
            @Param("demandType") Integer demandType,
            @Param("tags") String tags,
            @Param("idUser") Long idUser
    );

    @Query(value = "SELECT d FROM DataSummaryTable d WHERE d.demandType = :demandType and d.tags IN :tags and d.idCustomer != :idUser order by d.createdDate desc")
    List<DataSummaryTable> getListByFilterTags(
            @Param("demandType") Integer demandType,
            @Param("tags") List<String> tags,
            @Param("idUser") Long idUser
    );
}



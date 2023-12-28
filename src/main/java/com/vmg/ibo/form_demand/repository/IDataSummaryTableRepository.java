package com.vmg.ibo.form_demand.repository;

import com.vmg.ibo.form_demand.model.DataSummaryTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDataSummaryTableRepository extends JpaRepository<DataSummaryTable, Long> {
}

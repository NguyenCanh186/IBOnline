package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.form_demand.model.DataSummaryTable;
import com.vmg.ibo.form_demand.repository.IDataSummaryTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSummaryTableService {
    @Autowired
    private IDataSummaryTableRepository dataSummaryTableRepository;
    public void save(DataSummaryTable dataSummaryTable) {
        dataSummaryTableRepository.save(dataSummaryTable);
    }
}

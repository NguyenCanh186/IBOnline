package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.form_demand.model.DataSummaryTable;
import com.vmg.ibo.form_demand.repository.IDataSummaryTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataSummaryTableService {
    @Autowired
    private IDataSummaryTableRepository dataSummaryTableRepository;
    public void save(DataSummaryTable dataSummaryTable) {
        dataSummaryTableRepository.save(dataSummaryTable);
    }
    public List<DataSummaryTable> getListByFilter(Integer demandType, String tags, Long idUser) {
        return dataSummaryTableRepository.getListByFilter(demandType, tags, idUser);
    }

    public List<DataSummaryTable> getListByFilterTags(Integer demandType, List<String> tags, Long idUser) {
        return dataSummaryTableRepository.getListByFilterTags(demandType, tags, idUser);
    }
}

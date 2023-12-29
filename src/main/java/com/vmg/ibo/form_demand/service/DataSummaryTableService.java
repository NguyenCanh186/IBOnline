package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.customer.model.UserDetail;
import com.vmg.ibo.customer.model.UserDetailWithUserDTOImpl;
import com.vmg.ibo.customer.service.userDetail.UserDetailService;
import com.vmg.ibo.form_demand.model.DataSummaryTable;
import com.vmg.ibo.form_demand.model.DataSummaryTableRes;
import com.vmg.ibo.form_demand.model.DemandForm;
import com.vmg.ibo.form_demand.repository.IDataSummaryTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DataSummaryTableService extends BaseService {
    @Autowired
    private IDataSummaryTableRepository dataSummaryTableRepository;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private DemandFormService demandFormService;
    public DataSummaryTable save(DataSummaryTable dataSummaryTable) {
        return dataSummaryTableRepository.save(dataSummaryTable);
    }
    public List<DataSummaryTable> getListByFilter(Integer demandType, String tags, Long idUser) {
        return dataSummaryTableRepository.getListByFilter(demandType, tags, idUser);
    }

    public List<DataSummaryTable> getListByFilterTags(Integer demandType, List<String> tags, Long idUser) {
        return dataSummaryTableRepository.getListByFilterTags(demandType, tags, idUser);
    }
    public DataSummaryTable findById(Long id) {
        return dataSummaryTableRepository.findById(id).orElse(null);
    }

    public DataSummaryTableRes dataSummaryTableRes(Long id) {
        DemandForm demandForm = demandFormService.findById(id);
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
        UserDetailWithUserDTOImpl userDetail = userDetailService.findUserById(idUser);
        DataSummaryTable dataSummaryTable = findById(demandForm.getIdCustomer());
        List<DataSummaryTable> dataSummaryTableList;
        if (dataSummaryTable.getTags() != null && dataSummaryTable.getTags().contains(",")) {
            List<String> tagsList = Arrays.asList(dataSummaryTable.getTags().split(","));
            dataSummaryTableList = getListByFilterTags(dataSummaryTable.getDemandType(), tagsList, dataSummaryTable.getIdCustomer());
        } else {
            dataSummaryTableList = getListByFilter(dataSummaryTable.getDemandType(), dataSummaryTable.getTags(), dataSummaryTable.getIdCustomer());
        }
        DataSummaryTableRes dataSummaryTableRes = new DataSummaryTableRes();
        if (userDetail.getIsCustomerPersonal()) {
            dataSummaryTableRes.setName(userDetail.getContactName());
        } else {
            dataSummaryTableRes.setName(userDetail.getBusinessName());
        }
        dataSummaryTableRes.setDataSummaryTable(dataSummaryTable);
        dataSummaryTableRes.setStatusConnect(demandForm.getStatus());
        dataSummaryTableRes.setDataSummaryTableList(dataSummaryTableList);
        return dataSummaryTableRes;
    }

    public DataSummaryTableRes getDataSummaryTableResByIdDemandForm(Long id) {
        DataSummaryTableRes dataSummaryTableRes = new DataSummaryTableRes();
        DemandForm demandForm = demandFormService.findById(id);
        if (demandForm.getStatus() == 0) {
            dataSummaryTableRes = dataSummaryTableRes(id);
        } else {
            Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
            UserDetailWithUserDTOImpl userDetail = userDetailService.findUserById(idUser);
            DataSummaryTable dataSummaryTable = findById(demandForm.getIdCustomer());
            DataSummaryTable dataSummaryTable1 = findById(demandForm.getIdPartner());
            List<DataSummaryTable> dataSummaryTableList = new ArrayList<>();
            dataSummaryTableList.add(dataSummaryTable1);
            if (userDetail.getIsCustomerPersonal()) {
                dataSummaryTableRes.setName(userDetail.getContactName());
            } else {
                dataSummaryTableRes.setName(userDetail.getBusinessName());
            }
            dataSummaryTableRes.setDataSummaryTable(dataSummaryTable);
            dataSummaryTableRes.setDataSummaryTableList(dataSummaryTableList);
            dataSummaryTableRes.setStatusConnect(demandForm.getStatus());
        }
        return dataSummaryTableRes;
    }
}

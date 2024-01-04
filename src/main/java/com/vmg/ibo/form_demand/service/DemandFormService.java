package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.demand.entity.Demand;
import com.vmg.ibo.demand.service.DemandService;
import com.vmg.ibo.form_demand.model.*;
import com.vmg.ibo.form_demand.repository.IDemandFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class DemandFormService extends BaseService {
    @Autowired
    private IDemandFormRepository demandFormRepository;
    @Autowired
    private DataSummaryTableService dataSummaryTableService;

    @Autowired
    private DemandService demandService;
    public DemandForm save(DemandFormReq DemandFormReq) {
        DataSummaryTable dataSummaryTable = dataSummaryTableService.findById(DemandFormReq.getIdCustomer());
        Demand demand = demandService.getDemandById(dataSummaryTable.getDemandId());
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
        List<String> listUserCode = demandFormRepository.getAllCodeDemand();
        int maxNumber = listUserCode.stream()
                .map(s -> Integer.parseInt(s.substring(3)))
                .max(Comparator.naturalOrder()).orElse(0) + 1;
        String userCode = DataModel.DEMAND_FORM_CODE + String.valueOf(maxNumber);
        DemandForm demandForm = new DemandForm();
        demandForm.setCodeDemand(userCode);
        demandForm.setIdUser(idUser);
        demandForm.setIdCustomer(DemandFormReq.getIdCustomer());
        demandForm.setNameDemand(demand.getName());
        demandForm.setCreatedAt(new Date());
        demandForm.setStatus(0);
        return demandFormRepository.save(demandForm);
    }

    public DemandForm connect(ConnectReq connectReq) {

        DemandForm demandForm = findById(connectReq.getIdDemandForm());
        demandForm.setIdPartner(connectReq.getIdPartner());
        demandForm.setStatus(1);
        return demandFormRepository.save(demandForm);
    }
    public DemandForm findById(Long id) {
        return demandFormRepository.findById(id).orElse(null);
    }
}

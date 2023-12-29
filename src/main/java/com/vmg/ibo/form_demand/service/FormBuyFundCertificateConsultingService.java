package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.demand.entity.Demand;
import com.vmg.ibo.demand.service.IDemandService;
import com.vmg.ibo.form_demand.model.DataSummaryTable;
import com.vmg.ibo.form_demand.model.DemandForm;
import com.vmg.ibo.form_demand.model.DemandFormReq;
import com.vmg.ibo.form_demand.model.form_buy_fund_certificate_consulting.FormBuyFundCertificateConsulting;
import com.vmg.ibo.form_demand.model.form_buy_fund_certificate_consulting.FormBuyFundCertificateConsultingReq;
import com.vmg.ibo.form_demand.repository.IFormBuyFundCertificateConsultingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FormBuyFundCertificateConsultingService extends BaseService {
    @Autowired
    private IFormBuyFundCertificateConsultingRepository formBuyFundCertificateConsultingRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDemandService demandService;

    @Autowired
    private DemandFormService demandFormService;
    @Autowired
    private DataSummaryTableService dataSummaryTableService;

    public DemandForm saveFormBuyFundCertificateConsulting(FormBuyFundCertificateConsultingReq fundCertificateConsultingReq) {
        Long idUser = (long) Math.toIntExact(getCurrentUser().getId());
        User user = userService.FindUserById(idUser);
        FormBuyFundCertificateConsulting formBuyFundCertificateConsulting = new FormBuyFundCertificateConsulting();
        formBuyFundCertificateConsulting.setCreatedAt(new Date());
        formBuyFundCertificateConsulting.setUpdatedAt(new Date());
        formBuyFundCertificateConsulting.setCreatedByUserId(idUser);
        formBuyFundCertificateConsulting.setCreatedBy(getCurrentUser().getUsername());
        formBuyFundCertificateConsulting.setNameOfFund(fundCertificateConsultingReq.getNameOfFund());
        formBuyFundCertificateConsulting.setMinimumNumberOfRegisteredForSale(fundCertificateConsultingReq.getMinimumNumberOfRegisteredForSale());
        formBuyFundCertificateConsulting.setDenominations(fundCertificateConsultingReq.getDenominations());
        formBuyFundCertificateConsulting.setDeadlineForReceivingRegistrationPaymentForm(fundCertificateConsultingReq.getDeadlineForReceivingRegistrationPaymentForm());
        formBuyFundCertificateConsulting.setDeadlineForReceivingRegistrationPaymentTo(fundCertificateConsultingReq.getDeadlineForReceivingRegistrationPaymentTo());
        formBuyFundCertificateConsulting.setMinimumNumberOfPurchased(fundCertificateConsultingReq.getMinimumNumberOfPurchased());
        formBuyFundCertificateConsulting.setValidityOfRegistrationForOfferingFrom(fundCertificateConsultingReq.getValidityOfRegistrationForOfferingFrom());
        formBuyFundCertificateConsulting.setValidityOfRegistrationForOfferingTo(fundCertificateConsultingReq.getValidityOfRegistrationForOfferingTo());
        formBuyFundCertificateConsulting.setFundInvestmentFields(fundCertificateConsultingReq.getFundInvestmentFields());
        formBuyFundCertificateConsulting.setFundInvestmentObjective(fundCertificateConsultingReq.getFundInvestmentObjective());
        formBuyFundCertificateConsulting.setFundInvestmentStrategy(fundCertificateConsultingReq.getFundInvestmentStrategy());
        formBuyFundCertificateConsulting.setFundBenefits(fundCertificateConsultingReq.getFundBenefits());
        formBuyFundCertificateConsulting.setTags("Chứng chỉ quỹ");
        Demand demand = demandService.getDemandById(2L);
        formBuyFundCertificateConsulting.setUser(user);
        formBuyFundCertificateConsulting.setDemand(demand);
        FormBuyFundCertificateConsulting formBuyFundCertificateConsulting1 = formBuyFundCertificateConsultingRepository.save(formBuyFundCertificateConsulting);
        DataSummaryTable dataSummaryTable = new DataSummaryTable();
        dataSummaryTable.setTags("Chứng chỉ quỹ");
        dataSummaryTable.setIdChildTable(formBuyFundCertificateConsulting1.getId());
        dataSummaryTable.setDemandId(2L);
        dataSummaryTable.setDemandType(demand.getType());
        dataSummaryTable.setCreatedDate(new Date());
        dataSummaryTable.setIdCustomer(idUser);
        dataSummaryTable.setStatus(1);
        dataSummaryTable.setNameOfFund(fundCertificateConsultingReq.getNameOfFund());
        dataSummaryTable.setMinimumNumberOfRegisteredForSale(fundCertificateConsultingReq.getMinimumNumberOfRegisteredForSale());
        dataSummaryTable.setDenominations(fundCertificateConsultingReq.getDenominations());
        dataSummaryTable.setDeadlineForReceivingRegistrationPaymentForm(fundCertificateConsultingReq.getDeadlineForReceivingRegistrationPaymentForm());
        dataSummaryTable.setDeadlineForReceivingRegistrationPaymentTo(fundCertificateConsultingReq.getDeadlineForReceivingRegistrationPaymentTo());
        dataSummaryTable.setMinimumNumberOfPurchased(fundCertificateConsultingReq.getMinimumNumberOfPurchased());
        dataSummaryTable.setValidityOfRegistrationForOfferingFrom(fundCertificateConsultingReq.getValidityOfRegistrationForOfferingFrom());
        dataSummaryTable.setValidityOfRegistrationForOfferingTo(fundCertificateConsultingReq.getValidityOfRegistrationForOfferingTo());
        dataSummaryTable.setFundInvestmentFields(fundCertificateConsultingReq.getFundInvestmentFields());
        dataSummaryTable.setFundInvestmentObjective(fundCertificateConsultingReq.getFundInvestmentObjective());
        dataSummaryTable.setFundInvestmentStrategy(fundCertificateConsultingReq.getFundInvestmentStrategy());
        dataSummaryTable.setFundBenefits(fundCertificateConsultingReq.getFundBenefits());
        DataSummaryTable dataSummaryTable1 = dataSummaryTableService.save(dataSummaryTable);
        DemandFormReq demandFormReq = new DemandFormReq();
        demandFormReq.setIdCustomer(dataSummaryTable1.getId());
        return demandFormService.save(demandFormReq);
    }
    public FormBuyFundCertificateConsulting findById(Long id) {
        return formBuyFundCertificateConsultingRepository.findById(id).orElse(null);
    }
    public void deleteFormBuyFundCertificateConsulting(Long id) {
        formBuyFundCertificateConsultingRepository.deleteById(id);
    }
}

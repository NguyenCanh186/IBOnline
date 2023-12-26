package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.core.base.BaseService;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.demand.entity.Demand;
import com.vmg.ibo.demand.service.IDemandService;
import com.vmg.ibo.form_demand.model.form_buy_fund_certificate_consulting.FormBuyFundCertificateConsulting;
import com.vmg.ibo.form_demand.model.form_buy_fund_certificate_consulting.FormBuyFundCertificateConsultingReq;
import com.vmg.ibo.form_demand.repository.IFormBuyFundCertificateConsultingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FormBuyFundCertificateConsultingService extends BaseService {
    @Autowired
    private IFormBuyFundCertificateConsultingRepository formBuyFundCertificateConsultingRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDemandService demandService;

    public void saveFormBuyFundCertificateConsulting(FormBuyFundCertificateConsultingReq fundCertificateConsultingReq) {
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
        formBuyFundCertificateConsulting.setMinimumNumberOfPurchased(fundCertificateConsultingReq.getMinimumNumberOfPurchased());
        formBuyFundCertificateConsulting.setValidityOfRegistrationForOffering(fundCertificateConsultingReq.getValidityOfRegistrationForOffering());
        formBuyFundCertificateConsulting.setFundInvestmentFields(fundCertificateConsultingReq.getFundInvestmentFields());
        formBuyFundCertificateConsulting.setFundInvestmentObjective(fundCertificateConsultingReq.getFundInvestmentObjective());
        formBuyFundCertificateConsulting.setFundInvestmentStrategy(fundCertificateConsultingReq.getFundInvestmentStrategy());
        formBuyFundCertificateConsulting.setFundBenefits(fundCertificateConsultingReq.getFundBenefits());
        Demand demand = demandService.getDemandById(fundCertificateConsultingReq.getIdDemand());
        formBuyFundCertificateConsulting.setUser(user);
        formBuyFundCertificateConsulting.setDemand(demand);
        formBuyFundCertificateConsultingRepository.save(formBuyFundCertificateConsulting);
    }
    public FormBuyFundCertificateConsulting findById(Long id) {
        return formBuyFundCertificateConsultingRepository.findById(id).orElse(null);
    }
    public void deleteFormBuyFundCertificateConsulting(Long id) {
        formBuyFundCertificateConsultingRepository.deleteById(id);
    }
}

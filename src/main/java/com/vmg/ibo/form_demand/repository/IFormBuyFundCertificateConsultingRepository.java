package com.vmg.ibo.form_demand.repository;

import com.vmg.ibo.form_demand.model.form_buy_fund_certificate_consulting.FormBuyFundCertificateConsulting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFormBuyFundCertificateConsultingRepository extends JpaRepository<FormBuyFundCertificateConsulting, Long> {

}

package com.vmg.ibo.form_demand.repository;

import com.vmg.ibo.form_demand.model.sell_​​fund_certificates.SellFundCertificates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISellFundCertificatesRepository extends JpaRepository<SellFundCertificates, Long> {
}

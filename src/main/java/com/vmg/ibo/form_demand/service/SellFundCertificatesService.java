package com.vmg.ibo.form_demand.service;

import com.vmg.ibo.form_demand.model.sell_​​fund_certificates.InvestmentPortfolio;
import com.vmg.ibo.form_demand.model.sell_​​fund_certificates.SellFundCertificates;
import com.vmg.ibo.form_demand.repository.ISellFundCertificatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellFundCertificatesService {
    @Autowired
    private ISellFundCertificatesRepository sellFundCertificatesRepository;
    public SellFundCertificates save(SellFundCertificates sellFundCertificates) {
        return sellFundCertificatesRepository.save(sellFundCertificates);
    }
}

package com.vmg.ibo.form_demand.repository;

import com.vmg.ibo.form_demand.model.buy_stock_consulting.BuyStockConsulting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBuyStockConsultingRepository extends JpaRepository<BuyStockConsulting, Long> {
}

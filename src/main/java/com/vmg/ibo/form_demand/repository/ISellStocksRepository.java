package com.vmg.ibo.form_demand.repository;

import com.vmg.ibo.form_demand.model.sell_stocks.SellStocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISellStocksRepository extends JpaRepository<SellStocks, Long> {
}

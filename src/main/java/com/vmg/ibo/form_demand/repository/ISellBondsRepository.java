package com.vmg.ibo.form_demand.repository;

import com.vmg.ibo.form_demand.model.sell_​​bonds.SellBonds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISellBondsRepository extends JpaRepository<SellBonds, Long> {
}

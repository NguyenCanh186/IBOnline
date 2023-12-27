package com.vmg.ibo.form_demand.repository;

import com.vmg.ibo.form_demand.model.buy_bond_consulting.BuyBondConsulting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBuyBondConsultingRepository extends JpaRepository<BuyBondConsulting, Long> {
}

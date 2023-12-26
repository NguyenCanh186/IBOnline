package com.vmg.ibo.demand.repository;

import com.vmg.ibo.demand.entity.Demand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDemandRepository extends JpaRepository<Demand, Long> {
}

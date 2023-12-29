package com.vmg.ibo.form_demand.repository;

import com.vmg.ibo.form_demand.model.DemandForm;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDemandFormRepository extends JpaRepository<DemandForm, Long> {
    @Query(value = "SELECT df.codeDemand as codeDemad FROM DemandForm df")
    List<String> getAllCodeDemand();
}

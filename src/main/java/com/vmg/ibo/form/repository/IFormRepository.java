package com.vmg.ibo.form.repository;

import com.vmg.ibo.core.model.entity.Option;
import com.vmg.ibo.form.dto.DemandDTO;
import com.vmg.ibo.form.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFormRepository extends JpaRepository<Form, Long> {
    List<Form> findAllByTemplateIdIn(List<Long> ids);

    List<Form> findTop3ByTemplateIdInAndUserIdNotOrderByCreatedAtDesc(List<Long> ids, Long userId);

    @Query(value = "SELECT f.codeDemand FROM Form f")
    List<String> getAllCodeDemand();

    Optional<Form> findByPartnerId(Long partnerId);

    @Query(value = "SELECT f.id as id, f.codeDemand as codeDemand" +
            ", t.name as demandName, t.type as demandType," +
            "u.businessName as customerName, f.createdAt as createdAt," +
            "f.status as status FROM Form f join Template t on f.template.id = t.id join UserDetail u on f.user.id = u.id")
    List<DemandDTO> getAllDemand();

}

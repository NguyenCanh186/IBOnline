package com.vmg.ibo.form.repository;

import com.vmg.ibo.core.model.entity.Option;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.form.dto.DemandDTO;
import com.vmg.ibo.form.entity.Form;
import com.vmg.ibo.form.model.DemandReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IFormRepository extends JpaRepository<Form, Long> {
    List<Form> findAllByTemplateIdIn(List<Long> ids);

    List<Form> findTop3ByTemplateIdInAndUserIdNotAndPartnerIdNullOrderByCreatedAtDesc(List<Long> ids, Long userId);

    Form findByTemplateIdAndUserId(Long templateId, Long userId);

    @Query(value = "SELECT f.codeDemand FROM Form f")
    List<String> getAllCodeDemand();

    Optional<Form> findByPartnerId(Long partnerId);

    @Query(value = "SELECT f.id as id, f.codeDemand as codeDemand" +
            ", t.name as demandName, t.type as demandType," +
            "u.name as customerName, f.createdAt as createdAt," +
            "f.status as status FROM Form f join Template t on f.template.id = t.id join User u on f.user.id = u.id " +
            "where ((:#{#demandReq.query} is null) or (LOWER(f.codeDemand) like %:#{#demandReq.query}% or LOWER(u.name) like %:#{#demandReq.query}% or LOWER(t.name) like %:#{#demandReq.query}%)) " +
            "and (:#{#demandReq.demandName} is null or LOWER(t.name) like %:#{#demandReq.demandName}%) " +
            "and t.type in (:#{#demandReq.demandType})" +
            "and f.status in (:#{#demandReq.status}) and " +
            "(:#{#demandReq.createdAt} is null or :#{#demandReq.createdAt} = '' or DATE(f.createdAt) = COALESCE(DATE(:#{#demandReq.createdAt}), DATE(f.createdAt))) " +
            "and (:#{#userId} is null or f.user.id = :#{#userId})"
            )
    Page<DemandDTO> getAllDemand(DemandReq demandReq,Long userId, Pageable pageable);

    Page<Form> findByUser(User user, Pageable pageable);

    List<Form> findAllByUserIdAndStatus(Long userId, Integer status);

    @Query(value = "SELECT f.dealCode FROM Form f")
    List<String> getAllDealCode();
}

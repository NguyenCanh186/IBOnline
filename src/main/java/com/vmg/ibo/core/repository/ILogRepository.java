package com.vmg.ibo.core.repository;

import com.vmg.ibo.core.model.entity.LogAction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILogRepository extends JpaRepository<LogAction, Long> {

    @Query(value = "SELECT l from LogAction l WHERE (:query IS null OR TO_CHAR(l.createdAt, 'dd/MM/yyyy hh:mm:ss') LIKE %:query%" +
            "OR LOWER(l.actionDescription) like %:query% OR l.ipAddress LIKE %:query% OR l.actionType LIKE %:query%" +
            "OR l.originalValue LIKE %:query% OR l.actionData LIKE %:query%)")
    Page<LogAction> findAllByFilter(String query, PageRequest pageable);
}

package com.vmg.ibo.core.repository;

import com.vmg.ibo.core.model.entity.Permission;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPermissionRepository extends JpaRepository<Permission, Long> {
    @Query("SELECT DISTINCT p FROM User u " +
            "JOIN u.roles r " +
            "JOIN r.permissions p " +
            "WHERE ( :#{#userId} is null or u.id = :userId) " +
            "ORDER BY p.order")
    List<Permission> findPermissionsByUserId(@Param("userId") Long userId);

    List<Permission> findAllByNameContaining(String name, Sort sort);

    List<Permission> findByParentIn(List<Permission> parents);
}

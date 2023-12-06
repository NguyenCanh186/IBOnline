package com.vmg.ibo.core.repository;

import com.vmg.ibo.core.model.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT  r FROM Role r where (:name is null OR lower(r.name) LIKE  %:name% or lower(r.code) LIKE %:name%) ")
    Page<Role> findAllByNameContaining(String name, Pageable pageable);

    @Query("SELECT r FROM Role r WHERE r.status = 1")
    List<Role> findAllRoleActive();
    Role findByMerchantDefault(Integer merchantDefault);
}

package com.vmg.ibo.customer.repository;

import com.vmg.ibo.customer.model.CodeAndEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICodeAndEmailRepo extends JpaRepository<CodeAndEmail, Long> {
    @Query(value = "SELECT c FROM CodeAndEmail c WHERE c.code = :code")
    CodeAndEmail findByCode(@Param("code") String code);

    @Query(value = "SELECT c.code FROM CodeAndEmail c")
    List<String> findAllCode();

    @Query(value = "SELECT c.email FROM CodeAndEmail c")
    List<String> findAllEmail();
}

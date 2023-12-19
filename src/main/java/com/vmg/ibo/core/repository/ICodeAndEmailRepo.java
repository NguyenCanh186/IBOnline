package com.vmg.ibo.core.repository;

import com.vmg.ibo.core.model.entity.CodeAndEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICodeAndEmailRepo extends JpaRepository<CodeAndEmail, Long> {
    @Query(value = "SELECT c FROM CodeAndEmail c WHERE c.code = :code")
    CodeAndEmail findByCode(@Param("code") String code);

    @Query(value = "SELECT c.code FROM CodeAndEmail c")
    String[] findAllCode();
}

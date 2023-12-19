package com.vmg.ibo.core.repository;

import com.vmg.ibo.core.model.entity.CodeAndEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICodeAndEmailRepo extends JpaRepository<CodeAndEmail, Long> {
}

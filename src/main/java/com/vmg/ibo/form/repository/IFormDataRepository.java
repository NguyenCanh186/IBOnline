package com.vmg.ibo.form.repository;

import com.vmg.ibo.form.entity.FormData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFormDataRepository extends JpaRepository<FormData, Long> {
}

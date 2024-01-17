package com.vmg.ibo.form.repository;

import com.vmg.ibo.form.entity.FormField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFormFieldRepository extends JpaRepository<FormField, Long> {
}

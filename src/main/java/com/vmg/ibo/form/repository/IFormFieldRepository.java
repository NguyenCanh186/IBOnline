package com.vmg.ibo.form.repository;

import com.vmg.ibo.form.entity.FormField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFormFieldRepository extends JpaRepository<FormField, Long> {
    @Query(value = "SELECT f FROM FormField f WHERE f.form.id = :formId")
    List<FormField> getFormFieldsByFormId(@Param("formId") Long formId);
}

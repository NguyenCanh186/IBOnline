package com.vmg.ibo.form.repository;

import com.vmg.ibo.form.entity.TemplateField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITemplateFieldRepository extends JpaRepository<TemplateField, Long> {
    List<TemplateField> getTemplateFieldsByTemplateId(Long templateId);
}

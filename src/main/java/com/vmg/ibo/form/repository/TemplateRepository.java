package com.vmg.ibo.form.repository;

import com.vmg.ibo.form.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findAllByType(Integer type);
    List<Template> findAllByTypeAndIdIn(Integer type,List<Long> ids);

    Template findBySlug(String slug);
}

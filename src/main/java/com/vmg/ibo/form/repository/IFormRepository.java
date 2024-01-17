package com.vmg.ibo.form.repository;

import com.vmg.ibo.core.model.entity.Option;
import com.vmg.ibo.form.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFormRepository extends JpaRepository<Form, Long> {

    @Query ("SELECT f FROM Form f WHERE f.slug = :slug")
    Optional<Form> findBySlug(@Param("slug") String slug);
}

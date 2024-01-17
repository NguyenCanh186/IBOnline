package com.vmg.ibo.form.repository;

import com.vmg.ibo.form.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFormRepository extends JpaRepository<Form, Long> {
}

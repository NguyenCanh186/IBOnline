package com.vmg.ibo.contact_form.repository;

import com.vmg.ibo.contact_form.model.entity.ContactForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IContactFormRepository extends JpaRepository<ContactForm, Long> {
}

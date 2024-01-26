package com.vmg.ibo.contact_form.model.entity;

import com.vmg.ibo.core.base.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "contact_form")
public class ContactForm extends BaseEntity {
    @Column(columnDefinition = "VARCHAR(150)")
    private String fullname;
    @Column(columnDefinition = "VARCHAR(10)")
    private String phone;
    private String email;
    @Column(columnDefinition = "VARCHAR(500)")
    private String description;
}

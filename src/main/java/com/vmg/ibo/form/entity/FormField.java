package com.vmg.ibo.form.entity;

import com.vmg.ibo.core.base.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "FORM_FIELD")
public class FormField  extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "template_field_id")
    private TemplateField templateField;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    private String value;
}

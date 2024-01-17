package com.vmg.ibo.form.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vmg.ibo.core.base.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "TEMPLATE_FIELD")
public class TemplateField extends BaseEntity {
    private String name;
    private String type;
    private String metadata;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    private String rules;
    private String prop;
    @JsonIgnore
    @OneToMany(mappedBy = "templateField", cascade = CascadeType.ALL)
    private List<FormField> formFields;

}

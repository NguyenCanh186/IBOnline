package com.vmg.ibo.form.entity;

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

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    private String rules;
    private String prop;

    @OneToMany(mappedBy = "templateField", cascade = CascadeType.ALL)
    private List<FormField> formFields;

}

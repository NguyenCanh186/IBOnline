package com.vmg.ibo.form.entity;

import com.vmg.ibo.core.base.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "template")
public class Template extends BaseEntity {
    private String name;
    private String slug;
    private String tag;
    private Integer type;
    private String image;

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL)
    private List<TemplateField> templateFields;

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL)
    private List<Form> forms;

}

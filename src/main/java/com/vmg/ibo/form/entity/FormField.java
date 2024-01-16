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
@Table(name = "FORM_FIELD")
public class FormField extends BaseEntity {
    private String name;
    private String type;
    private String metadata;
    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;
    private String validate;

    @OneToMany(mappedBy = "formField", cascade = CascadeType.ALL)
    private List<FormData> data;

}
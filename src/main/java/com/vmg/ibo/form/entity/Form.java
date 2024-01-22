package com.vmg.ibo.form.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vmg.ibo.core.base.BaseEntity;
import com.vmg.ibo.core.model.entity.User;
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
@Table(name = "FORM")
public class Form extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL)
    private List<FormField> formFields;

    private String codeDemand;

    private Integer status;

    private Long partnerId;

    private String dealCode;
}

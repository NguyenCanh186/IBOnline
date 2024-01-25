package com.vmg.ibo.deal.model.entity;

import com.vmg.ibo.core.base.BaseEntity;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.form.entity.Form;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "deals")
public class Deal extends BaseEntity {
    private String code;
    @ManyToOne
    private Form first;
    @ManyToOne
    private Form second;
    @ManyToOne
    private User coordinator;
    private Date connectionDate;
    private Integer status;
}

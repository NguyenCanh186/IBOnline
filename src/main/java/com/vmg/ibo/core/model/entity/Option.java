package com.vmg.ibo.core.model.entity;

import com.vmg.ibo.core.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "SYS_OPTIONS")
public class Option extends BaseEntity {
    @Column(name="`KEY`")
    private String key;

    @Column(name="`VALUE`")
    private String value;

    @Column(name="`GROUP`")
    private String group;

    private String description;
}

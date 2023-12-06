package com.vmg.ibo.core.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.vmg.ibo.core.base.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "SYS_PERMISSIONS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Permission extends BaseEntity {
    private String name;

    private String slug;
    private String code;

    private String componentName;

    private String icon;

    private Boolean hidden;

    private Boolean hasChild;

    private String description;

    private Integer status;

    private String groupId;

    private String groupName;

    @Column(name = "\"order\"")
    private Integer order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    @ToString.Exclude
    @JsonIgnoreProperties("children")
    private Permission parent;

    @OneToMany(mappedBy = "parent")
    @ToString.Exclude
    @JsonIgnoreProperties("parent")
    private List<Permission> children;

    @Transient
    private Long parentId;

    @ManyToMany(mappedBy = "permissions")
    @JsonBackReference
    @ToString.Exclude
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Permission that = (Permission) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

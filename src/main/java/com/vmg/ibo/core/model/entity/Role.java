package com.vmg.ibo.core.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vmg.ibo.core.base.BaseEntity;
import com.vmg.ibo.core.constant.RoleConstant;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "SYS_ROLES")
public class Role extends BaseEntity {
    private String name;

    private String code;

    private String description;

    private Integer status = RoleConstant.ENABLE.getValue();

    private Integer merchantDefault = RoleConstant.MERCHANT_NOT_DEFAULT.getValue();

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    @ToString.Exclude
    private Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SYS_ROLES_PERMISSIONS",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    @JsonManagedReference
    @ToString.Exclude
    private Set<Permission> permissions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return getId() != null && Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

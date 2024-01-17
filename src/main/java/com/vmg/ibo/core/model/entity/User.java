package com.vmg.ibo.core.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vmg.ibo.core.base.BaseEntity;
import com.vmg.ibo.form.entity.Form;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "SYS_USERS")
public class User extends BaseEntity {

    private String username;

    private String password;

    private Integer channelId;

    private String channelName;

    private String avatar;

    private Integer status;

    private Boolean isAdminRoot;

    private Boolean isResetPass;

    private String lastIp;

    private Date lastLogin;

    private String name;

    private String email;

    private String phone;

    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "SYS_USERS_ROLES",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonManagedReference
    @ToString.Exclude
    private Set<Role> roles;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Form> forms;

//    @OneToMany(mappedBy = "user")
//    @JsonIgnore
//    private List<FinancialInvestment> financialInvestments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getId() != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void reverseStatus() {
        if (this.status == 1) {
            this.status = 2;
            return;
        }
        this.status = 1;
    }
}

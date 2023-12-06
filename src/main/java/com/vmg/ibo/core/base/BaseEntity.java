package com.vmg.ibo.core.base;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
@DynamicUpdate
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_by_user_id")
    private Long createdByUserId;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_by_user_id")
    private Long updatedByUserId;
}

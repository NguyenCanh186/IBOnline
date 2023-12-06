package com.vmg.ibo.core.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "log_action")
@Data
public class LogAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String ipAddress;

    private Date createdAt;

    private String actionType;

    private String actionDescription;

    private String actionData;

    private Integer impactLevel;

    private String originalValue;

    private String changedValue;

    private String affectTo;
}

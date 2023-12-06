package com.vmg.ibo.core.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LogDTO {
    private Long id;

    private String username;

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

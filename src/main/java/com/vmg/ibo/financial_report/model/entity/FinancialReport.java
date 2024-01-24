package com.vmg.ibo.financial_report.model.entity;

import com.vmg.ibo.core.base.BaseEntity;
import com.vmg.ibo.core.model.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "financial_report")
public class FinancialReport extends BaseEntity {
    private String title;
    private Long revenue;
    private Long profit;
    private Long asset;
    private Long debt;
    private Integer year;
    private Integer quarter;
    @ManyToOne
    private User user;
}

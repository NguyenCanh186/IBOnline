package com.vmg.ibo.form_demand.model;

import com.vmg.ibo.core.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DEMAND_FORM")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DemandForm extends BaseEntity {
    private String codeDemand;
    private String nameDemand;
    private Long idCustomer;
    private Long idPartner;
    private Integer status;
    private Long idUser;
}

package com.vmg.ibo.core.model.dto.filter;

import com.vmg.ibo.core.base.BaseFilter;
import lombok.Data;

@Data
public class UserFilter extends BaseFilter {
    private String email;
    private String username;
    private String query;
    private Boolean isCustomerPersonal;
    private Integer status;
    private Long fromCapitalSize;
    private Long toCapitalSize;
}

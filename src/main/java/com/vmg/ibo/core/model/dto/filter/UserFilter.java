package com.vmg.ibo.core.model.dto.filter;

import com.vmg.ibo.core.base.BaseFilter;
import lombok.Data;

import java.util.Objects;

@Data
public class UserFilter extends BaseFilter {
    private String email;
    private String username;
    private String query;
    private Boolean isCustomerPersonal;
    private Integer status;
    private Long fromCapitalSize;
    private Long toCapitalSize;

    public String getEmail() {
        return Objects.isNull(email) ? null : email.trim().replace("%", "\\%");
    }

    public String getUsername() {
        return Objects.isNull(username) ? null : username.trim().replace("%", "\\%");
    }

    public String getQuery() {
        return Objects.isNull(query) ? null : query.trim().replace("%", "\\%");
    }
}

package com.vmg.ibo.core.model.dto.filter;

import com.vmg.ibo.core.base.BaseFilter;
import lombok.Data;

@Data
public class UserFilter extends BaseFilter {
    private String username;
}

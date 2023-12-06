package com.vmg.ibo.core.model.dto.filter;

import com.vmg.ibo.core.base.BaseFilter;
import com.vmg.ibo.core.utils.CommonUtils;
import lombok.Data;

@Data
public class RoleFilter extends BaseFilter {
    private String name;

    public String getName() {
        if(CommonUtils.empty(this.name)) {
            return null;
        }
        return name.trim().toLowerCase();
    }
}

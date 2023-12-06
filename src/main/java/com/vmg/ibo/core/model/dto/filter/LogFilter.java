package com.vmg.ibo.core.model.dto.filter;

import com.vmg.ibo.core.base.BaseFilter;
import com.vmg.ibo.core.utils.CommonUtils;
import lombok.Data;

@Data
public class LogFilter extends BaseFilter {

    private String query;

    public String getQuery() {
        if(CommonUtils.empty(this.query)) {
            return null;
        }
        return query.toLowerCase().trim();
    }
}

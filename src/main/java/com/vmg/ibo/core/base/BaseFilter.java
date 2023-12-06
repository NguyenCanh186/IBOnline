package com.vmg.ibo.core.base;

import com.vmg.ibo.core.constant.PageConstant;
import lombok.Data;

@Data
public class BaseFilter {
    private Integer page = PageConstant.DEFAULT_PAGE.getValue();
    private Integer limit = PageConstant.DEFAULT_LIMIT.getValue();
}

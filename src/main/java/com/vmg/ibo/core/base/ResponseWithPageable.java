package com.vmg.ibo.core.base;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;


@Getter
public class ResponseWithPageable<T> {

    private final long total;

    private final List<T> data;

    private ResponseWithPageable(long total, List<T> data) {
        this.total = total;
        this.data = data;
    }

    public static <T> ResponseWithPageable<T> fromPageable(Page<T> page) {
        return new ResponseWithPageable<>(page.getTotalElements(), page.getContent());
    }
}

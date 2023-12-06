package com.vmg.ibo.core.base;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Log4j2
public class BasePage<T> {
    private List<T> content;
    private Metadata metadata;

    public BasePage(Page<T> page) {
        content = page.getContent();
        metadata = new Metadata(page);
    }

    public BasePage(List<T> list, Page page) {
        content = list;
        metadata = new Metadata(page);
    }

    @Data
    public static class Metadata {
        private int page = 0;
        private int size = 20;
        private long total = 0;
        private int totalPage = 0;

        public <T> Metadata(Page<T> page) {
            size = page.getSize();
            this.page = page.getNumber();
            this.total = page.getTotalElements();
            this.totalPage = page.getTotalPages();
        }
    }
}

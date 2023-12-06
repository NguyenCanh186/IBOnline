package com.vmg.ibo.core.config.formatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToIntConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String source) {
        try {
            return Integer.parseInt(source);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

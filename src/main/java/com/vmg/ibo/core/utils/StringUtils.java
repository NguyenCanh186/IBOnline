package com.vmg.ibo.core.utils;

import com.vmg.ibo.core.base.BaseFilter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

public class StringUtils {
    public static String convertToSnake(String camel) {
        return Arrays.stream(camel.split(""))
                .map((c) -> {
                    if (Character.isUpperCase(c.charAt(0))) {
                        return "_" + c.toLowerCase();
                    }
                    return c;
                }).collect(Collectors.joining());
    }

    public static boolean isBlank(String data) {
        return data.equals("");
    }

    public static <T extends BaseFilter> String convertFilterObjectToWhereClause(T filter) {
        StringBuilder result = new StringBuilder();
        Field[] fields = filter.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object data = field.get(filter);
                if (Objects.isNull(data)) {
                    continue;
                }
                if (data instanceof String && StringUtils.isBlank(data.toString())) {
                    continue;
                }
                String columnName = StringUtils.convertToSnake(field.getName());
                if (data instanceof String) {
                    result.append(" AND ")
                            .append(columnName)
                            .append(" LIKE '%")
                            .append(data)
                            .append("%'");
                } else if (!(data instanceof Date)) {
                    result.append(" AND ")
                            .append(columnName)
                            .append(" = ")
                            .append(data);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return result.toString();
    }
}

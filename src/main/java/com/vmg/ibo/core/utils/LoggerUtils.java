package com.vmg.ibo.core.utils;

import com.vmg.ibo.core.config.SpringContext;
import com.vmg.ibo.core.model.entity.LogAction;
import com.vmg.ibo.core.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Objects;

public class LoggerUtils {
    public static void log(LogAction logAction, Class<?> clazz) {
        Logger logger = LoggerFactory.getLogger(clazz.getCanonicalName());
        LogService logService = SpringContext.getBean(LogService.class);
        logAction = logService.save(logAction);
        logger.info("Action Type: {}, User Id: {}, IP Address: {}, Impact Level: {}, Affect To: {}, Original Value: {}, Change Value: {}, Action Data: {}", logAction.getActionType(), logAction.getUserId(), logAction.getIpAddress(), logAction.getImpactLevel(), logAction.getAffectTo(), logAction.getOriginalValue(), logAction.getChangedValue(), logAction.getActionData());
    }

    private static String formatObjectComparisonToString(Object oldObj, Object newObj) {
        StringBuilder stringBuilder = new StringBuilder();
        Class<?> clazz = oldObj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object oldValue;
            Object newValue;

            try {
                oldValue = field.get(oldObj);
                newValue = field.get(newObj);
            } catch (IllegalAccessException e) {
                oldValue = "N/A";
                newValue = "N/A";
            }

            if (!Objects.equals(oldValue, newValue)) {
                stringBuilder.append(fieldName)
                        .append(": ")
                        .append(oldValue)
                        .append(" -> ")
                        .append(newValue)
                        .append(", ");
            }
        }

        int length = stringBuilder.length();
        if (length > 2) {
            stringBuilder.delete(length - 2, length);
        }

        return stringBuilder.toString();
    }

    private static String formatObjectToString(Object obj) {
        StringBuilder stringBuilder = new StringBuilder();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue;
            try {
                fieldValue = field.get(obj);
            } catch (IllegalAccessException e) {
                fieldValue = "N/A";
            }
            stringBuilder.append(fieldName).append(": ").append(fieldValue).append(", ");
        }

        int length = stringBuilder.length();
        if (length > 2) {
            stringBuilder.delete(length - 2, length);
        }

        return stringBuilder.toString();
    }
}

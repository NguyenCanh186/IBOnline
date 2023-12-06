package com.vmg.ibo.core.utils;

import com.vmg.ibo.core.config.annoutation.SPInParameter;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ClazzUtils {

    public static Field getField(Object object, String filedName) {
        Class<?> clazz = object.getClass();
        while (Object.class != clazz) {
            try {
                Field field = clazz.getDeclaredField(filedName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException ignored) {
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    public static <T> T set(T object, String filedName, Object value) {
        Field field = getField(object, filedName);
        if (field != null) {
            field.setAccessible(true);
            try {
                field.set(object, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    @SuppressWarnings("rawtypes")
    public static Class getTypeParam(Class clazz, int index) {
        ParameterizedType superclass = (ParameterizedType) clazz.getGenericSuperclass();
        return (Class) (superclass.getActualTypeArguments()[index]);
    }

    public static boolean exitsClass(String clazzName) {
        Class<?> aClass;
        try {
            aClass = Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return aClass != null;
    }


    public static <T> Map<String, Object> toMap(String param, T value, String clazzName) {
        Map<String, Object> rs = new HashMap<>();
        HashMap<String, Object> hashParams = new HashMap<>();
        hashParams.put("type", clazzName);
        hashParams.put("value", value);
        rs.put(param, hashParams);
        return rs;
    }

    public static <T> Map<String, Object> toMap(Map<String, Object> params,String param, T value, String clazzName) {
        if(CollectionUtils.isEmpty(params)){
            return toMap(param,value,clazzName);
        }
        HashMap<String, Object> hashParams = new HashMap<>();
        hashParams.put("type", clazzName);
        hashParams.put("value", value);
        params.put(param, hashParams);
        return params;
    }

    public static <T> Map<String, Object> toMapInclude(T t, String... include) {
        Class<?> klass = t.getClass();
        Map<String, Object> rs = new HashMap<>();
        try {
            for (Field field : klass.getDeclaredFields()) {
                try {
                    SPInParameter spInParameter = field.getDeclaredAnnotation(SPInParameter.class);

                    boolean isInclude = false;
                    if (include != null) for (int i = 0; i < include.length; i++) {
                        if (field.getName().equals(include[i])) {
                            isInclude = true;
                            break;
                        }
                    }
                    if (isInclude) {
                        Method getMethod = klass.getDeclaredMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
                        Type genericType = field.getGenericType();
                        Map<String, Object> map = new HashMap<>();
                        map.put("value", getMethod.invoke(t));
                        map.put("type", genericType.getTypeName());
                        rs.put(spInParameter.name(), map);
                    }
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }


    public static <T> Map<String, Object> toMap(T t, String... ignore) {
        Class<?> klass = t.getClass();
        Map<String, Object> rs = new HashMap<>();
        try {
            for (Field field : klass.getDeclaredFields()) {
                try {
                    SPInParameter spInParameter = field.getDeclaredAnnotation(SPInParameter.class);

                    boolean isIgnore = false;
                    if (ignore != null) for (int i = 0; i < ignore.length; i++) {
                        if (field.getName().equals(ignore[i])) {
                            isIgnore = true;
                            break;
                        }
                    }
                    if (!isIgnore) {
                        Method getMethod = klass.getDeclaredMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
                        Type genericType = field.getGenericType();
                        Map<String, Object> map = new HashMap<>();
                        map.put("value", getMethod.invoke(t));
                        map.put("type", genericType.getTypeName());
                        rs.put(spInParameter.name(), map);
                    }
                } catch (Exception ex) {
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }
}

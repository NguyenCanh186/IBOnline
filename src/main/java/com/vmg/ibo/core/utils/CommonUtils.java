package com.vmg.ibo.core.utils;

import java.io.File;
import java.util.Collection;
import java.util.Map;

public class CommonUtils {
	
    public static boolean notEmpty(String var) {
    	return var != null && var.length() > 0;
    }

    public static boolean empty(String var) {
        return var == null || var.length() == 0;
    }

    public static boolean notEmpty(Number var) {
        return null != var;
    }

    public static boolean empty(Number var) {
        return null == var;
    }

    public static boolean empty(Collection<?> var) {
        return null == var || var.isEmpty();
    }
    
    public static boolean notEmpty(Collection<?> var) {
        return null != var && !var.isEmpty();
    }

    public static boolean notEmpty(Map<?, ?> var) {
        return null != var && !var.isEmpty();
    }

    public static boolean notEmpty(File file) {
        return null != file && file.exists();
    }

    public static boolean empty(File file) {
        return null == file || !file.exists();
    }

    public static boolean notEmpty(Object[] var) {
        return null != var && 0 < var.length;
    }

    public static boolean empty(Object[] var) {
        return null == var || 0 == var.length;
    }
}

package com.vmg.ibo.core.utils;

import java.util.Random;

public class StringRandomUtils {
    private static final String CHARACTER_AND_NUMBER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomString(int length) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        int size = CHARACTER_AND_NUMBER.length();
        for (int i = 0; i < length; ++i) {
            result.append(CHARACTER_AND_NUMBER.charAt(random.nextInt(size)));
        }
        return "VMG@" + result;
    }
}

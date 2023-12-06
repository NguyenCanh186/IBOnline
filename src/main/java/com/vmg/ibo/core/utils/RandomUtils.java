package com.vmg.ibo.core.utils;

import java.security.SecureRandom;
import java.util.UUID;

public class RandomUtils {
	private static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyz";
	private static final String NUMBERS = "0123456789";
	private static SecureRandom rnd = new SecureRandom();
	
	
	public static String randomChars(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(CHARACTERS.charAt(rnd.nextInt(CHARACTERS.length())));
		return sb.toString();
	}
	
	public static String randomNumbers(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(NUMBERS.charAt(rnd.nextInt(NUMBERS.length())));
		return sb.toString();
	}
	
	public static int randomInt(int min, int max){
		return min + rnd.nextInt(max);
	}
	
	public static int randomInt(int max){
		return rnd.nextInt(max);
	}
	
	public static int randomInt(){
		return rnd.nextInt();
	}
	
	public static String nextUUID(){
		return UUID.randomUUID().toString().toLowerCase();
	}
	
	public static String generateRequestId() {
		return System.currentTimeMillis() + randomNumbers(3);
	}
}

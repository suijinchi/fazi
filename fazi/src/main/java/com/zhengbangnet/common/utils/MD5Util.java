package com.zhengbangnet.common.utils;

import java.security.MessageDigest;

public class MD5Util {
	// 不要改动
	private static final String salt = "3.1415";

	private final static char[] HEXD = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public final static String encode(String s) {
		return encode(s, null);
	}

	public final static String encodeWithSalt(String s) {
		return encode(s + "{" + salt + "}", null);
	}

	public final static String encodeWithSalt(String s, String salt) {
		return encode(s + "{" + salt + "}", null);
	}

	public final static String encode(String s, String encoding) {
		final char[] hexDigits = HEXD;
		try {
			if (encoding == null || encoding.trim() == "") {
				encoding = "utf-8";
			}
			byte[] strTemp = s.getBytes(encoding);
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
				str[(k++)] = hexDigits[(byte0 & 0xF)];
			}
			return new String(str).toLowerCase();
		} catch (Exception e) {
		}
		return null;
	}

	public static void main(String[] args) {
		
		System.out.println(MD5Util.encodeWithSalt("123"));
		
	}
	
}

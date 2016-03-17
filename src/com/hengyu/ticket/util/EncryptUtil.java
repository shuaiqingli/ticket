package com.hengyu.ticket.util;

import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 * 
 * @author wwang
 * 
 */
public class EncryptUtil {

	private static char HEX_Digits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	public static String MD5(String content) throws Exception {
		if (StringUtils.isNotBlank(content)) {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(content.getBytes("UTF-8"));
			byte[] hashArray = messageDigest.digest();
			int j = hashArray.length;
			int k = 0;
			char str[] = new char[j * 2];
			for (int i = 0; i < j; i++) {
				byte byte0 = hashArray[i];
				str[k++] = HEX_Digits[byte0 >>> 4 & 0xf];
				str[k++] = HEX_Digits[byte0 & 0xf];
			}
			return new String(str);
		}
		return null;
	}
	
	public static String MD5(byte[] content) throws NoSuchAlgorithmException {
		if (null != content && content.length > 0) {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(content);
			byte[] hashArray = messageDigest.digest();
			int j = hashArray.length;
			int k = 0;
			char str[] = new char[j * 2];
			for (int i = 0; i < j; i++) {
				byte byte0 = hashArray[i];
				str[k++] = HEX_Digits[byte0 >>> 4 & 0xf];
				str[k++] = HEX_Digits[byte0 & 0xf];
			}
			return new String(str);
		}
		return null;
	}
}

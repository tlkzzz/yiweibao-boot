package com.src.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTool {

	public static String md5(String string) {
		if (string == null) {
			return "";
		}
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			return "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			} else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return md5StrBuff.toString();
	}

	/**
	 * 产生一个指定范围内的随机数
	 * 
	 * @param iMax
	 * @return
	 */
	public static int getRandInt(int iMax) {
		return (int) (Math.random() * 900 + 100);
	}

	/**
	 * 产生指定长度的随机数
	 * 
	 * @param len
	 * @return
	 */
	public static String getRandomForLen(int len) {
		String str = "";
		Random ran = new Random();
		for (int i = 0; i < len; i++) {
			str += ran.nextInt(9);
		}
		return str;
	}

	/**
	 * 验证日期格式【yyyy-MM-dd】
	 * @param ds
	 * @return
	 */
	public static boolean checkDate(String ds) {
		try {
			String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
			Pattern p = Pattern.compile(eL);
			Matcher m = p.matcher(ds);

			boolean b = m.matches();
			if (b) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 验证日期格式【yyyy-MM-dd HH:mm:ss】
	 * @param ds
	 * @return
	 */
	public static boolean checkDateTime(String ds) {
		try {
			String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-9]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
			Pattern p = Pattern.compile(eL);
			Matcher m = p.matcher(ds);
			
			boolean b = m.matches();
			if (b) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 验证手机号码
	 * @param mobile
	 * @return
	 */
	public static boolean checkMobile(String mobile) {
		try {
			String eL = "^1[358]\\d{9}$";
			Pattern p = Pattern.compile(eL);
			Matcher m = p.matcher(mobile);

			boolean b = m.matches();
			if (b) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}

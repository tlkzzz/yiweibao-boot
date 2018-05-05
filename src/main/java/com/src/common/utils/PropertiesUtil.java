package com.src.common.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

@SuppressWarnings("unchecked")
public class PropertiesUtil {

	@SuppressWarnings("rawtypes")
	private static Hashtable hash = new Hashtable();
	private	static final  String FILENAME = "application.properties";
	static{
		hash.put("config",FILENAME);
	}
	private PropertiesUtil() {
		
	}
	/**
	 * 得到文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("resource")
	private static Properties getProperties(String fileName) {
		InputStream input = null;
		Properties prop = null;
		try {
			prop = (Properties) hash.get(fileName);
			if (prop == null) {
				try {
					input = new FileInputStream(fileName);// 全路径
				} catch (Exception ex) {
					if (fileName.startsWith("/")) {
						input = PropertiesUtil.class.getResourceAsStream(fileName);// 只有/+文件名字
					} else {
						input = PropertiesUtil.class.getResourceAsStream("/" + fileName);// 只有文件名字的
					}
				}
				prop = new Properties();
				prop.load(input);
				if (input != null) {
					input.close();
				}
			}
		} catch (Exception e) {
			return null;
		}
		return prop;
	}

	/**
	 * 通过key得到值
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key,String fileKey) {
		try {
			String fileName = (String) hash.get(fileKey);
			Properties prop = PropertiesUtil.getProperties(fileName);
			if (null == prop)
				return null;
			return prop.getProperty(key);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 通过key得到拥有默认的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key, String defaultValue,String fileKey) {
		try {
			String fileName = (String) hash.get(fileKey);
			Properties prop = PropertiesUtil.getProperties(fileName);
			if (null == prop)
				return null;
			return prop.getProperty(key, defaultValue);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 通过key得到数组值
	 * 
	 * @param key
	 * @return
	 */
	public static String getProperty(String key, String[] array,String fileKey) {
		try {
			String fileName = (String) hash.get(fileKey);
			Properties prop = PropertiesUtil.getProperties(fileName);
			if (null == prop)
				return null;
			String str = prop.getProperty(key);
			for (int i = 0; i < array.length; i++) {
				str = str.replaceAll("\\{" + (i + 1) + "\\}", array[i]);
			}
			return str;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * 对sql模板进行包装
	 * @return
	 */
	public static String warpSQL(String sqlTemplate,String warpContent){
		sqlTemplate = sqlTemplate.replaceAll("\\{1\\}",warpContent);
		return sqlTemplate;
	}
	
	public static void main(String[] args) {
//		String str = warpSQL(getProperty("sqlserver2005","sqltemplate"),"select row_number() over(order by id) as rownum,* from mt200909 where 1=1");
		while(true){
			String str = getProperty("call.system.person.1","","call");
			System.out.println(str);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}


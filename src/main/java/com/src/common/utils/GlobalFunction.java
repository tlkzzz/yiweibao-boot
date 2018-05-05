package com.src.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GlobalFunction {
	
	public static boolean gDebug = true;  //调试开关
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GlobalFunction test = new GlobalFunction();
		
		System.out.println(test.getDayOfMonthEx("201602"));
		

	}

	
	
	
	
	
	
	/*
	 * Name:	getDayOfMonth
	 * Para:    none
	 * func:    获取每个月有多少天的函数
	 */
	public static int getDayOfMonth(){
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		int day=aCalendar.getActualMaximum(Calendar.DATE);
		return day;
	}
	
	public static int getDayOfMonthEx(String month){
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		aCalendar.set(Calendar.YEAR, Integer.parseInt(month.substring(0, 4))); 
		aCalendar.set(Calendar.MONTH, Integer.parseInt(month.substring(4,6))-1); 
		int day=aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
		return day;
	}
	
	

	
	
	/*
	 * Name:	getYyyyMm
	 * Para:    none
	 * func:    获取系统日期的年月
	 */
	public static String getYyyyMm() { 
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");//设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}
	
	
}

package com.src.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {

//	private static Date date;
//	private static Calendar CALENDAR = Calendar.getInstance();
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
	
	/**
	 * 返回指定格式的日期
	 * @param string
	 * @param pattern
	 * @return
	 */
	public static Date convertToDate(String string,String pattern) {
		try {
			simpleDateFormat.applyPattern(pattern);
			return simpleDateFormat.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 取得当月天数
	 * */
	public static int getCurrentMonthLastDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 得到指定月的天数
	 * */
	public static int getMonthLastDay(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 返回指定年份
	 * 
	 * @param date
	 * @return
	 * @author yangpeng
	 * @datetime 2012-7-11 下午3:26:26
	 */
	public static int getYear(Date date) {
		String year = new SimpleDateFormat("yyyy").format(date);
		return Integer.parseInt(year);
	}

	/**
	 * 返回指定月份
	 * 
	 * @param date
	 * @return
	 * @author yangpeng
	 * @datetime 2012-7-11 下午3:26:39
	 */
	public static int getMonth(Date date) {
		String month = new SimpleDateFormat("MM").format(date);
		return Integer.parseInt(month);
	}

	/**
	 * 返回指定天
	 * 
	 * @param date
	 * @return
	 * @author yangpeng
	 * @datetime 2012-7-11 下午3:26:46
	 */
	public static int getDay(Date date) {
		String day = new SimpleDateFormat("dd").format(date);
		return Integer.parseInt(day);
	}

	/**
	 * 星期
	 * 
	 * @param date
	 * @return
	 * @author yangpeng
	 * @datetime 2012-7-11 下午3:25:55
	 */
	public static String getDayweek(Date date) {
		String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayinweek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		return weeks[dayinweek];
	}

	/**
	 * 返回当前年月日
	 * 
	 * @return
	 * @author yangpeng
	 * @datetime 2012-7-11 下午3:26:09
	 */
	public static String getNowDate() {
		Date date = new Date();
		String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		return nowDate;
	}

	// 判断闰年
	static boolean isLeap(int year) {
		if (((year % 100 == 0) && year % 400 == 0)
				|| ((year % 100 != 0) && year % 4 == 0))
			return true;
		else
			return false;
	}

	// 返回当月天数
	public static int getDays(int year, int month) {
		int days;
		int FebDay = 28;
		if (isLeap(year))
			FebDay = 29;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			days = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			days = 30;
			break;
		case 2:
			days = FebDay;
			break;
		default:
			days = 0;
			break;
		}
		return days;
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @author yangpeng
	 * @datetime 2012-7-11 下午3:27:03
	 */
	public static String getDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @author yangpeng
	 * @datetime 2012-7-11 下午3:26:56
	 */
	public static String getDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);
		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}
	
	@SuppressWarnings("unused")
//	public void beginAndEndOfDates() {
//		DateTime dt = new DateTime();
//		DateTime startOfMonth = dt.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
//		DateTime endOfMonth = dt.dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue();
//
//	}

	/**
	 * @param args
	 * @author yangpeng
	 * @datetime 2012-7-11 下午2:42:39
	 */
	public static void main(String[] args) {
		int month = DateUtils.getMonth(new Date());
		int year = DateUtils.getYear(new Date());
		int days = DateUtils.getDays(year, month);
//		System.out.println(days+":"+month);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, month-1);
		for (int i = 1; i <= days; i++) {
			c.set(Calendar.DAY_OF_MONTH, i);
			System.out.println(getDayAfter(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime())));
		}
	}

}

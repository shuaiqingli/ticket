package com.hengyu.ticket.util;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtil {
	public static final int SECOND = 1000;
	public static final int MINUTE = 60000;
	public static final int HOUR = 3600000;
	public static final int DAY = 86400000;
	public static final int WEEK = 604800000;
	public static final int YEAR = 1471228928;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private static SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	
	private static SimpleDateFormat hhmmFormat = new SimpleDateFormat("HH:mm");

	private static SimpleDateFormat weekFormat = new SimpleDateFormat("yyyy年M月d日");

	private static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
	private static final String[] WEEKS = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	public static final String[] MONTHS = { "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月" };

	public static String formatDate(Date date) {
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static String formatDate(Timestamp date) {
		Date date2 = new Date(date.getTime());
		return dateFormat.format(date2);
	}

	public static Date stringToDate(String strDate) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		}
		DateFormat tempformat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = tempformat.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date StringToTime(String strTime) {
		DateFormat tempformat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		try {
			if ((strTime == null) || (strTime.equals("null"))) {
				return null;
			}
			date = tempformat.parse(strTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String formatHHmm(Date date) {
		if (date == null) {
			return null;
		}
		return hhmmFormat.format(date);
	}

	public static Date StringToDatetime(String strDatetime) {
		DateFormat tempformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			if ((strDatetime == null) || (strDatetime.equals("null"))) {
				return null;
			}
			date = tempformat.parse(strDatetime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String formatTime(Date date) {
		if (date == null) {
			return null;
		}
		return timeFormat.format(date);
	}

	public static String formatDateTime(Date date) {
		if (date == null) {
			return null;
		}
		return datetimeFormat.format(date);
	}

	public static String formatDateTimeWeek(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(1);
		int weeknum = cal.get(7) - 1;
		return weekFormat.format(date) + WEEKS[weeknum];
	}

	public static int DateTimeToWeekNum(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(1);
		int weeknum = cal.get(7) - 1;
		return weeknum;
	}

	public static byte getMemberAge(Timestamp memberBirthday) {
		Calendar birthday = Calendar.getInstance();
		birthday.setTimeInMillis(memberBirthday.getTime());
		Calendar today = Calendar.getInstance();
		int age = today.get(1) - birthday.get(1);
		birthday.add(1, age);
		if (today.before(birthday)) {
			--age;
		}
		return (byte) age;
	}

	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	public static String getCurrentYear() {
		return yearFormat.format(new Date(System.currentTimeMillis()));
	}

	public static int getBirthYear(Timestamp birth) {
		int birth_year = 0;
		Calendar birthday = Calendar.getInstance();
		birthday.setTimeInMillis(birth.getTime());
		birth_year = birthday.get(1);
		return birth_year;
	}

	public static int getBirthMonth(Timestamp birth) {
		int birth_month = 0;
		Calendar birthday = Calendar.getInstance();
		birthday.setTimeInMillis(birth.getTime());
		birth_month = birthday.get(2) + 1;
		return birth_month;
	}

	public static int getBirthDay(Timestamp birth) {
		int birth_day = 0;
		Calendar birthday = Calendar.getInstance();
		birthday.setTimeInMillis(birth.getTime());
		birth_day = birthday.get(5);
		return birth_day;
	}

	public static String getPicPath() {
		StringBuffer picpath = new StringBuffer(50);
		Calendar date = Calendar.getInstance();
		picpath.append("/");
		picpath.append(date.get(5) % 8);
		picpath.append("/");
		picpath.append(date.get(12));
		picpath.append("/");
		picpath.append(date.get(13));
		picpath.append("/");
		return picpath.toString();
	}

	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static String timestampToString(Timestamp timestamp, boolean displayTime) {
		if (timestamp == null) {
			return "";
		}
		if (displayTime) {
			return timestamp.toString().substring(0, 16);
		}
		return timestamp.toString().substring(0, 10);
	}

	public static String timestampToStringSecond(Timestamp timestamp, boolean displayTime) {
		if (timestamp == null) {
			return "";
		}
		if (displayTime) {
			return timestamp.toString().substring(0, 19);
		}
		return timestamp.toString().substring(0, 10);
	}

	public static String timestampToString(Timestamp timestamp) {
		return timestampToString(timestamp, false);
	}

	public static Date calculatedays(Date date, int amount) {
		GregorianCalendar g = new GregorianCalendar();
		g.setGregorianChange(date);
		g.add(5, amount);
		Date d = g.getTime();

		return d;
	}

	public static Date calculatemonths(Date date, int amount) {
		GregorianCalendar g = new GregorianCalendar();
		g.setGregorianChange(date);
		g.add(2, amount);
		Date d = g.getTime();
		return d;
	}

	public static int getMonthsDay(int year, int month) {
		int day;
		switch (month) {
		case 2:
			if (year % 100 != 0) {
				day = (year % 400 == 0) ? 28 : 29;
			}
			day = (year % 4 == 0) ? 28 : 29;

			break;
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
		default:
			day = 30;
		}

		return day;
	}

	public static String getCurrentDateTime() {
		String currentTime = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar currentDate = Calendar.getInstance();
			currentTime = format.format(currentDate.getTime());
		} catch (Exception e) {
			return null;
		}
		return currentTime;
	}
	
	public static String getCurrentHourMunite() {
		String currentTime = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			Calendar currentDate = Calendar.getInstance();
			currentTime = format.format(currentDate.getTime());
		} catch (Exception e) {
			return null;
		}
		return currentTime;
	}

	public static String getCurrentDateTimeString() {
		String currentTime = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar currentDate = Calendar.getInstance();
			currentTime = format.format(currentDate.getTime());
		} catch (Exception e) {
			return null;
		}
		return currentTime;
	}

	public static String getCurrentShortDateTimeString() {
		String currentTime = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
			Calendar currentDate = Calendar.getInstance();
			currentTime = format.format(currentDate.getTime());
		} catch (Exception e) {
			return null;
		}
		return currentTime;
	}

	public static String getCurrentLongDateTimeString() {
		String currentTime = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
			Calendar currentDate = Calendar.getInstance();
			currentTime = format.format(currentDate.getTime());
		} catch (Exception e) {
			return null;
		}
		return currentTime;
	}

	public static String getCurrentDateString() {
		String currentTime = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar currentDate = Calendar.getInstance();
			currentTime = format.format(currentDate.getTime());
		} catch (Exception e) {
			return null;
		}

		return currentTime;
	}

	public static String getBeforeLongDateTimeString() {
		String beforeTime = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -3);
			beforeTime = format.format(calendar.getTime());
		} catch (Exception e) {
			return null;
		}
		return beforeTime;
	}

	public static String getMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(5, calendar.getActualMinimum(5));

		return dateFormat.format(calendar.getTime());
	}

	public static String getMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(5, calendar.getActualMaximum(5));
		return dateFormat.format(calendar.getTime());
	}

	public static int getTimeDifference(String firstDate, String endDate) {
		int d = 0;
		firstDate = firstDate.replaceAll("-", "/");
		endDate = endDate.replaceAll("-", "/");

		d = Integer.parseInt(String.valueOf(Math.abs(Date.parse(firstDate) - Date.parse(endDate)) / 1000L / 60L));

		return d;
	}

	// 时间加分钟
	public static String MinuteAdd(int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		System.out.println(calendar.get(Calendar.DAY_OF_MONTH));// 今天的日期
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minute);// 让分钟加xxx
		return DateUtil.formatDateTime(calendar.getTime());
	}

	public static Date getFirstDate(Date date) {
		if (null == date) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static Date getLatestDate(Date date) {
		if (null == date) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	public static Date getAfterDate(Date date, int num){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, num);
		return c.getTime();
	}

	public static void main(String[] args) {
		System.out.println("---------------"+formatHHmm(StringToDatetime(MinuteAdd(-30))));
		System.out.println(getCurrentYear());
		System.out.println("-----" + getCurrentLongDateTimeString());
		System.out.println("-----" + getBeforeLongDateTimeString());
	}
}
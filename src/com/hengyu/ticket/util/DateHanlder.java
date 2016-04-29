package com.hengyu.ticket.util;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 日期处理类
 * @author LGF
 *
 */
public class DateHanlder {
	
	//常量字符串
	public static String DATE_TIME_NUMBER = "yyMMddHHmmssSSS";
	public static String DATE_TIME_MIN_NUMBER = "yyyyMMddHHmmssSSS";
	public static String DATE_TIME = "yyyy-MM-dd HH:mm";
	public static String DATE = "yyyy-MM-dd";
	public static String TIME = "HH:mm";
	
	public static Long ONE_DAY = 1000*60*60*24L;

	
	static Map<Integer,String>  WEEKSTR_MAP = new HashMap<Integer,String>();
	static Map<Integer,Integer>  weekint = new HashMap<Integer,Integer>();
	static int[] WEEKS = {64,32,16,8,4,2,1};
	static String[] WEEKS_STR = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
	static{
		// 星期字符串
		WEEKSTR_MAP.put(64,"日");
		WEEKSTR_MAP.put(32,"六");
		WEEKSTR_MAP.put(16,"五");
		WEEKSTR_MAP.put(8,"四");
		WEEKSTR_MAP.put(4,"三");
		WEEKSTR_MAP.put(2,"二");
		WEEKSTR_MAP.put(1,"一");
		
		weekint.put(64,1);
		weekint.put(32,7);
		weekint.put(16,6);
		weekint.put(8,5);
		weekint.put(4,4);
		weekint.put(2,3);
		weekint.put(1,2);
	}
	
	/**
	 * 获取当前日期的字符串
	 * @return
	 */
	public static String getCurrentDateTime(){
		return new SimpleDateFormat(DATE_TIME).format(new Date());
	}
	
	/**
	 * 获取当前日期的字符串 1501021214222
	 * @return
	 */
	public static String getCurrentDateTimeNumber(){
		return new SimpleDateFormat(DATE_TIME_NUMBER).format(new Date());
	}
	
	/**
	 * 获取当前日期的字符串 201501021214222
	 * @return
	 */
	public static String getCurrentDateTimeMinuteNumber(){
		return new SimpleDateFormat(DATE_TIME_MIN_NUMBER).format(new Date());
	}
	
	/**
	 * 获取当前日期的字符串
	 * @return
	 */
	public static String getCurrentDate(){
		return new SimpleDateFormat(DATE).format(new Date());
	}
	
	/**
	 * 获取当前日期的字符串
	 * @return
	 */
	public static String getCurrentTime(){
		return new SimpleDateFormat(TIME).format(new Date());
	}
	
	/**
	 * 获取日期列表
	 * @param szie
	 * @return
	 */
	public static Set<String> getDates(int szie){
		Set<String> dates = new LinkedHashSet<String>(szie);
		Date date = new Date();
		SimpleDateFormat dateFromat = new SimpleDateFormat(DATE);
		dates.add(dateFromat.format(date));
		for (int i = 1; i < szie; i++) {
			dates.add(dateFromat.format(new Date(date.getTime()+i*ONE_DAY)));
		}
		return dates;
	}
	
	/**
	 * 按开始日期，结束日期，获取日期集合
	 * @param begin
	 * @param end
	 * @return
	 * @throws ParseException
	 */
	public static Set<String> getDates(String begin,String end) throws ParseException{
		SimpleDateFormat dateFromat = new SimpleDateFormat(DATE);
		long b = dateFromat.parse(begin).getTime();
		int ds = 0;
		if(end!=null&&end.isEmpty()==false){
			long e = dateFromat.parse(end).getTime();
			ds = (int) ((e-b)/(1000*60*60*24));
		}
		Set<String> dates = new LinkedHashSet<String>(ds);
		dates.add(dateFromat.format(b));
		for (int i = 1; i <= ds; i++) {
			dates.add(dateFromat.format(new Date(b+i*ONE_DAY)));
			if(i>=60){
				break;
			}
		}
		return dates;
	}
	
	
	/**
	 * 获取16进制的星期
	 * @param sum 星期之和
	 * @return
	 */
	public static List<Integer> getweeks(int sum){
		int temp = sum;
		List<Integer> list = new ArrayList<Integer>();
		for (Integer i : WEEKS) {
			if(i>temp||temp<=0){
				continue;
			}
			list.add(0,i);
			temp-=i;
		}
		return list;
		
	}
	
	/**
	 * 获取星期字符串
	 * @param wks 十六进制星期数组
	 * @return
	 */
	public static List<String> getweekString(int[] wks){
		List<String> strs = new ArrayList<String>();
		for (int i : wks) {
			strs.add(WEEKSTR_MAP.get(i));
		}
		return strs;
	}
	
	/**
	 * 获取星期字符串
	 * @param sum 星期之和
	 * @return
	 */
	public static List<String> getweekString(int sum){
		List<Integer> ints = getweeks(sum);
		List<String> strs = new ArrayList<String>();
		for (int i : ints) {
			strs.add(WEEKSTR_MAP.get(i));
		}
		return strs;
	}
	
	/**
	 * 获取星期int
	 * @param sum 星期之和
	 * @return
	 */
	public static List<Integer> getweekInt(int sum){
		List<Integer> ints = getweeks(sum);
		List<Integer> wks = new ArrayList<Integer>();
		for (int i : ints) {
			wks.add(weekint.get(i));
		}
		return wks;
	}

    public  static String getWeekDayStr(Object date) throws Exception {
        Date d  = null;
        if(date instanceof Date ){
            d = (Date) date;
        }else{
            d = getDateFromat().parse(date.toString());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int w = calendar.get(Calendar.DAY_OF_WEEK);
        return WEEKS_STR[w-1];

    }
	
	/**
	 * 检查日期是否包含星期
	 * @param date
	 * @param weekday
	 * @return
	 * @throws Exception
	 */
	public static boolean checkExistsWeek(String date,Integer weekday) throws Exception{
		SimpleDateFormat dateFromat = new SimpleDateFormat(DATE);
		Calendar instance = Calendar.getInstance();
		instance.setTime(dateFromat.parse(date));
		int week = instance.get(Calendar.DAY_OF_WEEK);
		List<Integer> weeks = getweeks(weekday);
		for (Integer wk : weeks) {
			Integer temp = weekint.get(wk);
			if(temp==null){
				continue;
			}
			if(temp==week){
				return true;
			}
			
		}
		return false;
	}
	
	/**
	 * 检查时间范围
	 * @param time
	 * @param begintime
	 * @param endtime
	 * @return
	 */
	public static boolean checkeBetweenTime(String time,String begintime,String endtime){
		try {
			SimpleDateFormat timeFromat = new SimpleDateFormat(TIME);
			long t = timeFromat.parse(time).getTime();
			long b = timeFromat.parse(begintime).getTime();
			long e = timeFromat.parse(endtime).getTime();
			if(t>=b&&t<=e){
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * 按日期获取星期
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getWeekDayStrByDate(Date date) throws ParseException{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return WEEKS_STR[c.get(Calendar.DAY_OF_WEEK)-1];
	}
	
	
	/**
	 * yyyy-MM-dd HH:SS
	 * @return
	 */
	public static SimpleDateFormat getDateTimeFromat(){
		return new SimpleDateFormat(DATE_TIME);
	}
	
	/**
	 * yyyy-MM-dd 
	 * @return
	 */
	public static SimpleDateFormat getDateFromat(){
		return new SimpleDateFormat(DATE);
	}
	
	/**
	 * HH:SS
	 * @return
	 */
	public static SimpleDateFormat getTimeFromat(){
		return new SimpleDateFormat(TIME);
	}
	
}

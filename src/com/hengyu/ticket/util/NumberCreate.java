package com.hengyu.ticket.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * 提供id生成方法
 * @author LGF
 *
 */
public class NumberCreate {
	
	private static Random random = new Random();
	
	/**
	 * 创编号
	 * @param id
	 * @param num
	 * @return
	 */
	public static String createNumber(Object id,int num){
		if(id==null){
			throw new RuntimeException("num is not null");
		}else{
			if(num==0){
				return id.toString();
			}
			StringBuffer sb = new StringBuffer();
			int len = id.toString().length();
			for (int i = len; i < num; i++) {
				sb.append("0");
			}
			return sb.append(id).toString();
		}
	}
	
	/**
	 * 创建班次号
	 * @param pref
	 * @param size
	 * @return
	 */
	public static List<String> createShiftCodes(String pref,int size,int num){
		List<String> numbers = new ArrayList<String>(size);
		for (int i = 0; i < size; i++) {
			numbers.add((pref+(createNumber(num++, 3))).toUpperCase());
		}
		return numbers;
	}
	
	/**
	 * 生成取票码
	 * @param mobile
	 * @return
	 */
	public static String createTicketCode(String mobile){
		int number = 100000+random.nextInt(899990);
		String s = String.valueOf(number)+mobile.substring(5);
		if(s.length()>12){
			s = s.substring(0, 12);
		}
		return s;
	}
	
	/**
	 * 获取反向的线路id
	 * @param id
	 * @return
	 */
	public static String getReverseLineId(String lineid){
		if(lineid==null||lineid.isEmpty()){
			return lineid;
		}
		try {
			return new StringBuffer(lineid.substring(0, 2)).reverse().append(lineid.substring(2)).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
}

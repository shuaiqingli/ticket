package com.hengyu.ticket.util;

import java.util.HashMap;
import java.util.Map;

public class APIStatus {
	
	public static final Map<Integer,String> codes = new HashMap<Integer,String>();
	
	public static String SUCCESS_STATUS = "00";
	public static String SIGN_ERROR_STATUS = "01";
	public static String AUTH_ERROR_STATUS = "02";
	public static String PARAMETER_ERROR_STATUS = "03";
	public static String SYSTEM_ERROR_STATUS = "04";
	public static String MOBILE_EXIST_STATUS = "05";
	public static String USER_NOT_EXIST_STATUS = "06";
	public static String MOBLIE_OR_PASSWORD_ERROR_STATUS = "07";
	public static String VERIFICATION_CODE_ERROR_STATUS = "08";
	public static String OLD_PASSWORD_ERROR_STATUS = "09";
	
	public static String SUCCESS_INFO = "成功";
	public static String SIGN_ERROR_INFO = "签名错误";
	public static String AUTH_ERROR_INFO = "认证错误";
	public static String PARAMETER_ERROR_INFO = "参数错误";
	public static String SYSTEM_ERROR_INFO = "系统错误";
	public static String MOBILE_EXIST_INFO = "手机号已被注册";
	public static String USER_NOT_EXIST_INFO = "用户不存在";
	public static String MOBLIE_OR_PASSWORD_ERROR_INFO = "用户名或密码错误";
	public static String VERIFICATION_CODE_ERROR_INFO = "验证码错误";
	public static String OLD_PASSWORD_ERROR_INFO = "旧密码错误";
	
	static{
		codes.put(200,SUCCESS_INFO);
		codes.put(5000,"服务器异常，请稍后再试...");
		codes.put(5001,SIGN_ERROR_INFO);
		codes.put(5002,MOBILE_EXIST_INFO);
		codes.put(5003,USER_NOT_EXIST_INFO);
		codes.put(5004,"验证码错误");
		codes.put(5005,"验证码过期");
		codes.put(5006,"检查参数是否为空？");
		codes.put(5007,"用户名或密码错误");
		codes.put(5008,"会话过期，请重新登录");
		codes.put(5009,"手机号码和token不能为空！");
		codes.put(5010,"该记录已经存在！");
		codes.put(5011,"订单超时，下单失败！");
		codes.put(5012,"订单异常！");
		codes.put(5013,"订单已取消！");
		codes.put(5014,"旧密码错误！");
		
		//后台管理状态
		codes.put(8000,"会话过期，请重新登录！");
	}
	
}

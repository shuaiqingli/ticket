package com.hengyu.ticket.common;

import com.hengyu.ticket.util.SecurityHanlder;

/**
 * 常量
 * @author LGF
 *
 */
public class Const {
	public static final String LOGIN_USER="user";//登录用户
	public static final String LOGIN_CUSTOMER="customer";//登录用户
	public static final String ROLES="roles";//角色
	public static final String FUN_MODEL="funModel";//菜单列表
	public static final Integer SUCCESS_CODE = 200;//操作成功
	public static final Integer ERROR_CODE = -1;//操作失败
	public static final Integer EXIST_CODE = -100;
	public static final Integer NUMBER_SIX = 6;//六位数字
	public static final String MVC_PRODUCES = "text/plain;charset=UTF-8";
	public static boolean DEBUG = true;
	public static final Integer MIN_INT = 0;
	
	public static final String ISAPP = "isAPP";
	
	//发车状态
	public static final Integer SHIFSTATE_DEFAULT_CODE = 0;
	public static final String SHIFSTATE_DEFAULT_Name = "未发车";
	
	//基本资源类型
	public static final String BASE_RESOURCE_CARMODEL = "CarModel";
	public static final String BASE_RESOURCE_TRANSCOMPANY = "TransCompany";
	
	//页数大小
	public static final Integer PAGE_SIZE = 10;
	
	
	public static final String QRCODE_PATH = "/wx/qrcode/";
	
	
	//主键配置表 主键常量
	public static final String SALEORDER_PREFIX_KEY= "SaleOrderPreKey";
	//初始化值
	public static final Integer SALEORDER_PREFIX_KEY_INITVAL = 160;
	
	//用户cookie
	
	public static String COOKIE_USERID;
	public static String COOKIE_PASSWORD;
	public static String COOKIE_CUSTOMER_MOBILE = "COOKIE_CUSTOMER_MOBILE";
	public static String COOKIE_CUSTOMER_PASSWORD = "COOKIE_CUSTOMER_PASSWORD";
	public static final int COOKIE_USER_AGE = 1000*60*60*24*30;
	
	static{
		try {
			COOKIE_USERID = SecurityHanlder.md5("USERID");
			COOKIE_PASSWORD = SecurityHanlder.md5("PASSWORD");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//消息提示
	public static final String HTTP_MSG_NOT_FOUND = "请求资源没有找到！";
	
	
	
	
}

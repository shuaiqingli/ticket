package com.hengyu.ticket.util;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hengyu.ticket.entity.SmsHistory;
import com.hengyu.ticket.service.SmsHistoryService;

public class SmsUtil {
	private static String URL = "http://smsapi.c123.cn/OpenPlatform/OpenApi";
	private static String AC = "1001@500944260002";
	private static String AUTH_KEY = "C3EC5F14AD5517C32021501FE035A26D";
	private static int CGID = 5879;
	private static int CSID = 50094426;
	private static String SEND_ONCE = "sendOnce";
	
	public static String REGISTER_MSG = "验证码：{0}，有效时间30分钟，请不要向任何人透露您的验证码，欢迎加入";
	public static String UPDATE_PASSWOED_MSG = "验证码：{0}，有效时间30分钟，请不要向任何人透露您的验证码";
	
	private static Map<String,String> MSG_TYPE = new HashMap<String,String>();
	static{
		MSG_TYPE.put("1", REGISTER_MSG);
		MSG_TYPE.put("2", UPDATE_PASSWOED_MSG);
	}

	public static boolean sendVerifyCode(String mobile, SmsHistoryService smsHistoryService,String type) {
		String verifyCode = SmsUtil.generateVerifyCode();
		String msg = MSG_TYPE.get(type);
		if(msg!=null){
			msg = MessageFormat.format(msg, verifyCode);
		}else{
			msg = "您注册捷乘巴士的短信动态验证码："+verifyCode+"，有效时间30分钟";
		}
		boolean flag = sendSms(mobile, msg);
		if (flag) {
			try {
				SmsHistory smsHistory = new SmsHistory();
				smsHistory.setContent(msg);
				smsHistory.setMobile(mobile);
				smsHistory.setVerifyCode(verifyCode);
				smsHistory.setMakedate(DateUtil.getCurrentDateTime());
				smsHistoryService.save(smsHistory);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 通用单条短信发送
	 * 
	 * @param mobile
	 * @param content
	 * @return
	 */
	public static boolean sendSms(String mobile, String content) {
		if (StringUtils.isBlank(mobile) || StringUtils.isBlank(content)) {
			return false;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("action", SEND_ONCE);
		params.put("ac", AC);
		params.put("authkey", AUTH_KEY);
		params.put("cgid", String.valueOf(CGID));
		params.put("csid", String.valueOf(CSID));
		params.put("m", mobile);
		params.put("c", content);
		try {
			String responseStr = HttpClientUtil.get(URL, params);
			Log.info(SmsUtil.class,"短信发送,mobile:" + mobile + ",content:" + content + ",response:" + responseStr);
			if (StringUtils.isNotBlank(responseStr)) {
				if (responseStr.contains("<xml name=\"sendOnce\" result=\"1\">")) {
					return true;
				}
			}
		} catch (Exception e) {
			Log.error(e.getMessage(), e);
		}
		return false;
	}

	public static String generateVerifyCode() {
		String optionStr = "1234567890";
		int verifyCodeLength = 6;
		StringBuffer verifyCode = new StringBuffer();
		for (int i = 0; i < verifyCodeLength; i++) {
			int index = (int) (Math.random() * (optionStr.length()));
			verifyCode.append(optionStr.charAt(index));
		}
		return verifyCode.toString();
	}
}

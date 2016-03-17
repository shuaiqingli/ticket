package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2015-09-26 
  */
public class SmsHistory{

	//------------------字段-----------------
	
	private Integer id;
	//手机号码
	private String mobile;
	//验证码
	private String verifyCode;
	//短信内容
	private String content;
	//发送日期
	private String makeDate;

	//----------------构造方法----------------
	
	public SmsHistory(){

	}

	//-------------- get/set方法 --------------
	
	
	/**
	 *获取mobile (手机号码)
	 * @return String  
	 */
	public String getMobile(){
		return mobile;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 *设置mobile (手机号码)
	 * @param mobile 
 	 */
	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	/**
	 *获取verifyCode (验证码)
	 * @return String  
	 */
	public String getVerifyCode(){
		return verifyCode;
	}

	/**
	 *设置verifyCode (验证码)
	 * @param verifyCode 
 	 */
	public void setVerifyCode(String verifyCode){
		this.verifyCode = verifyCode;
	}

	/**
	 *获取content (短信内容)
	 * @return String  
	 */
	public String getContent(){
		return content;
	}

	/**
	 *设置content (短信内容)
	 * @param content 
 	 */
	public void setContent(String content){
		this.content = content;
	}

	/**
	 *获取makeDate (发送日期)
	 * @return String  
	 */
	public String getMakeDate(){
		return makeDate;
	}

	/**
	 *设置makeDate (发送日期)
	 * @param makeDate 
 	 */
	public void setMakedate(String makeDate){
		this.makeDate = makeDate;
	}

}
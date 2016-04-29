package com.hengyu.ticket.entity;

import java.math.BigDecimal;


 /**
  * @author 李冠锋 2015-09-26 
  */
public class Customer{

	 //辅助字段
	 private String startDate;
	 private String endDate;
	//------------------字段-----------------
	
	//客户编号
	private String cid;
	//客户手机号码
	private String mobile;
	//客户名称
	private String cname;
	//客户邮箱
	private String email;
	//客户密码
	private String password;
	//Token
	private String token;
	//余额
	private BigDecimal money = new BigDecimal("0");
	//注册日期
	private String makeDate;
	//微信OPenId
	private String openid;
	//头像
	private String headphoto;
	//里程
	private Integer mileage;
	//积分
	private Integer integral;

	 private String lat;
	 private String lng;
	 private String regaddr;

	 //级别 0.普通 1.VIP 2.站务
	 private Integer rank;
	 private String UserID;
	 private String UserName;

	//优惠劵数量
	private Integer coupons;
	
	//----------------构造方法----------------
	
	public Customer(){

	}

	//-------------- get/set方法 --------------
	

	/**
	 *获取mobile (客户手机号码)
	 * @return String  
	 */
	public String getMobile(){
		return mobile;
	}

	/**
	 *设置mobile (客户手机号码)
	 * @param mobile 
 	 */
	public void setMobile(String mobile){
		this.mobile = mobile;
	}


	/**
	 *获取email (客户邮箱)
	 * @return String  
	 */
	public String getEmail(){
		return email;
	}

	/**
	 *设置email (客户邮箱)
	 * @param email 
 	 */
	public void setEmail(String email){
		this.email = email;
	}

	/**
	 *获取password (客户密码)
	 * @return String  
	 */
	public String getPassword(){
		return password;
	}

	/**
	 *设置password (客户密码)
	 * @param password 
 	 */
	public void setPassword(String password){
		this.password = password;
	}

	/**
	 *获取token (Token)
	 * @return String  
	 */
	public String getToken(){
		return token;
	}

	/**
	 *设置token (Token)
	 * @param token 
 	 */
	public void setToken(String token){
		this.token = token;
	}

	/**
	 *获取money (余额)
	 * @return String  
	 */
	public BigDecimal getMoney(){
		return money;
	}

	/**
	 *设置money (余额)
	 * @param money 
 	 */
	public void setMoney(BigDecimal money){
		this.money = money;
	}

	/**
	 *获取makeDate (注册日期)
	 * @return String  
	 */
	public String getMakeDate(){
		return makeDate;
	}

	/**
	 *设置makeDate (注册日期)
	 * @param makeDate 
 	 */
	public void setMakedate(String makeDate){
		this.makeDate = makeDate;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getHeadphoto() {
		return headphoto;
	}

	public void setHeadphoto(String headphoto) {
		this.headphoto = headphoto;
	}

	public Integer getMileage() {
		return mileage;
	}

	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	public Integer getCoupons() {
		return coupons;
	}

	public void setCoupons(Integer coupons) {
		this.coupons = coupons;
	}

	 public Integer getIntegral() {
		 return integral;
	 }

	 public void setIntegral(Integer integral) {
		 this.integral = integral;
	 }

	 public Integer getRank() {
		 return rank;
	 }

	 public void setRank(Integer rank) {
		 this.rank = rank;
	 }

	 public String getUserID() {
		 return UserID;
	 }

	 public void setUserID(String userID) {
		 UserID = userID;
	 }

	 public String getUserName() {
		 return UserName;
	 }

	 public void setUserName(String userName) {
		 UserName = userName;
	 }

	 public void setMakeDate(String makeDate) {
		 this.makeDate = makeDate;
	 }

	 public String getLat() {
		 return lat;
	 }

	 public void setLat(String lat) {
		 this.lat = lat;
	 }

	 public String getLng() {
		 return lng;
	 }

	 public void setLng(String lng) {
		 this.lng = lng;
	 }

	 public String getRegaddr() {
		 return regaddr;
	 }

	 public void setRegaddr(String regaddr) {
		 this.regaddr = regaddr;
	 }

	 public String getStartDate() {
		 return startDate;
	 }

	 public void setStartDate(String startDate) {
		 this.startDate = startDate;
	 }

	 public String getEndDate() {
		 return endDate;
	 }

	 public void setEndDate(String endDate) {
		 this.endDate = endDate;
	 }
 }

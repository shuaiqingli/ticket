package com.hengyu.ticket.entity;

import java.util.List;

 /**
  * @author 李冠锋 2015-09-26 
  */
public class Admin{

	//------------------字段-----------------
	
	//用户编号
	private String userID;
	//上级编号
	private String parentid;
	//名字
	private String realName;
	//手机号码
	private String mobile;
	//Email
	private String email;
	//密码
	private String password;
	//Token
	private String token;
	//是否删除 1 或 0
	private Integer isDel;
	//是否超级管理员
	private Integer isAdmin = 0;
	//角色编号id
	private Integer roleID;
	//注册日期
	private String makeDate;
//	//所属车站编号
//	private String sTID;
//	//所属车站名称
//	private String sTName;
	//角色
	private Role role;
	//城市编号
	private String cityId;
	//城市名称
	private String cityName;
	//菜单列表
	private List<FuncMode> menus;
	//是否有审核权限
	private Integer isapprove;
	
	//公司id、名称
	private Integer tcid;
	private String companyname;
	
	private Integer ischange;

	private String isautobindline;
	
	//站点
	private List<RelationStation> stations;

	//----------------构造方法----------------
	
	public Admin(){

	}

	//-------------- get/set方法 --------------
	
	/**
	 *获取userID (用户编号)
	 * @return String  
	 */
	public String getUserID(){
		return userID;
	}

	/**
	 *设置userID (用户编号)
	 * @param userID 
 	 */
	public void setUserID(String userID){
		this.userID = userID;
	}

	/**
	 *获取realName (名字)
	 * @return String  
	 */
	public String getRealName(){
		return realName;
	}

	/**
	 *设置realName (名字)
	 * @param realName 
 	 */
	public void setRealName(String realName){
		this.realName = realName;
	}

	/**
	 *获取mobile (手机号码)
	 * @return String  
	 */
	public String getMobile(){
		return mobile;
	}

	/**
	 *设置mobile (手机号码)
	 * @param mobile 
 	 */
	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	/**
	 *获取email (Email)
	 * @return String  
	 */
	public String getEmail(){
		return email;
	}

	/**
	 *设置email (Email)
	 * @param email 
 	 */
	public void setEmail(String email){
		this.email = email;
	}

	/**
	 *获取password (密码)
	 * @return String  
	 */
	public String getPassword(){
		return password;
	}

	/**
	 *设置password (密码)
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
	 *获取isDel (是否删除 1 或 0)
	 * @return Integer  
	 */
	public Integer getIsDel(){
		return isDel;
	}

	/**
	 *设置isDel (是否删除 1 或 0)
	 * @param isDel 
 	 */
	public void setIsDel(Integer isDel){
		this.isDel = isDel;
	}

	/**
	 *获取isAdmin (是否超级管理员)
	 * @return Integer  
	 */
	public Integer getIsAdmin(){
		return isAdmin;
	}

	/**
	 *设置isAdmin (是否超级管理员)
	 * @param isAdmin 
 	 */
	public void setIsAdmin(Integer isAdmin){
		this.isAdmin = isAdmin;
	}

	/**
	 *获取roleID (角色编号id)
	 * @return Integer  
	 */
	public Integer getRoleID(){
		return roleID;
	}

	/**
	 *设置roleID (角色编号id)
	 * @param roleID 
 	 */
	public void setRoleID(Integer roleID){
		this.roleID = roleID;
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

//	/**
//	 *获取sTID (所属车站编号)
//	 * @return String  
//	 */
//	public String getSTID(){
//		return sTID;
//	}
//
//	/**
//	 *设置sTID (所属车站编号)
//	 * @param sTID 
// 	 */
//	public void setSTID(String sTID){
//		this.sTID = sTID;
//	}
//
//	/**
//	 *获取sTName (所属车站名称)
//	 * @return String  
//	 */
//	public String getSTName(){
//		return sTName;
//	}
//
//	/**
//	 *设置sTName (所属车站名称)
//	 * @param sTName 
// 	 */
//	public void setSTName(String sTName){
//		this.sTName = sTName;
//	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<FuncMode> getMenus() {
		return menus;
	}

	public void setMenus(List<FuncMode> menus) {
		this.menus = menus;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityname() {
		return cityName;
	}

	public void setCityname(String cityName) {
		this.cityName = cityName;
	}

	public List<RelationStation> getStations() {
		return stations;
	}

	public void setStations(List<RelationStation> stations) {
		this.stations = stations;
	}

	public Integer getIsapprove() {
		return isapprove;
	}

	public void setIsapprove(Integer isapprove) {
		this.isapprove = isapprove;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public Integer getTcid() {
		return tcid;
	}

	public void setTcid(Integer tcid) {
		this.tcid = tcid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public Integer getIschange() {
		return ischange;
	}

	public void setIschange(Integer ischange) {
		this.ischange = ischange;
	}

	 public String getIsautobindline() {
		 return isautobindline;
	 }

	 public void setIsautobindline(String isautobindline) {
		 this.isautobindline = isautobindline;
	 }
 }
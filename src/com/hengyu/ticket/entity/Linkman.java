package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2015-10-10 
  */
public class Linkman{

	//------------------字段-----------------
	
	//编号
	private String id;
	//客户编号
	private String cid;
	//姓名
	private String lname;
	//手机号码
	private String lmobile;
	//身份证号码
	private String idcode;
	//添加日期
	private String makedate;
	
	private Long synchrono;

	//----------------构造方法----------------
	
	public Linkman(){

	}

	//-------------- get/set方法 --------------
	
	/**
	 *获取id (编号)
	 * @return String  
	 */
	public String getId(){
		return id;
	}

	/**
	 *设置id (编号)
	 * @param id 
 	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 *获取cid (客户编号)
	 * @return String  
	 */
	public String getCid(){
		return cid;
	}

	/**
	 *设置cid (客户编号)
	 * @param cid 
 	 */
	public void setCid(String cid){
		this.cid = cid;
	}

	/**
	 *获取lname (姓名)
	 * @return String  
	 */
	public String getLname(){
		return lname;
	}

	/**
	 *设置lname (姓名)
	 * @param lname 
 	 */
	public void setLname(String lname){
		this.lname = lname;
	}

	/**
	 *获取lmobile (手机号码)
	 * @return String  
	 */
	public String getLmobile(){
		return lmobile;
	}

	/**
	 *设置lmobile (手机号码)
	 * @param lmobile 
 	 */
	public void setLmobile(String lmobile){
		this.lmobile = lmobile;
	}

	/**
	 *获取idcode (身份证号码)
	 * @return String  
	 */
	public String getIdcode(){
		return idcode;
	}

	/**
	 *设置idcode (身份证号码)
	 * @param idcode 
 	 */
	public void setIdcode(String idcode){
		this.idcode = idcode;
	}

	/**
	 *获取makedate (添加日期)
	 * @return String  
	 */
	public String getMakedate(){
		return makedate;
	}

	/**
	 *设置makedate (添加日期)
	 * @param makedate 
 	 */
	public void setMakedate(String makedate){
		this.makedate = makedate;
	}

	public Long getSynchrono() {
		return synchrono;
	}

	public void setSynchrono(Long synchrono) {
		this.synchrono = synchrono;
	}
}
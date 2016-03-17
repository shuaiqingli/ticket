package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2015-11-09 
  */
public class RelationStation{

	//------------------字段-----------------
	
	private Integer id;
	private String userid;
	private String stid;
	private String stname;

	//----------------构造方法----------------
	
	public RelationStation(){

	}

	//-------------- get/set方法 --------------
	
	/**
	 *获取id 
	 * @return Integer  
	 */
	public Integer getId(){
		return id;
	}

	/**
	 *设置id 
	 * @param id 
 	 */
	public void setId(Integer id){
		this.id = id;
	}

	/**
	 *获取userid 
	 * @return String  
	 */
	public String getUserid(){
		return userid;
	}

	/**
	 *设置userid 
	 * @param userid 
 	 */
	public void setUserid(String userid){
		this.userid = userid;
	}

	/**
	 *获取stid 
	 * @return String  
	 */
	public String getStid(){
		return stid;
	}

	/**
	 *设置stid 
	 * @param stid 
 	 */
	public void setStid(String stid){
		this.stid = stid;
	}

	/**
	 *获取stname 
	 * @return String  
	 */
	public String getStname(){
		return stname;
	}

	/**
	 *设置stname 
	 * @param stname 
 	 */
	public void setStname(String stname){
		this.stname = stname;
	}

}
package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2015-12-30 
  */
public class AdminLine{

	//------------------字段-----------------
	
	private Integer id;
	private String userid;
	private Integer lmid;

	//----------------构造方法----------------
	
	public AdminLine(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getUserid(){
		return userid;
	}
	public void setUserid(String userid){
		this.userid = userid;
	}
	public Integer getLmid(){
		return lmid;
	}
	public void setLmid(Integer lmid){
		this.lmid = lmid;
	}
}
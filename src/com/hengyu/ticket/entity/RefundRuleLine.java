package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2016-01-14 
  */
public class RefundRuleLine{

	//------------------字段-----------------
	
	private Integer id;
	private Integer rrid;
	private Integer lmid;

	//----------------构造方法----------------
	
	public RefundRuleLine(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getRrid(){
		return rrid;
	}
	public void setRrid(Integer rrid){
		this.rrid = rrid;
	}
	public Integer getLmid(){
		return lmid;
	}
	public void setLmid(Integer lmid){
		this.lmid = lmid;
	}
}
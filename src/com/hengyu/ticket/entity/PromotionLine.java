package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2015-11-14 
  */
public class PromotionLine{

	//------------------字段-----------------
	
	private Integer id;
	private Integer pid;
	private Integer lmid;
	private String lineid;

	//----------------构造方法----------------
	
	public PromotionLine(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getPid(){
		return pid;
	}
	public void setPid(Integer pid){
		this.pid = pid;
	}
	public Integer getLmid(){
		return lmid;
	}
	public void setLmid(Integer lmid){
		this.lmid = lmid;
	}

	public String getLineid() {
		return lineid;
	}

	public void setLineid(String lineid) {
		this.lineid = lineid;
	}
}
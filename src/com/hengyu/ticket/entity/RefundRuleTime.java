package com.hengyu.ticket.entity;


 /**
  * @author 李冠锋 2016-01-14 
  */
public class RefundRuleTime{

	//------------------字段-----------------
	
	private Integer id;
	private Integer rrid;
	private Double advancetime;
	private Integer deduction;
	
	//是否删除
	private Integer isdel = 0;

	//----------------构造方法----------------
	
	public RefundRuleTime(){

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
	public Double getAdvancetime(){
		return advancetime;
	}
	public void setAdvancetime(Double advancetime){
		this.advancetime = advancetime;
	}
	public Integer getDeduction(){
		return deduction;
	}
	public void setDeduction(Integer deduction){
		this.deduction = deduction;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
}
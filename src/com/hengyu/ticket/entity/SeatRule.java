package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2015-11-14 
  */
public class SeatRule{

	//------------------字段-----------------
	
	private Integer id;
	private Integer lmid;
	private Integer firstseat;
	private Integer seatquantity;

	//----------------构造方法----------------
	
	public SeatRule(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getLmid(){
		return lmid;
	}
	public void setLmid(Integer lmid){
		this.lmid = lmid;
	}
	public Integer getFirstseat(){
		return firstseat;
	}
	public void setFirstseat(Integer firstseat){
		this.firstseat = firstseat;
	}
	public Integer getSeatquantity(){
		return seatquantity;
	}
	public void setSeatquantity(Integer seatquantity){
		this.seatquantity = seatquantity;
	}
}
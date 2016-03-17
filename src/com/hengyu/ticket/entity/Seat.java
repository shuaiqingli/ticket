package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2015-11-14 
  */
public class Seat{

	//------------------字段-----------------
	
	private Integer id;
	private Integer seatno;
	private Integer lmid;
	private String shiftcode;
	private String ridedate;
	private Integer issale;
	
	private Seat seattow;

	//----------------构造方法----------------
	
	public Seat(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getSeatno(){
		return seatno;
	}
	public void setSeatno(Integer seatno){
		this.seatno = seatno;
	}
	public Integer getLmid(){
		return lmid;
	}
	public void setLmid(Integer lmid){
		this.lmid = lmid;
	}
	public String getShiftcode(){
		return shiftcode;
	}
	public void setShiftcode(String shiftcode){
		this.shiftcode = shiftcode;
	}
	public String getRidedate(){
		return ridedate;
	}
	public void setRidedate(String ridedate){
		this.ridedate = ridedate;
	}
	public Integer getIssale(){
		return issale;
	}
	public void setIssale(Integer issale){
		this.issale = issale;
	}

	public Seat getSeattow() {
		return seattow;
	}

	public void setSeattow(Seat seattow) {
		this.seattow = seattow;
	}
	
}
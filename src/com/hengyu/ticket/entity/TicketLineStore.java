package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2015-12-31 
  */
public class TicketLineStore{

	//------------------字段-----------------
	
	private Integer id;
	//线路编号
	private Integer lmid;
	private String shiftcode;
	//车票日期
	private String ticketdate;
	//剩余车票
	private Integer balancequantity;
	//总票
	private Integer allquantity;
	//生成车票日期
	private String makedate;
	//总优惠票
	private Integer couponquantity;
	//剩余优惠票
	private Integer balancecouponquantity;
	//站点id
	private String currstationid;

	//----------------构造方法----------------
	
	public TicketLineStore(){

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
	public String getShiftcode(){
		return shiftcode;
	}
	public void setShiftcode(String shiftcode){
		this.shiftcode = shiftcode;
	}
	public String getTicketdate(){
		return ticketdate;
	}
	public void setTicketdate(String ticketdate){
		this.ticketdate = ticketdate;
	}
	public Integer getBalancequantity(){
		return balancequantity;
	}
	public void setBalancequantity(Integer balancequantity){
		this.balancequantity = balancequantity;
	}
	public Integer getAllquantity(){
		return allquantity;
	}
	public void setAllquantity(Integer allquantity){
		this.allquantity = allquantity;
	}
	public String getMakedate(){
		return makedate;
	}
	public void setMakedate(String makedate){
		this.makedate = makedate;
	}
	public Integer getCouponquantity(){
		return couponquantity;
	}
	public void setCouponquantity(Integer couponquantity){
		this.couponquantity = couponquantity;
	}
	public Integer getBalancecouponquantity(){
		return balancecouponquantity;
	}
	public void setBalancecouponquantity(Integer balancecouponquantity){
		this.balancecouponquantity = balancecouponquantity;
	}

	public String getCurrstationid() {
		return currstationid;
	}

	public void setCurrstationid(String currstationid) {
		this.currstationid = currstationid;
	}
}
package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2015-12-29 
  */
public class StationLine{

	//------------------字段-----------------
	
	//编号
	private Integer id;
	//站点编号
	private String stationid;
	//线路编号
	private Integer lmid;
	//限制张数
	private Integer salequantity = 0;
	//优惠张数
	private Integer couponsalequantity = 0;
	//行程价格规则外键
	private Integer tprid;
	//卖票百分比
	private Integer ticketpercent = 0;
	//优惠票百分比
	private Integer couponticketpercent = 0;
	
	//-----------------------
	//站点名称
	private String stationname;
	//站点优惠金额百分比
	private Integer couponpercent;

	//----------------构造方法----------------
	
	public StationLine(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getStationid(){
		return stationid;
	}
	public void setStationid(String stationid){
		this.stationid = stationid;
	}
	public Integer getLmid(){
		return lmid;
	}
	public void setLmid(Integer lmid){
		this.lmid = lmid;
	}
	public Integer getSalequantity(){
		return salequantity;
	}
	public void setSalequantity(Integer salequantity){
		this.salequantity = salequantity;
	}

	public Integer getTprid() {
		return tprid;
	}

	public void setTprid(Integer tprid) {
		this.tprid = tprid;
	}

	public String getStationname() {
		return stationname;
	}

	public void setStationname(String stationname) {
		this.stationname = stationname;
	}

	public Integer getTicketpercent() {
		return ticketpercent;
	}

	public void setTicketpercent(Integer ticketpercent) {
		this.ticketpercent = ticketpercent;
	}

	public Integer getCouponsalequantity() {
		return couponsalequantity;
	}

	public void setCouponsalequantity(Integer couponsalequantity) {
		this.couponsalequantity = couponsalequantity;
	}

	public Integer getCouponticketpercent() {
		return couponticketpercent;
	}

	public void setCouponticketpercent(Integer couponticketpercent) {
		this.couponticketpercent = couponticketpercent;
	}

	public Integer getCouponpercent() {
		return couponpercent;
	}

	public void setCouponpercent(Integer couponpercent) {
		this.couponpercent = couponpercent;
	}
	
}
package com.hengyu.ticket.entity;

import java.math.BigDecimal;

/**
  * @author 李冠锋 2015-11-14 
  */
public class TripPriceSub{

	//------------------字段-----------------
	
	private Integer id;
	private Integer tprid;
	private Integer lmid;
	private String linename;
	private String ststartid;
	private String ststartname;
	private String starriveid;
	private String starrivename;
	private BigDecimal price = BigDecimal.ZERO;
	private Integer mileage = 0;
	private Integer pricesort;

	//----------------构造方法----------------
	
	public TripPriceSub(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getTprid(){
		return tprid;
	}
	public void setTprid(Integer tprid){
		this.tprid = tprid;
	}
	public Integer getLmid(){
		return lmid;
	}
	public void setLmid(Integer lmid){
		this.lmid = lmid;
	}
	public String getLinename(){
		return linename;
	}
	public void setLinename(String linename){
		this.linename = linename;
	}
	public String getStstartname(){
		return ststartname;
	}
	public void setStstartname(String ststartname){
		this.ststartname = ststartname;
	}
	public String getStarriveid(){
		return starriveid;
	}
	public void setStarriveid(String starriveid){
		this.starriveid = starriveid;
	}
	public String getStarrivename(){
		return starrivename;
	}
	public void setStarrivename(String starrivename){
		this.starrivename = starrivename;
	}
	public BigDecimal getPrice(){
		return price;
	}
	public void setPrice(BigDecimal price){
		this.price = price;
	}
	public Integer getMileage(){
		return mileage;
	}
	public void setMileage(Integer mileage){
		this.mileage = mileage;
	}
	public Integer getPricesort(){
		return pricesort;
	}
	public void setPricesort(Integer pricesort){
		this.pricesort = pricesort;
	}

	public String getStstartid() {
		return ststartid;
	}

	public void setStstartid(String ststartid) {
		this.ststartid = ststartid;
	}
}
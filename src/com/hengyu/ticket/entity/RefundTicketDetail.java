package com.hengyu.ticket.entity;

import java.math.BigDecimal;

 /**
  * @author 李冠锋 2016-01-19 
  */
public class RefundTicketDetail{

	//------------------字段-----------------
	
	private Integer id;
	private String rtid;
	private String checkcode;
	private BigDecimal price;
	private BigDecimal vprice;

	//----------------构造方法----------------
	
	public RefundTicketDetail(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getRtid(){
		return rtid;
	}
	public void setRtid(String rtid){
		this.rtid = rtid;
	}
	public String getCheckcode(){
		return checkcode;
	}
	public void setCheckcode(String checkcode){
		this.checkcode = checkcode;
	}
	public BigDecimal getPrice(){
		return price;
	}
	public void setPrice(BigDecimal price){
		this.price = price;
	}
	public BigDecimal getVprice(){
		return vprice;
	}
	public void setVprice(BigDecimal vprice){
		this.vprice = vprice;
	}
}
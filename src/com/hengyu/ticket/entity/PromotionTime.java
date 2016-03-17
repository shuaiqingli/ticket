package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2015-11-19 
  */
public class PromotionTime{

	//------------------字段-----------------
	
	private Integer id;
	private String pid;
	private String begintime;
	private String endtime;
	private Double reducesum;
	//优惠票百分比
	private Integer couponpercent;

	//----------------构造方法----------------
	
	public PromotionTime(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getPid(){
		return pid;
	}
	public void setPid(String pid){
		this.pid = pid;
	}
	public String getBegintime(){
		return begintime;
	}
	public void setBegintime(String begintime){
		this.begintime = begintime;
	}
	public String getEndtime(){
		return endtime;
	}
	public void setEndtime(String endtime){
		this.endtime = endtime;
	}

	public Double getReducesum() {
		return reducesum;
	}

	public void setReducesum(Double reducesum) {
		this.reducesum = reducesum;
	}

	public Integer getCouponpercent() {
		return couponpercent;
	}

	public void setCouponpercent(Integer couponpercent) {
		this.couponpercent = couponpercent;
	}
}
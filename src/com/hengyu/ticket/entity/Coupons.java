package com.hengyu.ticket.entity;

import java.math.BigDecimal;

/**
  * @author 李冠锋 2015-11-12 
  */
public class Coupons{

	//------------------字段-----------------
	
	//券码
	private String vouchercode;
	//券名
	private String vouchername;
	//摘要
	private String memo;
	//客户ID
	private String cid;
	//客户姓名
	private String cname;
	//开始日期
	private String begindate;
	//结束日期
	private String enddate;
	//最低消费
	private BigDecimal lowlimit;
	//面值
	private BigDecimal vprice;
	//是否可用
	private Integer isuse;
	//类型
	private Integer vsort;
	//是否可用
	private String isusename;
	//制券日期
	private String makedate;

	//----------------构造方法----------------
	
	public Coupons(){

	}

	//-------------- get/set方法 --------------
	
	public String getVouchercode(){
		return vouchercode;
	}
	public void setVouchercode(String vouchercode){
		this.vouchercode = vouchercode;
	}
	public String getVouchername(){
		return vouchername;
	}
	public void setVouchername(String vouchername){
		this.vouchername = vouchername;
	}
	public String getCid(){
		return cid;
	}
	public void setCid(String cid){
		this.cid = cid;
	}
	public String getCname(){
		return cname;
	}
	public void setCname(String cname){
		this.cname = cname;
	}
	public String getBegindate(){
		return begindate;
	}
	public void setBegindate(String begindate){
		this.begindate = begindate;
	}
	public String getEnddate(){
		return enddate;
	}
	public void setEnddate(String enddate){
		this.enddate = enddate;
	}
	public BigDecimal getLowlimit(){
		return lowlimit;
	}
	public void setLowlimit(BigDecimal lowlimit){
		this.lowlimit = lowlimit;
	}
	public BigDecimal getVprice(){
		return vprice;
	}
	public void setVprice(BigDecimal vprice){
		this.vprice = vprice;
	}
	public Integer getIsuse(){
		return isuse;
	}
	public void setIsuse(Integer isuse){
		this.isuse = isuse;
	}
	public String getIsusename(){
		return isusename;
	}
	public void setIsusename(String isusename){
		this.isusename = isusename;
	}
	public String getMakedate(){
		return makedate;
	}
	public void setMakedate(String makedate){
		this.makedate = makedate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getVsort() {
		return vsort;
	}

	public void setVsort(Integer vsort) {
		this.vsort = vsort;
	}
}
package com.hengyu.ticket.entity;

import java.math.BigDecimal;

/**
  * @author 李冠锋 2015-11-12 
  */
public class CouponsRule{

	//------------------字段-----------------
	
	//编码
	private Integer id;
	//规则名称
	private String rulename;
	//券类型
	private Integer vsort;
	//券类型
	private String vsortname;
	//开始日期
	private String begindate;
	//结束日期
	private String enddate;
	//有效期
	private Integer validday;
	//满额可送
	private BigDecimal buysum;
	//最低消费可用
	private java.math.BigDecimal lowlimit;
	//面值
	private java.math.BigDecimal vprice;
	//是否启用
	private Integer isenable;
	//是否启用中文
	private String isenablename;
	
	//制单日期
	private String makedate;
	//是否删除
	private Integer isdel;
	//是否只送异常
	private Integer islimitgive;

	//----------------构造方法----------------
	
	public String getMakedate() {
		return makedate;
	}

	public Integer getIslimitgive() {
		return islimitgive;
	}

	public void setIslimitgive(Integer islimitgive) {
		this.islimitgive = islimitgive;
	}

	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public CouponsRule(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getRulename(){
		return rulename;
	}
	public void setRulename(String rulename){
		this.rulename = rulename;
	}
	public Integer getVsort(){
		return vsort;
	}
	public void setVsort(Integer vsort){
		this.vsort = vsort;
	}
	public String getVsortname(){
		return vsortname;
	}
	public void setVsortname(String vsortname){
		this.vsortname = vsortname;
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
	public Integer getValidday(){
		return validday;
	}
	public void setValidday(Integer validday){
		this.validday = validday;
	}
	public BigDecimal getBuysum(){
		return buysum;
	}
	public void setBuysum(BigDecimal buysum){
		this.buysum = buysum;
	}
	public java.math.BigDecimal getLowlimit(){
		return lowlimit;
	}
	public void setLowlimit(java.math.BigDecimal lowlimit){
		this.lowlimit = lowlimit;
	}
	public java.math.BigDecimal getVprice(){
		return vprice;
	}
	public void setVprice(java.math.BigDecimal vprice){
		this.vprice = vprice;
	}
	public Integer getIsenable(){
		return isenable;
	}
	public void setIsenable(Integer isenable){
		this.isenable = isenable;
	}
	public String getIsenablename(){
		return isenablename;
	}
	public void setIsenablename(String isenablename){
		this.isenablename = isenablename;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
}
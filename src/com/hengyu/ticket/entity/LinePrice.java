package com.hengyu.ticket.entity;

import java.math.BigDecimal;

 /**
  * @author 李冠锋 2015-10-15 
  */
public class LinePrice{

	//------------------字段-----------------
	
	//编号
	private Integer id;
	//线路编号
	private Integer lmid;
	//线路名称
	private String linename;
	//发车站点id
	private String ststartid;
	//发车站点名称
	private String ststartname;
	//到达站点 id
	private String starriveid;
	//到达站点名称
	private String starrivename;
	//价格
	private BigDecimal price;
	//排序
	private Integer pricesort;
	//里程
	private Integer mileage;

	//----------------构造方法----------------
	
	public LinePrice(){

	}

	//-------------- get/set方法 --------------
	
	/**
	 *获取id 
	 * @return Integer  
	 */
	public Integer getId(){
		return id;
	}

	/**
	 *设置id 
	 * @param id 
 	 */
	public void setId(Integer id){
		this.id = id;
	}

	public Integer getLmid() {
		return lmid;
	}

	public void setLmid(Integer lmid) {
		this.lmid = lmid;
	}

	/**
	 *获取linename 
	 * @return String  
	 */
	public String getLinename(){
		return linename;
	}

	/**
	 *设置linename 
	 * @param linename 
 	 */
	public void setLinename(String linename){
		this.linename = linename;
	}

	/**
	 *获取ststartid 
	 * @return String  
	 */
	public String getStstartid(){
		return ststartid;
	}

	/**
	 *设置ststartid 
	 * @param ststartid 
 	 */
	public void setStstartid(String ststartid){
		this.ststartid = ststartid;
	}

	/**
	 *获取ststartname 
	 * @return String  
	 */
	public String getStstartname(){
		return ststartname;
	}

	/**
	 *设置ststartname 
	 * @param ststartname 
 	 */
	public void setStstartname(String ststartname){
		this.ststartname = ststartname;
	}

	/**
	 *获取starriveid 
	 * @return String  
	 */
	public String getStarriveid(){
		return starriveid;
	}

	/**
	 *设置starriveid 
	 * @param starriveid 
 	 */
	public void setStarriveid(String starriveid){
		this.starriveid = starriveid;
	}

	/**
	 *获取starrivename 
	 * @return String  
	 */
	public String getStarrivename(){
		return starrivename;
	}

	/**
	 *设置starrivename 
	 * @param starrivename 
 	 */
	public void setStarrivename(String starrivename){
		this.starrivename = starrivename;
	}

	/**
	 *获取price 
	 * @return String  
	 */
	public BigDecimal getPrice(){
		return price;
	}

	/**
	 *设置price 
	 * @param price 
 	 */
	public void setPrice(BigDecimal price){
		this.price = price;
	}

	public Integer getPricesort() {
		return pricesort;
	}

	public void setPricesort(Integer pricesort) {
		this.pricesort = pricesort;
	}

	public Integer getMileage() {
		return mileage;
	}

	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	
	
	
}
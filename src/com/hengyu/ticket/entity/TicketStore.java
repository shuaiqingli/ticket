package com.hengyu.ticket.entity;

import java.util.List;

/**
  * @author 李冠锋 2015-09-26 
  */
public class TicketStore{

	//------------------字段-----------------
	
	//编号
	private Integer id;
	//线路编号
	private Integer lmid;
	//车票日期
	private String ticketDate;
	//剩余车票
	private Integer balanceQuantity;
	//总票
	private Integer allquantity;
	//总优惠票
	private Integer couponquantity;
	//剩余优惠票
	private Integer balancecouponquantity;
	//锁定车票数量
	private Integer lockquantity;
	//生成车票日期
	private String makeDate;
	//班次号
	private String shiftCode;
	
	//车票线路
	private List<TicketLine> ticketlines;
	
	
	//===================== 查询条件
	
	
	//始发站城市编号
	private String citystartid;
	//始发站编号
	private String ststartid;
	//到达城市编号
	private String cityarriveid;
	//到达站点编号
	private String starriveid;
	//运输公司
	private String transcompany;
	//是否发布
	private Integer isrelease;
	//是否审核
	//0未审核
	//1已审核
	//2已取消
	private Integer isapprove;
	//行程价格规则
	private TripPriceRule tpr;
	
	

	//----------------构造方法----------------
	
	public TicketStore(){

	}

	//-------------- get/set方法 --------------
	
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 *获取ticketDate (车票日期)
	 * @return String  
	 */
	public String getTicketDate(){
		return ticketDate;
	}

	/**
	 *设置ticketDate (车票日期)
	 * @param ticketDate 
 	 */
	public void setTicketDate(String ticketDate){
		this.ticketDate = ticketDate;
	}

	/**
	 *获取balanceQuantity (剩余车票)
	 * @return Integer  
	 */
	public Integer getBalanceQuantity(){
		return balanceQuantity;
	}

	/**
	 *设置balanceQuantity (剩余车票)
	 * @param balanceQuantity 
 	 */
	public void setBalanceQuantity(Integer balanceQuantity){
		this.balanceQuantity = balanceQuantity;
	}

	/**
	 *获取makeDate (生成车票日期)
	 * @return String  
	 */
	public String getMakeDate(){
		return makeDate;
	}

	/**
	 *设置makeDate (生成车票日期)
	 * @param makeDate 
 	 */
	public void setMakedate(String makeDate){
		this.makeDate = makeDate;
	}

	public String getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	public Integer getLmid() {
		return lmid;
	}

	public void setLmid(Integer lmid) {
		this.lmid = lmid;
	}

	public String getCitystartid() {
		return citystartid;
	}

	public void setCitystartid(String citystartid) {
		this.citystartid = citystartid;
	}

	public String getStstartid() {
		return ststartid;
	}

	public void setStstartid(String ststartid) {
		this.ststartid = ststartid;
	}

	public String getCityarriveid() {
		return cityarriveid;
	}

	public void setCityarriveid(String cityarriveid) {
		this.cityarriveid = cityarriveid;
	}

	public String getStarriveid() {
		return starriveid;
	}

	public void setStarriveid(String starriveid) {
		this.starriveid = starriveid;
	}

	public String getTranscompany() {
		return transcompany;
	}

	public void setTranscompany(String transcompany) {
		this.transcompany = transcompany;
	}

	public Integer getAllquantity() {
		return allquantity;
	}

	public void setAllquantity(Integer allquantity) {
		this.allquantity = allquantity;
	}

	public List<TicketLine> getTicketlines() {
		return ticketlines;
	}

	public void setTicketlines(List<TicketLine> ticketlines) {
		this.ticketlines = ticketlines;
	}

	public Integer getIsrelease() {
		return isrelease;
	}

	public void setIsrelease(Integer isrelease) {
		this.isrelease = isrelease;
	}

	public Integer getIsapprove() {
		return isapprove;
	}

	public void setIsapprove(Integer isapprove) {
		this.isapprove = isapprove;
	}

	public Integer getCouponquantity() {
		return couponquantity;
	}

	public void setCouponquantity(Integer couponquantity) {
		this.couponquantity = couponquantity;
	}

	public Integer getBalanceCouponquantity() {
		return balancecouponquantity;
	}

	public void setBalanceCouponquantity(Integer balancecouponquantity) {
		this.balancecouponquantity = balancecouponquantity;
	}

	public TripPriceRule getTpr() {
		return tpr;
	}

	public void setTpr(TripPriceRule tpr) {
		this.tpr = tpr;
	}

	public Integer getBalancecouponquantity() {
		return balancecouponquantity;
	}

	public void setBalancecouponquantity(Integer balancecouponquantity) {
		this.balancecouponquantity = balancecouponquantity;
	}

	public Integer getLockquantity() {
		return lockquantity;
	}

	public void setLockquantity(Integer lockquantity) {
		this.lockquantity = lockquantity;
	}
}
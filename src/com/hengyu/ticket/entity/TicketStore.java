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
    //班次号
	private Long shiftid;
	//车票日期
	private String ticketdate;
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
	private String makedate;
	//是否发布
	private Integer isrelease;
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

    public Integer getLmid() {
        return lmid;
    }

    public void setLmid(Integer lmid) {
        this.lmid = lmid;
    }

    public String getTicketdate() {
        return ticketdate;
    }

    public void setTicketdate(String ticketdate) {
        this.ticketdate = ticketdate;
    }

    public Integer getBalanceQuantity() {
        return balanceQuantity;
    }

    public void setBalanceQuantity(Integer balanceQuantity) {
        this.balanceQuantity = balanceQuantity;
    }

    public Integer getAllquantity() {
        return allquantity;
    }

    public void setAllquantity(Integer allquantity) {
        this.allquantity = allquantity;
    }

    public Integer getCouponquantity() {
        return couponquantity;
    }

    public void setCouponquantity(Integer couponquantity) {
        this.couponquantity = couponquantity;
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

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public List<TicketLine> getTicketlines() {
        return ticketlines;
    }

    public void setTicketlines(List<TicketLine> ticketlines) {
        this.ticketlines = ticketlines;
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

    public TripPriceRule getTpr() {
        return tpr;
    }

    public void setTpr(TripPriceRule tpr) {
        this.tpr = tpr;
    }

    public Long getShiftid() {
        return shiftid;
    }

    public void setShiftid(Long shiftid) {
        this.shiftid = shiftid;
    }
}
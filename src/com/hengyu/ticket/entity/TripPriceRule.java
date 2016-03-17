package com.hengyu.ticket.entity;

import java.util.List;

import javax.validation.constraints.Min;

import com.hengyu.ticket.util.DateHanlder;

/**
  * @author 李冠锋 2015-11-14 
  */
public class TripPriceRule{

	//------------------字段-----------------
	
	private Integer id;
	private Integer tplid;
	private Integer weekday = 0;
	//总票
	private Integer ticketquantity = 0;
	//总优惠票
	private Integer couponticketquantity = 0;
	//锁票数量
	private Integer lockquantity = 0;
	
	private Integer isstart = 0;
	//开始卖票位置
	private Integer startseat  = 1;
	
	//行程价格列表
	private List<TripPriceSub> tps;
	//限制买票规则
	private List<StationLine> stationlines;
	
	//显示星期字符
	private String weekdaystr;
	
	//查询条件字段
	private Integer lmid;

	//----------------构造方法----------------
	
	public TripPriceRule(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	
	public Integer getTplid() {
		return tplid;
	}

	public void setTplid(Integer tplid) {
		this.tplid = tplid;
	}

	public Integer getWeekday(){
		return weekday;
	}
	public void setWeekday(Integer weekday){
		this.weekday = weekday;
	}
	public Integer getTicketquantity(){
		return ticketquantity;
	}
	public void setTicketquantity(Integer ticketquantity){
		this.ticketquantity = ticketquantity;
	}

	public Integer getIsstart() {
		return isstart;
	}

	public void setIsstart(Integer isstart) {
		this.isstart = isstart;
	}

	public List<TripPriceSub> getTps() {
		return tps;
	}

	public void setTps(List<TripPriceSub> tps) {
		this.tps = tps;
	}

	public Integer getLmid() {
		return lmid;
	}

	public void setLmid(Integer lmid) {
		this.lmid = lmid;
	}
	
	public void setWeekdaystr(String weekdaystr) {
		this.weekdaystr = weekdaystr;
	}

	public String getWeekdaystr() {
		if(this.weekday==null){
			return weekdaystr;
		}
		List<String> list = DateHanlder.getweekString(this.getWeekday());
		if(list!=null){
			StringBuffer sb = new StringBuffer();
			int i = 0;
			for (String s : list) {
				sb.append(s);
				if(i!=list.size()-1){
					sb.append("、");
				}
				i++;
			}
			this.weekdaystr = sb.toString();
		}
		return weekdaystr;
	}

	public List<StationLine> getStationlines() {
		return stationlines;
	}

	public void setStationlines(List<StationLine> stationlines) {
		this.stationlines = stationlines;
	}

	public Integer getCouponticketquantity() {
		return couponticketquantity;
	}

	public void setCouponticketquantity(Integer couponticketquantity) {
		this.couponticketquantity = couponticketquantity;
	}

	public Integer getLockquantity() {
		return lockquantity;
	}

	public void setLockquantity(Integer lockquantity) {
		this.lockquantity = lockquantity;
	}

	public Integer getStartseat() {
		return startseat;
	}

	public void setStartseat(Integer startseat) {
		this.startseat = startseat;
	}
	
}
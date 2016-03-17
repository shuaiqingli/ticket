package com.hengyu.ticket.entity;

import java.util.List;

import com.hengyu.ticket.util.DateHanlder;

/**
 * @author 李冠锋 2015-11-09
 */
public class Promotion {

	// ------------------字段-----------------

	private String id;
	private String promotionname;
	private Integer lmid;
	private String begindate;
	private String enddate;
	private Integer weekdays;
	private Double consamount;
	private String makeid;
	private String makename;
	private String makedate;
	private Integer isenable;
	private String isenablename;
	private Integer promotionline;
	private Integer isdel;
	
	private String weekdaystr;
	
	private List<PromotionTime> times;

	// ----------------构造方法----------------

	public Promotion() {

	}

	// -------------- get/set方法 --------------

	/**
	 * 获取id
	 * 
	 * @return Integer
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	public Integer getLmid() {
		return lmid;
	}

	public void setLmid(Integer lmid) {
		this.lmid = lmid;
	}

	/**
	 * 获取begindate
	 * 
	 * @return String
	 */
	public String getBegindate() {
		return begindate;
	}

	/**
	 * 设置begindate
	 * 
	 * @param begindate
	 */
	public void setBegindate(String begindate) {
		this.begindate = begindate;
	}

	/**
	 * 获取enddate
	 * 
	 * @return String
	 */
	public String getEnddate() {
		return enddate;
	}

	/**
	 * 设置enddate
	 * 
	 * @param enddate
	 */
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	/**
	 * 获取weekdays
	 * 
	 * @return String
	 */
	public Integer getWeekdays() {
		return weekdays;
	}

	/**
	 * 设置weekdays
	 * 
	 * @param weekdays
	 */
	public void setWeekdays(Integer weekdays) {
		this.weekdays = weekdays;
	}

	/**
	 * 获取makeid
	 * 
	 * @return String
	 */
	public String getMakeid() {
		return makeid;
	}

	/**
	 * 设置makeid
	 * 
	 * @param makeid
	 */
	public void setMakeid(String makeid) {
		this.makeid = makeid;
	}

	/**
	 * 获取makename
	 * 
	 * @return String
	 */
	public String getMakename() {
		return makename;
	}

	/**
	 * 设置makename
	 * 
	 * @param makename
	 */
	public void setMakename(String makename) {
		this.makename = makename;
	}

	/**
	 * 获取makedate
	 * 
	 * @return String
	 */
	public String getMakedate() {
		return makedate;
	}

	/**
	 * 设置makedate
	 * 
	 * @param makedate
	 */
	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public Integer getIsenable() {
		return isenable;
	}

	public void setIsenable(Integer isenable) {
		this.isenable = isenable;
	}

	public String getIsenablename() {
		return isenablename;
	}

	public void setIsenablename(String isenablename) {
		this.isenablename = isenablename;
	}

	public Integer getIsdel() {
		return isdel;
	}

	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}

	public Double getConsamount() {
		return consamount;
	}

	public void setConsamount(Double consamount) {
		this.consamount = consamount;
	}

	public String getPromotionname() {
		return promotionname;
	}

	public void setPromotionname(String promotionname) {
		this.promotionname = promotionname;
	}

	public Integer getPromotionline() {
		return promotionline;
	}

	public void setPromotionline(Integer promotionline) {
		this.promotionline = promotionline;
	}

	public String getWeekdaystr() {
		if(this.weekdays==null){
			return weekdaystr;
		}
		List<String> list = DateHanlder.getweekString(this.getWeekdays());
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

	public void setWeekdaystr(String weekdaystr) {
		this.weekdaystr = weekdaystr;
	}

	public List<PromotionTime> getTimes() {
		return times;
	}

	public void setTimes(List<PromotionTime> times) {
		this.times = times;
	}
}
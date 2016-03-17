package com.hengyu.ticket.entity;

import java.util.List;

import com.hengyu.ticket.util.DateHanlder;

/**
  * @author 李冠锋 2015-11-28 
  */
public class TripPriceList{

	//------------------字段-----------------
	
	private Integer id;
	private Integer lmid;
	private String begindate;
	private String enddate;
	private Integer isforever = 0;
	private Integer iseffect = 0;
	private Integer weekday;
	
	private String weekdaystr;
	
	private List<TripPriceRule> rules;

	//----------------构造方法----------------
	
	public TripPriceList(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getLmid(){
		return lmid;
	}
	public void setLmid(Integer lmid){
		this.lmid = lmid;
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
	public Integer getIsforever(){
		return isforever;
	}
	public void setIsforever(Integer isforever){
		this.isforever = isforever;
	}

	public Integer getIseffect() {
		return iseffect;
	}
	public void setIseffect(Integer iseffect) {
		this.iseffect = iseffect;
	}

	public List<TripPriceRule> getRules() {
		return rules;
	}

	public void setRules(List<TripPriceRule> rules) {
		this.rules = rules;
	}

	public Integer getWeekday() {
		return weekday;
	}

	public void setWeekday(Integer weekday) {
		this.weekday = weekday;
	}
	
	public void setWeekdaystr(String weekdaystr) {
		this.weekdaystr = weekdaystr;
	}

	public String getWeekdaystr() {
		if(this.weekday==null){
			weekday = 0;
		}
		List<String> list = DateHanlder.getweekString(127-this.getWeekday());
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
	
}
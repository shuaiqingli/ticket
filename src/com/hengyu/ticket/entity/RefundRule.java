package com.hengyu.ticket.entity;

import java.util.List;

/**
  * @author 李冠锋 2016-01-14 
  */
public class RefundRule{

	//------------------字段-----------------
	
	private Integer id;
	private String begindate;
	private String enddate;
	private Integer rank;
	private String makeid;
	private String makename;
	private String makedate;
	
	//线路，和时间
	private String time;
	private String line;
	
	
	//退票时间列表
	private List<RefundRuleTime> times; 
	//线路列表
	private List<RefundRuleLine> lines;
	
	//日期
	private String date;

	//----------------构造方法----------------
	
	public RefundRule(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
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
	public Integer getRank(){
		return rank;
	}
	public void setRank(Integer rank){
		this.rank = rank;
	}
	public String getMakeid(){
		return makeid;
	}
	public void setMakeid(String makeid){
		this.makeid = makeid;
	}
	public String getMakename(){
		return makename;
	}
	public void setMakename(String makename){
		this.makename = makename;
	}
	public String getMakedate(){
		return makedate;
	}
	public void setMakedate(String makedate){
		this.makedate = makedate;
	}

	public List<RefundRuleTime> getTimes() {
		return times;
	}

	public void setTimes(List<RefundRuleTime> times) {
		this.times = times;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<RefundRuleLine> getLines() {
		return lines;
	}

	public void setLines(List<RefundRuleLine> lines) {
		this.lines = lines;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
}
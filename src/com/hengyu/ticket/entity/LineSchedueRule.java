package com.hengyu.ticket.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.hengyu.ticket.util.DateHanlder;

/**
  * @author 李冠锋 2015-11-14 
  */
public class LineSchedueRule{

	//------------------字段-----------------
	
	//编号
	private Integer id;
	//星期
	@NotNull
	private Integer weekday;
	//首发时间
	@NotNull
	private String firststarttime;
	//间隔时间
	@NotNull
	private Integer intevalminute;
	//班次数量
	@NotNull
	private Integer shiftnum;
	//是否排班
	@NotNull
	private Integer isshift;
	//排班编号
	@NotNull
	private Integer lsid;
	
	//显示星期字符
	private String weekdaystr;
	//---------查询条件
	private Integer isapprove;

	private List<LineScheduDetail> linescheduledetail;
	
	//----------------构造方法----------------
	
	public LineSchedueRule(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public Integer getWeekday(){
		return weekday;
	}
	public void setWeekday(Integer weekday){
		this.weekday = weekday;
	}
	public String getFirststarttime(){
		return firststarttime;
	}
	public void setFirststarttime(String firststarttime){
		this.firststarttime = firststarttime;
	}
	public Integer getIntevalminute(){
		return intevalminute;
	}
	public void setIntevalminute(Integer intevalminute){
		this.intevalminute = intevalminute;
	}
	public Integer getShiftnum(){
		return shiftnum;
	}
	public void setShiftnum(Integer shiftnum){
		this.shiftnum = shiftnum;
	}
	public Integer getIsshift(){
		return isshift;
	}
	public void setIsshift(Integer isshift){
		this.isshift = isshift;
	}

	public Integer getLsid() {
		return lsid;
	}

	public void setLsid(Integer lsid) {
		this.lsid = lsid;
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

	public void setWeekdaystr(String weekdaystr) {
		this.weekdaystr = weekdaystr;
	}

	public List<LineScheduDetail> getLinescheduledetail() {
		return linescheduledetail;
	}

	public void setLinescheduledetail(List<LineScheduDetail> linescheduledetail) {
		this.linescheduledetail = linescheduledetail;
	}

	public Integer getIsapprove() {
		return isapprove;
	}

	public void setIsapprove(Integer isapprove) {
		this.isapprove = isapprove;
	}
}
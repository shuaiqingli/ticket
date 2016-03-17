package com.hengyu.ticket.common;
import com.hengyu.ticket.entity.LineManageStation;

/**
 * 站点信息列表
 * @author LGF
 *
 */
public class LineStation{
	//开始站点
	private LineManageStation startStation;
	//到达站点
	private LineManageStation endStation;
	
	public LineManageStation getStartStation() {
		return startStation;
	}
	public void setStartStation(LineManageStation startStation) {
		this.startStation = startStation;
	}
	public LineManageStation getEndStation() {
		return endStation;
	}
	public void setEndStation(LineManageStation endStation) {
		this.endStation = endStation;
	}
}
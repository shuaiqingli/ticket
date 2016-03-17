package com.hengyu.ticket.config;

import java.util.HashMap;
import java.util.Map;

import com.hengyu.ticket.common.Const;

public class TicketConfig {

	//购票控制的时间
	private Integer ticketTime = 30;
	//是否启用任务调度
	private boolean enableQuartz = true;
	//自动审核排班，天数
	private Integer approveScheduleDay = 15;
	//调试
	private boolean debug;
	//域名地址
	private String domain;
	
	//微信消息配置
	private Map<String,String> wxmsg = new HashMap<String, String>(0);	

	public Integer getTicketTime() {
		return ticketTime;
	}

	public void setTicketTime(Integer ticketTime) {
		this.ticketTime = ticketTime;
	}

	public boolean isEnableQuartz() {
		return enableQuartz;
	}

	public void setEnableQuartz(boolean enableQuartz) {
		this.enableQuartz = enableQuartz;
	}

	public Integer getApproveScheduleDay() {
		return approveScheduleDay;
	}

	public void setApproveScheduleDay(Integer approveScheduleDay) {
		this.approveScheduleDay = approveScheduleDay;
	}

	public Map<String, String> getWxmsg() {
		return wxmsg;
	}

	public void setWxmsg(Map<String, String> wxmsg) {
		this.wxmsg = wxmsg;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		Const.DEBUG = debug;
		this.debug = debug;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDomain() {
		return domain;
	}
}

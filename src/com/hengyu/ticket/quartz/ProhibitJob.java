package com.hengyu.ticket.quartz;


import com.hengyu.ticket.config.TicketConfig;
import com.hengyu.ticket.service.ProhibitService;
import com.hengyu.ticket.service.TicketStoreService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 应用禁售规则定时任务
 */
public class ProhibitJob {
	
	//是否启用
	private boolean enable = true;
	private Integer days;
	//车票配置
	@Autowired
	private TicketConfig conf;
	@Autowired
	private ProhibitService prohibitService;
	
	
	public void execute() throws Exception {
		if(!enable || !conf.isEnableQuartz()){
			return;
		}
		try {
			prohibitService.updateForTask(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}
}

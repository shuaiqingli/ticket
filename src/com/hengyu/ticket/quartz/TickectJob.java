package com.hengyu.ticket.quartz;


import com.hengyu.ticket.service.SaleOrderTicketService;
import org.springframework.beans.factory.annotation.Autowired;

import com.hengyu.ticket.config.TicketConfig;
import com.hengyu.ticket.service.ApproveTicketService;
import com.hengyu.ticket.service.SaleOrderService;
import com.hengyu.ticket.service.TicketStoreService;

/**
 * 发布车票定时器
 * @author LGF
 * 2015-10-16
 */
public class TickectJob{

	@Autowired
	private SaleOrderService sos;
	//是否启用
	private boolean enable = true;
	private Integer days;
	//车票配置
	@Autowired
	private TicketConfig conf;
	@Autowired
	private TicketStoreService tss;
	@Autowired
	private SaleOrderTicketService sots;
	@Autowired
	private ApproveTicketService ats;


	public void execute() throws Exception {
		if(enable==false||conf.isEnableQuartz()==false){
			return;
		}
		try {
			tss.updateTicketStoreReleaseByLineManage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//自动审核排班
			ats.saveapproveTicketByDay();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			//更新客户积分
			sos.updateCustomerOrderIntegral();
		} catch (Exception e) {
		}

		//更新过期车票
		try {
			sos.updateDateOutOrder();
			sots.updateDateOutOrder();
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

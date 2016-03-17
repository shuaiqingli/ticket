package com.hengyu.ticket.util;

import com.hengyu.ticket.entity.LineSchedue;
import com.hengyu.ticket.entity.LineSchedueRule;
import com.hengyu.ticket.service.ApproveTicketService;

/**
 * 生成车票线程
 * @author LGF
 *
 */
public class ApproveTicketThread implements Runnable{
	
	
	private ApproveTicketService ats;
	private LineSchedue ls;
	private LineSchedueRule rule;
	
	public ApproveTicketThread(ApproveTicketService ats,LineSchedue ls,LineSchedueRule rule){
		this.ats = ats;
		this.ls = ls;
		this.rule = rule;
	}
	
	@Override
	public void run() {
		try {
			int ret = ats.saveapproveTicket(ls, rule);
			Log.info(this.getClass(),"======= 线程启动，生成车票..... " + ret);
		} catch (Exception e) {
			e.printStackTrace();
			Log.info(this.getClass(),"======= 生成车票失败.....");
		}
	}
	
}
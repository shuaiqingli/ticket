package com.hengyu.ticket.quartz;

import org.springframework.beans.factory.annotation.Autowired;

import com.hengyu.ticket.config.TicketConfig;
import com.hengyu.ticket.service.SaleOrderService;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.Log;

/**
 * 自动取消订单
 * @author LGF
 *
 */
public class CancelOrderJob {
	
	@Autowired
	private SaleOrderService saleOrderService;
	@Autowired
	private TicketConfig conf;
	
	//默认是否中没有支付取消订单
	private Integer minute = 10;
	//是否启用
	private boolean enable = true;
	
	
	public void execute(){
		try {
			if(enable==false||conf.isEnableQuartz()==false){
				return;
			}
			Log.info(this.getClass(),"=========自动取消订单 : ", DateHanlder.getCurrentDateTime());
			saleOrderService.updatecancelorder(minute);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(this.getClass(),"=========自动订单异常 : ", e.getMessage());
		}
	}


	public Integer getMinute() {
		return minute;
	}

	public void setMinute(Integer minute) {
		this.minute = minute;
	}


	public boolean isEnable() {
		return enable;
	}


	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}

package com.hengyu.ticket.quartz;

import org.springframework.beans.factory.annotation.Autowired;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.config.TicketConfig;
import com.hengyu.ticket.entity.MakeConf;
import com.hengyu.ticket.entity.SaleOrder;
import com.hengyu.ticket.service.MakeConfService;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.Log;

/**
 * 自动取消订单
 * @author LGF
 *
 */
public class SaleOrderIdJob {

	@Autowired
	private MakeConfService makeConfService;
	@Autowired
	private TicketConfig conf;
	
	//是否启用
	private boolean enable = true;
	
	
	public void execute(){
		if(enable==false||conf.isEnableQuartz()==false){
			return;
		}
		try {
			Log.error(this.getClass(),"========= 增加化订单号 : ", DateHanlder.getCurrentDateTime());
			MakeConf makeConf = makeConfService.find(Const.SALEORDER_PREFIX_KEY);
			if(makeConf==null){
				Log.error(this.getClass(),"=============》没有找到相关记录");
				return;
			}
			Log.error(this.getClass(),"========= 当前订单号前缀 : ",makeConf.getCurrentValue());
			makeConf.setCurrentValue(makeConf.getCurrentValue()+1);
			makeConfService.update(makeConf );
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(this.getClass(),"========= 增加化订单号前缀 : ", e.getMessage());
		}
	}

	public boolean isEnable() {
		return enable;
	}


	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}

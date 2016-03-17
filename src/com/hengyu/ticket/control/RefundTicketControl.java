package com.hengyu.ticket.control;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.RefundTicket;
import com.hengyu.ticket.service.RefundTicketService;
import com.hengyu.ticket.service.SaleOrderService;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.RequestTool;

/**
 * 退款记录
 * @author LGF
 * 2016-01-19
 */
@Controller
@RequestMapping("/user")
public class RefundTicketControl {
	
	@Autowired
	private RefundTicketService rts;
	@Autowired
	private SaleOrderService sos;

	//退款列表
	@RequestMapping("refundTicketList")
	public String refundTicketList(Page page,Model m,HttpServletRequest req) throws Exception{
		Map<String, Object> params = RequestTool.paramsAsMap(req);
		page.setParam(params);
		rts.findlist(page);
		m.addAttribute(page);
		return "user/refund_ticket";
	}
	
	
	//退票，退款
	@RequestMapping("refundTicket")
	@ResponseBody
	public String refundTicket(String refundno,Integer status,String memo,BigDecimal actualprice,Integer rpercent,HttpServletRequest req) throws Exception{
		Assert.hasText(refundno,"退款单号不能为空！");
		Assert.notNull(status,"修改状态不能为空！");
		Assert.notNull(actualprice,"退款金额不能为空！");
		Assert.notNull(rpercent,"手续费扣除百分比不能为空！");
		Assert.isTrue(rpercent>=0&&rpercent<=100,"手续费填写不正确！");
		Admin admin = (Admin) req.getSession().getAttribute(Const.LOGIN_USER);
		int result = sos.updateRefundSaleOrederTicket(refundno, status, memo, admin, actualprice, rpercent);
		return String.valueOf(result);
	}
	
	//关闭订单
	@RequestMapping("cancelRefundTicket")
	@ResponseBody
	public String cancelRefundTicket(String refundno) throws Exception{
		Assert.isTrue(refundno!=null&&refundno.isEmpty()==false,"编号不能为空！");
		RefundTicket r = rts.find(refundno);
		String date = r.getRidedate();
		SimpleDateFormat df = DateHanlder.getDateFromat();
		Date time = df.parse(date);
		String starttime = r.getStarttime();
		if(starttime==null||starttime.trim().isEmpty()){
			starttime = "00:00";
		}
		Calendar c =  Calendar.getInstance();
		c.setTime(time);
		c.add(Calendar.HOUR_OF_DAY,Integer.parseInt(starttime.split(":")[0]));
		c.add(Calendar.MINUTE,Integer.parseInt(starttime.split(":")[1]));
		Assert.isTrue(System.currentTimeMillis()>c.getTime().getTime(),"该退款订单不能关闭！");
		r.setRtstatus(3);
		rts.updateRefundTicket(r);
		return "1";
	}
	
	//删除退票
	@RequestMapping("deleteRefundTicket")
	@ResponseBody
	public String deleteRefundTicket(String refundno,HttpServletRequest req) throws Exception{
		Assert.isTrue(refundno!=null&&refundno.isEmpty()==false,"编号不能为空！");
		RefundTicket r = rts.find(refundno);
		Assert.notNull(r,"没有找到该退款订单！");
		Assert.isTrue(r.getRtstatus()==0,"该退款订单不能删除！");
		return String.valueOf(rts.delete(refundno,r));
	}
	
}

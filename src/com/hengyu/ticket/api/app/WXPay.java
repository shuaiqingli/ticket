package com.hengyu.ticket.api.app;

import java.io.Writer;
import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.entity.API;
import com.hengyu.ticket.entity.SaleOrder;
import com.hengyu.ticket.service.SaleOrderDifferenceService;
import com.hengyu.ticket.service.SaleOrderService;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.DateUtil;
import com.hengyu.ticket.util.ValidationUtil;
import com.hengyu.ticket.util.WeixinHanlder;


/**
 * 微信支付
 *
 */
@Controller
@RequestMapping(value={"/app/api","/web/api"})
public class WXPay {
	
	@Autowired
	private SaleOrderService sos;

	@Autowired
	private SaleOrderDifferenceService sods;
	//微信预支付信息
	@SuppressWarnings("unchecked")
	@RequestMapping("wx/pay")
	public void pay(Writer w,String id,String openid,String type,HttpServletRequest rq) throws Exception{
		if(APIUtil.isNotNull(w,id)){
			API api = new API();
			SaleOrder so = sos.find(id);
			if(so==null){
				api.setCode(5012);
			}else if(4 == so.getStatus()){
				//订单已取消
				api.setCode(5013);
			}else{
				Map<String, String> pay;
				if(type!=null&&type.equals("APP")){
					pay = WeixinHanlder.appPay(so.getLinename(),id, so.getActualsum(), rq.getLocalAddr(), null,type);
				}else{
					Assert.hasText(openid,"微信唯一编号不能为空！");
					pay = WeixinHanlder.pay(so.getLinename(), id, so.getActualsum(), rq.getLocalAddr(), openid, null,null);
				}
                if(pay.containsKey("error")){
                    api.setCode(500);
                }
				api.getDatas().add(pay);
				so.setPayfeedback(JSON.toJSONString(pay));
			}
			APIUtil.toJSON(api, w);
		}
	}
	
	@RequestMapping("wx/scanCode")
	public void scanCode(String openid,HttpServletRequest req,Writer w) throws Exception{
		String payamt = req.getParameter("pay_amt");
		Assert.isTrue(ValidationUtil.isAmount(payamt),"金额不正确");
		String soid = req.getParameter("soid");
		SaleOrder order = sos.find(soid);
		Assert.notNull(order,"补差价--订单不存在");
		String sid = req.getParameter("sid");
		String sname = req.getParameter("sname");
		String memo = req.getParameter("memo");
		String makeDate = DateUtil.getCurrentDateTime();
		String body = req.getParameter("body");
		String pay_source = req.getParameter("pay_source");//预留字段 支付来源
		if(StringUtils.isEmpty(body)){
			body = "欢迎使用捷乘扫码支付!!!";
		}
		String inside_id = sods.saveRequestPayData(payamt, soid, makeDate, sid, sname, memo,"WX");
		BigDecimal money = sods.getPayAmt(inside_id);
		Map<String, String> pay = WeixinHanlder.pay(body, inside_id,money, req.getLocalAddr(), openid, "http://bisouyi.tunnel.qydev.com/ticket/wx/scanCodeNotify", "NATIVE");
		System.out.println(pay.toString());
	}
}

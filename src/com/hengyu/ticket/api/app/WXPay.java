package com.hengyu.ticket.api.app;

import java.io.Writer;
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
import com.hengyu.ticket.service.SaleOrderService;
import com.hengyu.ticket.util.APIUtil;
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
					pay = WeixinHanlder.appPay(so.getLinename(),id, so.getActualSum(), rq.getLocalAddr(), null,type);
				}else{
					Assert.hasText(openid,"微信唯一编号不能为空！");
					pay = WeixinHanlder.pay(so.getLinename(), id, so.getActualSum(), rq.getLocalAddr(), openid, null,null);
				}
				api.getDatas().add(pay);
				so.setPayfeedback(JSON.toJSONString(pay));
			}
			APIUtil.toJSON(api, w);
		}
	}
	
}

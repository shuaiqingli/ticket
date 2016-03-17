package com.hengyu.ticket.control.weixin;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.SaleOrder;
import com.hengyu.ticket.service.SaleOrderService;
import com.hengyu.ticket.util.Log;
import com.hengyu.ticket.util.MatrixToImageWriter;
import com.hengyu.ticket.util.NumberCreate;
import com.hengyu.ticket.util.SmsUtil;
import com.hengyu.ticket.util.TemplateHanlder;
import com.hengyu.ticket.util.WeixinHanlder;

/**
 * 微信支付回调
 *
 */
@Controller
@RequestMapping("/wx")
public class Notify {
	
	@Autowired
	private SaleOrderService saleOrderService;
	
	@RequestMapping("notify")
	@ResponseBody
	public String notifyURL(HttpServletRequest req) throws Exception{
		try {
			Map<String, String> map = WeixinHanlder.XML.toMap(req.getInputStream());
			String orderid = map.get("out_trade_no");
			String createSign = WeixinHanlder.createSign(map);
//			result_code=SUCCESS, return_code=SUCCESS
			boolean success = "success".equalsIgnoreCase(map.get("return_code"))&&"success".equalsIgnoreCase(map.get("result_code"));
			boolean sign = map.get("sign").equals(createSign);
			Log.info(this.getClass(),"==================== 微信支付回调 ======================");
			Log.info(this.getClass(),"============= >> 加密签名：" + createSign);
			Log.info(this.getClass(),"============= >> 回调结果：" + map);
			Log.info(this.getClass(),"============= >> 回调状态：" + success);
			Log.info(this.getClass(),"============= >> 签名验证：" + sign);
			if(success&&sign){
				String qrcodepath = req.getSession().getServletContext().getRealPath(Const.QRCODE_PATH);
				return saleOrderService.updatePayNotify(orderid, qrcodepath,"WX");
			}else{
				Log.error("============= >> 订单异常！"+map);
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("订单异常====》",JSON.toJSONString(e.getStackTrace()),e.getMessage());
			throw e;
		}
	}

}

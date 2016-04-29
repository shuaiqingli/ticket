package com.hengyu.ticket.control.weixin;


import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.hengyu.ticket.util.*;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.SaleOrder;
import com.hengyu.ticket.service.SaleOrderDifferenceService;
import com.hengyu.ticket.service.SaleOrderService;

/**
 * 微信支付回调
 *
 */
@Controller
@RequestMapping("/wx")
public class Notify {
	
	@Autowired
	private SaleOrderService saleOrderService;
	
	@Autowired
	private SaleOrderDifferenceService sods;
	
	@RequestMapping("notify")
	@ResponseBody
	public String notifyURL(HttpServletRequest req) throws Exception{
		try {
			Map<String, String> map = WeixinHanlder.XML.toMap(req.getInputStream());
			String orderid = map.get("out_trade_no");
			String out_code = map.get("transaction_id");
			String createSign = WeixinHanlder.createSign(map);
//			result_code=SUCCESS, return_code=SUCCESS
			boolean success = "success".equalsIgnoreCase(map.get("return_code"))&&"success".equalsIgnoreCase(map.get("result_code"));
			boolean sign = map.get("sign").equals(createSign);
			Log.error("微信支付回调，订单号：",orderid," 微信订单号：",map.get("transaction_id"));
			if(success&&sign){
				String qrcodepath = req.getSession().getServletContext().getRealPath(Const.QRCODE_PATH);
				return saleOrderService.updatePayNotify(orderid, qrcodepath,"WX",out_code);
			}else{
				Log.error("============= >> 订单异常,签名或者其他错误！",map);
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("订单异常====》",JSON.toJSONString(e.getStackTrace()),e.getMessage());
			throw e;
		}
	}

	@RequestMapping("scanCodeNotify")
	@ResponseBody
	public String scanCodeNotify(HttpServletRequest req){
		Map<String, String> map = new TreeMap<>();
		try {
			String out_trade_no = req.getParameter("out_trade_no");
			String transaction_id = req.getParameter("transaction_id");
			String total_fee = req.getParameter("total_fee");
			int updatePay = sods.updatePay(out_trade_no, transaction_id, "WX",ConvertUtil.fenToYuan(total_fee));
			/*map = WeixinHanlder.XML.toMap(req.getInputStream());
			String orderid = map.get("out_trade_no");
			String out_code = map.get("transaction_id");
			String total_fee = map.get("total_fee");
			
			String createSign = WeixinHanlder.createSign(map);
			boolean success = "success".equalsIgnoreCase(map.get("return_code"))&&"success".equalsIgnoreCase(map.get("result_code"));
			boolean sign = map.get("sign").equals(createSign);
			if(success&&sign){
				int updatePay = sods.updatePay(orderid, out_code, "WX",ConvertUtil.fenToYuan(total_fee));
			}else{
				Log.error("微信扫码支付回调签名或者其他错误:",map);
				return "error";
			}*/
			return "success";
		} catch (Exception e) {
			Log.exception(e,"微信扫码支付回调异常:"+map.toString());
		}
		return "error";
	}
}

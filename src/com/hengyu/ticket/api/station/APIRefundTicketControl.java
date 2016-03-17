package com.hengyu.ticket.api.station;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.entity.RefundTicket;
import com.hengyu.ticket.service.RefundTicketService;
import com.hengyu.ticket.util.APIStatus;
import com.hengyu.ticket.util.DateUtil;
import com.hengyu.ticket.util.RequestTool;

@Controller
public class APIRefundTicketControl {
	@Autowired
	private RefundTicketService refundticketService;

	// 获取待确认退票数量
	@RequestMapping(value = "/api/station/waitAffirmRefundTicketQuantity", method = RequestMethod.POST)
	@ResponseBody
	public String waitAffirmRefundTicketQuantity(HttpServletRequest request) throws Exception {


		Map<String, Object> result = new HashMap<String, Object>();
		String ststartid = request.getParameter("ststartid");
		Map<String, Object> a = new HashMap<String, Object>();

		String before7 = DateUtil.formatDate(DateUtil.calculatedays(DateUtil.getCurrentDate(), -7));
		a.put("makedate", before7);
		a.put("ststartid", ststartid);
		long rq = refundticketService.waitAffirmRefundTicketQuantity(a);
		result.put("quantity", rq);
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 待确认退票列表
	@RequestMapping(value = "/api/station/waitAffirmRefundList", method = RequestMethod.POST)
	@ResponseBody
	public String waitAffirmRefundList(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer pageno = RequestTool.getInt(request, "pageno", 1);
		Integer pagesize = RequestTool.getInt(request, "pagesize", 10);
		String ststartid = request.getParameter("ststartid");
		String before7 = DateUtil.formatDate(DateUtil.calculatedays(DateUtil.getCurrentDate(), -7));
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("ststartid", ststartid);
		a.put("startOfPage", (pageno - 1) * pagesize);
		a.put("pageSize", pagesize);
		a.put("makedate", before7);
		List rtls = refundticketService.waitAffirmRefundTicketList(a);
		Long count = refundticketService.waitAffirmRefundTicketQuantity(a);
//		result.put("size", rtls.size());
		result.put("size", count);
		result.put("data", rtls);
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 退票单详情
	@RequestMapping(value = "/api/station/refundTicketDetail", method = RequestMethod.POST)
	@ResponseBody
	public String refundTicketDetail(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String id = request.getParameter("id");
		Map<String, Object> c = new HashMap<String, Object>();
		c.put("id", id);
		RefundTicket rt = refundticketService.find(id);

		result.put("data", rt);
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 退票确认改状态
	@RequestMapping(value = "/api/station/refundTicketAffirm", method = RequestMethod.POST)
	@ResponseBody
	public String refundTicketAffirm(HttpServletRequest request) throws Exception {
		Map<String, Object> amap = (Map<String, Object>) request.getAttribute("amap");
		String userid = amap.get("userid").toString();
		String realname = amap.get("realname").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String affirmmemo = request.getParameter("affirmmemo");
		Map<String, Object> c = new HashMap<String, Object>();
		c.put("id", id);
		c.put("affirmid", userid);
		c.put("affirmname", realname);
		c.put("affirmdate", DateUtil.getCurrentDateTime());
		c.put("affirmmemo", affirmmemo);
		refundticketService.refundTicketAffirm(c);

		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

}

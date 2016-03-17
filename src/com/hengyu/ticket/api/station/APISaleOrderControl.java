package com.hengyu.ticket.api.station;

import java.util.ArrayList;
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
import com.alibaba.fastjson.JSONObject;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.SaleOrder;
import com.hengyu.ticket.entity.SaleOrderTicket;
import com.hengyu.ticket.service.LineManageService;
import com.hengyu.ticket.service.SaleOrderService;
import com.hengyu.ticket.service.SaleOrderTicketService;
import com.hengyu.ticket.service.SeatService;
import com.hengyu.ticket.service.TicketStoreService;
import com.hengyu.ticket.util.APIStatus;
import com.hengyu.ticket.util.DateUtil;
import com.hengyu.ticket.util.RequestTool;

@Controller
public class APISaleOrderControl {
	// 订单服务类
	@Autowired
	private SaleOrderService saleorderService;
	@Autowired
	private SaleOrderTicketService saleorderticketService;
	@Autowired
	private SeatService seatService;
	@Autowired
	private TicketStoreService ticketstoreService;
	@Autowired
	private LineManageService linemanageService;

	// 订单详情
	@RequestMapping(value = "/api/station/saleOrderDetail", method = RequestMethod.POST)
	@ResponseBody
	public String selectPlate(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String ticketcode = request.getParameter("ticketcode");
		SaleOrder so = null;
		Map<String, Object> c = new HashMap<String, Object>();
		c.put("id", id);
		c.put("ticketcode", ticketcode);
		c.put("soid", id);
		if (id != null && !id.equals("")) {
			so = saleorderService.find(id);
			// 查询sale_order_ticket
			List sotls = saleorderticketService.getSaleOrderTicketBySOIDTC(c);
			result.put("saleorderticket", sotls);
		} else if (ticketcode != null && !ticketcode.equals("")) {
			so = saleorderService.getSaleOrderByTicketCode(ticketcode);
			// 查询sale_order_ticket
			List sotls = saleorderticketService.getSaleOrderTicketBySOIDTC(c);
			result.put("saleorderticket", sotls);
		}
		// 没有找到数据，
		if (so == null) {
			result.put("status", APIStatus.PARAMETER_ERROR_STATUS);
			result.put("info", APIStatus.PARAMETER_ERROR_INFO);
			return JSON.toJSONString(result);
		} else {
			int soldquantity = saleorderticketService.getValidateTicketCountBySOID(c);
			so.setQuantity(soldquantity);
			//补充运输公司名称
			LineManage lm = linemanageService.find(so.getLmid());
			result.put("transcompany", lm.getTranscompany());
			result.put("data", so);
			result.put("status", APIStatus.SUCCESS_STATUS);
			result.put("info", APIStatus.SUCCESS_INFO);
		}
		return JSON.toJSONString(result);
	}

	// 打印车票
	@RequestMapping(value = "/api/station/printSaleOrderTicket", method = RequestMethod.POST)
	@ResponseBody
	public String printSaleOrderTicket(HttpServletRequest request) throws Exception {
		// Admin admin = (Admin) request.getSession().getAttribute("admin");
		Map<String, Object> amap = (Map<String, Object>) request.getAttribute("amap");
		Map<String, Object> result = new HashMap<String, Object>();
		String id = request.getParameter("id");
		int isabnormal = RequestTool.getInt(request, "isabnormal", 1);
		String memo = request.getParameter("memo");
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("id", id);
		List sotls = saleorderticketService.getSaleOrderTicketBySOIDTC(a);// 获取车票列表
		result.put("data", sotls);
		SaleOrder so = new SaleOrder();
		so.setId(id);
		so.setStatus(1);
		so.setStatusName("已取票");
		so.setIsabnormal(isabnormal);
		so.setMemo(memo);
		so.setSid(amap.get("userid").toString());
		so.setSname(amap.get("realname").toString());
		so.setTakedate(DateUtil.getCurrentDateTime());
		int u = saleorderService.updateStatus(so);
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 验票
	@RequestMapping(value = "/api/station/checkTicket", method = RequestMethod.POST)
	@ResponseBody
	public String checkTicket(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String checkcode = request.getParameter("checkcode").trim();
		String shiftnum = request.getParameter("shiftnum").trim();
		String currstationid = request.getParameter("currstationid").trim();
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("checkcode", checkcode);
		a.put("shiftnum", shiftnum);
		a.put("ridedate", DateUtil.getCurrentDateString());
		a.put("currdate", DateUtil.getCurrentDateTime());
		// 对比资料
		SaleOrderTicket sot = null;
		// sot = saleorderticketService.constrastTicket(a);
		// 按验票码查出 票面资料
		sot = saleorderticketService.find(checkcode);
		if(sot.getStatus()!=0){
			result.put("status", "06");
			result.put("info", "该车票无效或已退票");
		}else if (!sot.getShiftnum().equals(shiftnum)) {
			result.put("status", "09");
			result.put("info", "该车票不能乘坐本班次");
		} else if (!sot.getStstartid().equals(currstationid)) {
			result.put("status", "07");
			result.put("info", "该车票不是当前站点的票");
		} else if (!sot.getRidedate().equals(DateUtil.getCurrentDateString())) {
			result.put("status", "08");
			result.put("info", "该车票不是今天的班次");
		} else {
			saleorderticketService.checkTicket(a);
			result.put("status", "00");
			result.put("info", "该车票有效");
		}
		result.put("saleorderticket", sot);
		return JSON.toJSONString(result);
	}

	// 单张异常验票
	@RequestMapping(value = "/api/station/abnormalCheckTicket", method = RequestMethod.POST)
	@ResponseBody
	public String abnormalCheckTicket(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String checkcode = request.getParameter("checkcode");
		String shiftnum = request.getParameter("shiftnum");
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("checkcode", checkcode);
		a.put("shiftnum", shiftnum);
		a.put("currdate", DateUtil.getCurrentDateTime());
		// 按验票码改票面班次
		int updso = saleorderticketService.abnormalUpdateShiftNum(a);
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 查找整单-----验票
	@RequestMapping(value = "/api/station/findSaleOrderForCheck", method = RequestMethod.POST)
	@ResponseBody
	public String findSaleOrderForCheck(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String ticketcode = request.getParameter("ticketcode");// 取票码
		String shiftnum = request.getParameter("shiftnum");
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("ticketcode", ticketcode);
		a.put("shiftnum", shiftnum);
		a.put("ridedate", DateUtil.getCurrentDateString());
		a.put("currdate", DateUtil.getCurrentDateTime());

		// 根据取票码找出订单
		SaleOrder so = saleorderService.getAllSaleOrderByTicketCode(ticketcode);
		Map<String,Object> b = new HashMap<String,Object>();
		b.put("ticketcode", ticketcode);
		int ticketcount = saleorderticketService.getValidateTicketCountBySOID(b);
		so.setQuantity(ticketcount);
		// 按取票码查找已验票人数
		long alreadyontrain = saleorderticketService.getAlreadyOnTrain(a);
		// 订单里的所有票
		List sotls = saleorderticketService.getSaleOrderTicketBySOIDTC(a);
		result.put("saleorderticket", sotls);
		result.put("alreadyontrain", alreadyontrain);// 已验票人数
		result.put("status", "00");
		result.put("info", "该车票有效");
		// }
		result.put("saleorder", so);
		return JSON.toJSONString(result);
	}

	// 整单验票
	@RequestMapping(value = "/api/station/saleorderCheck", method = RequestMethod.POST)
	@ResponseBody
	public String saleorderCheck(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String ticketcode = request.getParameter("ticketcode");// 取票码
		String shiftnum = request.getParameter("shiftnum");
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("ticketcode", ticketcode);
		a.put("shiftnum", shiftnum);

		// 根据取票码找出订单
		SaleOrder so = saleorderService.getAllSaleOrderByTicketCode(ticketcode);
		// 判断订单是否本班次
		if(so.getPayStatus()!=1){
			result.put("status", "06");
			result.put("info", "该订单没有付款");
		}else if (!so.getShiftNum().equals(shiftnum)) {
			result.put("status", "09");
			result.put("info", "该车票不能乘坐本班次");
		} else {
			// 验票
			int socheck = saleorderticketService.saleorderCheck(a);
			// 修改sale_order取票状态
			so.setStatus(1);
			so.setStatusName("已取票");
			saleorderService.updateStatus(so);
			result.put("status", "00");
			result.put("info", "验票成功");
		}
		return JSON.toJSONString(result);
	}

	// 整单异常验票
	@RequestMapping(value = "/api/station/saleorderAbnormalCheck", method = RequestMethod.POST)
	@ResponseBody
	public String saleorderAbnormalCheck(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String ticketcode = request.getParameter("ticketcode");// 取票码
		String shiftnum = request.getParameter("shiftnum");
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("ticketcode", ticketcode);
		a.put("shiftnum", shiftnum);
		a.put("currdate", DateUtil.getCurrentDateTime());
		// 将sale_order 班次改变
		saleorderService.abnormalUpdateSaleOrderShiftNum(a);

		// 将sale_order_ticket班次改变,by ticketcode
		int sotchk = saleorderticketService.abnormalUpdateShiftNumByTicketCode(a);
		//System.out.println("-----------" + sotchk);
		result.put("status", "00");
		result.put("info", "验票成功");
		return JSON.toJSONString(result);
	}

	// 已验过的车票

	// 查询车票状态
	@RequestMapping(value = "/api/station/findTicketStatus", method = RequestMethod.POST)
	@ResponseBody
	public String findTicketStatus(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String checkcode = request.getParameter("checkcode");
		// 对比资料
		SaleOrderTicket sot = null;
		sot = saleorderticketService.find(checkcode);
		if(sot.getStatus()!=0){
			result.put("status", "06");
			result.put("info", "该车票无效或已退票");
		} else if(sot == null){
			result.put("status", "09");
			result.put("info", "该车票不存在");
		} else {
			result.put("saleorderticket", sot);
			result.put("status", "00");
			result.put("info", "该车票有效");
		}
		return JSON.toJSONString(result);
	}

	// 搜索订单
	@RequestMapping(value = "/api/station/searchSaleOrder", method = RequestMethod.POST)
	@ResponseBody
	public String searchSaleOrder(Integer isfull, HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// String ststartid = request.getParameter("ststartid");
		String keywords = request.getParameter("keywords");
		Map<String, Object> a = new HashMap<String, Object>();
		// a.put("ststartid", ststartid);
		a.put("keywords", keywords);
		a.put("isfull", isfull == null ? 0 : isfull);
		List sols = saleorderService.searchSaleOrder(a);
		ArrayList als = new ArrayList();
		for (int i = 0; i < sols.size(); i++) {
			Map<String, Object> b = new HashMap<String, Object>();
			SaleOrder o = (SaleOrder) sols.get(i);
			Map<String,Object> c = new HashMap<String,Object>();
			c.put("soid", o.getId());
			b.put("ticketcode", o.getTicketCode());
			int validatecount = saleorderticketService.getValidateTicketCountBySOID(c);
			b.put("id", o.getId());
			b.put("status", o.getStatus());
			b.put("statusName", o.getStatusName());
			b.put("lName", o.getLName());
			b.put("lMobile", o.getLMobile());
			b.put("quantity", validatecount);
			b.put("sTArriveName", o.getStarrivename());
			b.put("iDCode", o.getIDCode());
			als.add(b);
		}
		result.put("data", als);
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 查询异常记录
	@RequestMapping(value = "/api/station/getAbnormalList", method = RequestMethod.POST)
	@ResponseBody
	public String getAbnormalList(HttpServletRequest request) throws Exception {
		// Admin admin = (Admin) request.getSession().getAttribute("admin");
		// Map<String,Object> amap = (Map<String,Object>)
		// request.getAttribute("amap");
		Map<String, Object> a = new HashMap<String, Object>();
		Integer pageno = RequestTool.getInt(request, "pageno", 1);
		Integer pagesize = RequestTool.getInt(request, "pagesize", 10);
		String stid = request.getParameter("stid");
		a.put("startOfPage", (pageno - 1) * pagesize);
		a.put("pageSize", pagesize);
		a.put("currstationid", stid);// 站务所属站点
		// 获取销售记录条数，
		List<SaleOrder> sols = saleorderService.getAbnormalSaleOrderList(a);
		Long totalcount = saleorderService.totalCountAbnormal(a);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		result.put("data", sols);
		result.put("totalcount", totalcount);
		return JSON.toJSONString(result);
	}

	// 站务--整单退票,不写退票表，只改状态
	@RequestMapping(value = "/api/station/saleOrderRefund", method = RequestMethod.POST)
	@ResponseBody
	public String saleOrderRefund(HttpServletRequest request) throws Exception {
		Map<String, Object> amap = (Map<String, Object>) request.getAttribute("amap");
		String userid = amap.get("userid").toString();
		String realname = amap.get("realname").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		String id = request.getParameter("id");// 销售单ID
		String islock = request.getParameter("islock");

		Admin admin = new Admin();
		admin.setUserID(userid);
		admin.setRealName(realname);
		saleorderService.updateForCancelLockTicket(admin, id, Integer.parseInt(islock));
		
		/*Map<String, Object> c = new HashMap<String, Object>();
		c.put("id", id);
		List sot = saleorderticketService.getSaleOrderTicketBySOIDTC(c);
		
		// 修改sale_order退票状态
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("id", id);
		a.put("status", 3);
		a.put("statusName", "已退票");
		a.put("memo", "站务退票");
		a.put("sid", userid);
		a.put("sname", realname);
		a.put("takedate", DateUtil.getCurrentDateString());
		a.put("quantity", 0);
		a.put("paystatus", 0);
		a.put("paystatusname", "未付款");
		saleorderService.saleOrderRefund(a);
		// 修改sale_order_ticket 退票状态
		Map<String, Object> b = new HashMap<String, Object>();
		b.put("soid", id);
		saleorderticketService.ticketRefundBySOID(b);

		// 释放位置，ticket_store 余票数量增加，锁定票数量减少，seat表issale变0
		// 先获取sale_order_ticket列表
		
//		int lockquantity = 0;// 锁定数量
//		int releasequantity = 0;// 释放数量
		for (int i = 0; i < sot.size(); i++) {
			SaleOrderTicket o = (SaleOrderTicket) sot.get(i);
			if (islock.equals("0")) {
				// 释放位置
				Map<String, Object> d = new HashMap<String, Object>();
				d.put("issale", 0);
				d.put("id", o.getSeatid());
				seatService.ReleaseSeat(d);
			} else if (islock.equals("1")) {
				// 锁定位置
//				lockquantity++;
				Map<String, Object> d = new HashMap<String, Object>();
				d.put("issale", 2);
				d.put("id", o.getSeatid());
				seatService.ReleaseSeat(d);
			}
			//改sale_order_ticket的车票状态
			Map<String,Object> f = new HashMap<String,Object>();
			f.put("status", 3);
			f.put("paystatus", 0);
			f.put("sid", userid);
			f.put("sname", realname);
			f.put("sdate", DateUtil.getCurrentDateString());
			f.put("checkcode", o.getCheckcode());
			saleorderticketService.ticketDealByCheckCode(f);
		}
		// 释放，余票数量增加，
		SaleOrder so = saleorderService.find(id);
		if (islock.equals("0")) {
			Map<String, Object> e = new HashMap<String, Object>();
			e.put("quantity", sot.size());
			e.put("shiftcode", so.getShiftNum());
			e.put("ticketdate", so.getRideDate());
			ticketstoreService.releaseZWReleaseQuantity(e,so);
		}
		//锁定，锁定票数增加
		if(islock.equals("1")){
			Map<String, Object> e = new HashMap<String, Object>();
			e.put("quantity", sot.size());
			e.put("shiftcode", so.getShiftNum());
			e.put("ticketdate", so.getRideDate());
			ticketstoreService.releaseZWLockQuantity(e);
		}*/

		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 站务--收款
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/api/station/saleOrderReceive", method = RequestMethod.POST)
	@ResponseBody
	public String saleOrderReceive(HttpServletRequest request) throws Exception {
		Map<String, Object> amap = (Map<String, Object>) request.getAttribute("amap");
		String userid = amap.get("userid").toString();
		String realname = amap.get("realname").toString();
		Map<String, Object> result = new HashMap<String, Object>();

		String paramstr = request.getParameter("paramstr");
		JSONObject json = JSON.parseObject(paramstr);

		String id = json.getString("id");
		String islock = json.getString("islock");
		List dls = json.getJSONArray("data");

		List<String> checkcodeList = new ArrayList<String>();
		for (Object data : dls) {
			Map<String, Object> parsedData = (Map<String, Object>) data;
			String checkcode = parsedData.get("checkcode").toString();
			String isbuy = parsedData.get("isbuy").toString();
			if (isbuy.equals("1")) {
				checkcodeList.add(checkcode);
			}
		}
		Admin admin = new Admin();
		admin.setUserID(userid);
		admin.setRealName(realname);
		saleorderService.updateForPaylockTicket(admin, id, checkcodeList, Integer.parseInt(islock));

		/*// 根据id将sale_order的paystatus改成1，将收款部份的sale_order_ticket的paystatus=1，将未收款部份的sale_order_ticket的status=3
		// 写入操作站务信息
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("id", id);
		a.put("paystatus", 1);
		a.put("paystatusname", "已付款");
		a.put("memo", "站务收款");
		a.put("receiveid", userid);
		a.put("receivename", realname);
		a.put("receivedate", DateUtil.getCurrentDateString());
		int quantity = 0;// 付款票数
		int lockquantity = 0;// 锁定数量
		int releasequantity = 0;// 释放数量
		for (int i = 0; i < dls.size(); i++) {
			Map<String, Object> e = (Map<String, Object>) dls.get(i);
			String checkcode = e.get("checkcode").toString();
			String isbuy = e.get("isbuy").toString();
			String seatid = e.get("seatid").toString();

			if (isbuy.equals("0")) {// 退票，并释放或锁定位置
				Map<String, Object> b = new HashMap<String, Object>();
				b.put("checkcode", checkcode);
				b.put("status", 3);
				b.put("paystatus", 0);
				b.put("paystatusname", "未付款");
				b.put("sid", userid);
				b.put("sname", realname);
				b.put("sdate", DateUtil.getCurrentDateString());
//				saleorderticketService.ticketDealByCheckCode(b);
				if (islock.equals("0")) {
					// 释放位置
					Map<String, Object> d = new HashMap<String, Object>();
					d.put("issale", 0);
					d.put("id", seatid);
					seatService.ReleaseSeat(d);
					// 改ticket_store数量
					releasequantity++;
				} else if (islock.equals("1")) {
					// 锁定位置
					lockquantity++;
					Map<String, Object> d = new HashMap<String, Object>();
					d.put("issale", 2);
					d.put("id", seatid);
					seatService.ReleaseSeat(d);
				}
				//改sale_order_ticket的车票状态
				Map<String,Object> f = new HashMap<String,Object>();
				f.put("status", 3);
				f.put("paystatus", 0);
//				f.put("paystatusname", "未付款");
				f.put("sid", userid);
				f.put("sname", realname);
				f.put("sdate", DateUtil.getCurrentDateString());
				f.put("checkcode",checkcode);
				saleorderticketService.ticketDealByCheckCode(f);
			} else if (isbuy.equals("1")) {// 付款，记站务信息
				Map<String, Object> b = new HashMap<String, Object>();
				b.put("checkcode", checkcode);
				b.put("status", 0);
				b.put("paystatus", 1);
				b.put("paystatusname", "已付款");
				b.put("sid", userid);
				b.put("sname", realname);
				b.put("sdate", DateUtil.getCurrentDateString());
				saleorderticketService.ticketDealByCheckCode(b);
				quantity++;
			}
		}
		SaleOrder so = saleorderService.find(id);
		// 如果释放releasequantity>0，ticket_store的余票增加
		if (releasequantity > 0) {
			Map<String, Object> e = new HashMap<String, Object>();
			e.put("quantity", releasequantity);
			e.put("shiftcode", so.getShiftNum());
			e.put("ticketdate", so.getRideDate());
			ticketstoreService.releaseZWReleaseQuantity(e,so);
		}
		// 如果锁定lockquantity>0,ticket_store的锁定票增加
		if(lockquantity > 0){
			Map<String, Object> e = new HashMap<String, Object>();
			e.put("quantity", lockquantity);
			e.put("shiftcode", so.getShiftNum());
			e.put("ticketdate", so.getRideDate());
			ticketstoreService.releaseZWLockQuantity(e);
		}
		a.put("quantity", quantity);
		saleorderService.saleOrderReceive(a);*/
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}
}

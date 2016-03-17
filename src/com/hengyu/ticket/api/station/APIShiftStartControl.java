package com.hengyu.ticket.api.station;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.entity.CityStation;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.SaleOrder;
import com.hengyu.ticket.entity.ShiftStart;
import com.hengyu.ticket.entity.TicketStore;
import com.hengyu.ticket.service.AdminLineService;
import com.hengyu.ticket.service.CityStationService;
import com.hengyu.ticket.service.LineManageService;
import com.hengyu.ticket.service.SaleOrderService;
import com.hengyu.ticket.service.SaleOrderTicketService;
import com.hengyu.ticket.service.ShiftStartService;
import com.hengyu.ticket.service.TicketLineService;
import com.hengyu.ticket.service.TicketStoreService;
import com.hengyu.ticket.util.APIStatus;
import com.hengyu.ticket.util.DateUtil;
import com.hengyu.ticket.util.NumberCreate;
import com.hengyu.ticket.util.RequestTool;

@Controller
public class APIShiftStartControl {

	@Autowired
	private ShiftStartService shiftstartService;
	@Autowired
	private TicketStoreService ticketstoreService;
	@Autowired
	private SaleOrderService saleorderService;
	@Autowired
	private LineManageService linemanageService;
	@Autowired
	private SaleOrderTicketService saleorderticketService;
	@Autowired
	private CityStationService citystationService;
	@Autowired
	private AdminLineService adminlineService;
	@Autowired
	private TicketLineService ticketlineService;

	// 查询班次--首页，无条件
	@RequestMapping(value = "/api/station/shiftStartList", method = RequestMethod.POST)
	@ResponseBody
	public String shiftStartList(HttpServletRequest request) throws Exception {
		// Admin admin = (Admin) request.getSession().getAttribute("admin");
		Map<String, Object> amap = (Map<String, Object>) request.getAttribute("amap");
		Map<String, Object> a = new HashMap<String, Object>();
		Integer pageno = RequestTool.getInt(request, "pageno", 1);
		Integer pagesize = RequestTool.getInt(request, "pagesize", 10);
		String stid = request.getParameter("stid");
		String userid = amap.get("userid").toString();
		a.put("startOfPage", (pageno - 1) * pagesize);
		a.put("pageSize", pagesize);
		a.put("ridedate", DateUtil.getCurrentDateString());// 出行日期
		a.put("currstationid", stid);// 站务所属站点
		a.put("userid", userid);
		a.put("beforstart", DateUtil.formatHHmm(DateUtil.StringToDatetime(DateUtil.MinuteAdd(-30))));
		// a.put("starriveid", RequestTool.getString(request, "starriveid"));
		// 获取记录条数
		Long totalcount = shiftstartService.totalCount(a);
		List ssls = shiftstartService.getShiftStartByDate(a);
		ArrayList als = new ArrayList();
		for (int i = 0; i < ssls.size(); i++) {
			Map<String, Object> na = new HashMap<String, Object>();
			ShiftStart o = (ShiftStart) ssls.get(i);
			na.put("id", o.getId());
			na.put("lmid", o.getLmid());
			na.put("linename", o.getLinename());
			na.put("shiftcode", o.getShiftcode());
			na.put("starttime", o.getPunctualstart());
			// 查余票 按lineid,shiftcode,ticketdate
			Map<String, Object> b = new HashMap<String, Object>();
			b.put("lmid", o.getLmid());
			b.put("shiftcode", o.getShiftcode());
			b.put("ticketdate", DateUtil.getCurrentDateString());
			b.put("stid", stid);
			List stls = ticketstoreService.getTicketStoreByLST(b);
			TicketStore st = new TicketStore();
			if (stls.size() > 0) {
				st = (TicketStore) stls.get(0);
			} else {
				st = null;
			}
			if (st != null) {
				na.put("balancequantity", st.getBalanceQuantity());
			} else {
				na.put("balancequantity", 0);
			}

			na.put("originstid", o.getOriginstid());
			na.put("originstname", o.getOriginstname());
			na.put("starriveid", o.getStarriveid());
			na.put("starrivename", o.getStarrivename());
			// na.put("currstationname", o.getCurrstationname());
			na.put("originstarttime", o.getOriginstarttime());
			na.put("platenum", o.getPlatenum());
			na.put("startmemo", o.getStartmemo());
			// 查本站已售 ，按ridedate,lineid,shiftnum,统计sale_order的quantity
			Long soldquantity = saleorderticketService.getSoldQuantity(b);
			// Long soldquantity = saleorderService.getSoldQuantity(b);

			if (soldquantity == null) {
				na.put("sold", 0);
			} else {
				na.put("sold", soldquantity);
			}
			na.put("isstart", o.getIsstart());
			na.put("isstartname", o.getIsstartname());
			na.put("iscancel", o.getIscancel());
			na.put("iscancelname", o.getIscancelname());
			als.add(na);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		result.put("data", als);
		result.put("totalcount", totalcount);
		return JSON.toJSONString(result);
	}

	// 查询班次-（临时班次搜索）
	@RequestMapping(value = "/api/station/searchShiftStartList", method = RequestMethod.POST)
	@ResponseBody
	public String searchShiftStartList(HttpServletRequest request) throws Exception {
		// Admin admin = (Admin) request.getSession().getAttribute("admin");
		// Map<String,Object> amap = (Map<String,Object>)
		// request.getAttribute("amap");
		Map<String, Object> a = new HashMap<String, Object>();
		Integer pageno = RequestTool.getInt(request, "pageno", 1);
		Integer pagesize = RequestTool.getInt(request, "pagesize", 10);
		String stid = request.getParameter("stid");
		a.put("startOfPage", (pageno - 1) * pagesize);
		a.put("pageSize", pagesize);
		a.put("ridedate", DateUtil.getCurrentDateString());// 出行日期
		a.put("currstationid", stid);// 站务所属站点
		a.put("keywords", request.getParameter("keywords"));
		a.put("istemp", request.getParameter("istemp"));
		// a.put("starriveid", RequestTool.getString(request, "starriveid"));
		// 获取记录条数
		Long totalcount = shiftstartService.totalCountBySearch(a);
		List ssls = shiftstartService.getShiftStartBySearch(a);
		ArrayList als = new ArrayList();
		for (int i = 0; i < ssls.size(); i++) {
			Map<String, Object> na = new HashMap<String, Object>();
			ShiftStart o = (ShiftStart) ssls.get(i);
			na.put("id", o.getId());
			na.put("lmid", o.getLmid());
			na.put("linename", o.getLinename());
			na.put("shiftcode", o.getShiftcode());
			na.put("starttime", o.getPunctualstart());
			// 查余票 按lineid,shiftcode,ticketdate
			Map<String, Object> b = new HashMap<String, Object>();
			b.put("lmid", o.getLmid());
			b.put("shiftcode", o.getShiftcode());
			b.put("ticketdate", DateUtil.getCurrentDateString());
			b.put("stid", stid);
			List stls = ticketstoreService.getTicketStoreByLST(b);
			TicketStore st = new TicketStore();
			if (stls.size() > 0) {
				st = (TicketStore) stls.get(0);
			} else {
				st = null;
			}
			if (st != null) {
				na.put("balancequantity", st.getBalanceQuantity());
			} else {
				na.put("balancequantity", 0);
			}
			na.put("originstid", o.getOriginstid());
			na.put("originstname", o.getOriginstname());
			na.put("starriveid", o.getStarriveid());
			na.put("starrivename", o.getStarrivename());
			na.put("originstarttime", o.getOriginstarttime());
			na.put("currstationname", o.getCurrstationname());
			na.put("platenum", o.getPlatenum());
			na.put("startmemo", o.getStartmemo());
			// 查本站已售 ，按ridedate,lineid,shiftnum,统计sale_order的quantity
			Long soldquantity = saleorderticketService.getSoldQuantity(b);
			// Long soldquantity = saleorderService.getSoldQuantity(b);
			if (soldquantity == null) {
				na.put("sold", 0);
			} else {
				na.put("sold", soldquantity);
			}
			na.put("isstart", o.getIsstart());
			na.put("isstartname", o.getIsstartname());
			na.put("istemp", o.getIsTemp());
			als.add(na);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		result.put("data", als);
		result.put("totalcount", totalcount);
		return JSON.toJSONString(result);
	}

	// 查询班次-（正常班次搜索）
	@RequestMapping(value = "/api/station/searchNormalShiftStartList", method = RequestMethod.POST)
	@ResponseBody
	public String searchNormalShiftStartList(HttpServletRequest request) throws Exception {
		// Admin admin = (Admin) request.getSession().getAttribute("admin");
		Map<String, Object> amap = (Map<String, Object>) request.getAttribute("amap");
		Map<String, Object> a = new HashMap<String, Object>();
		Integer pageno = RequestTool.getInt(request, "pageno", 1);
		Integer pagesize = RequestTool.getInt(request, "pagesize", 10);
		String stid = request.getParameter("stid");
		String userid = amap.get("userid").toString();
		a.put("startOfPage", (pageno - 1) * pagesize);
		a.put("pageSize", pagesize);
		a.put("ridedate", DateUtil.getCurrentDateString());// 出行日期
		a.put("currstationid", stid);// 站务所属站点
		a.put("keywords", request.getParameter("keywords"));
		a.put("istemp", request.getParameter("istemp"));
		a.put("userid", userid);
		// a.put("starriveid", RequestTool.getString(request, "starriveid"));
		// 获取记录条数
		Long totalcount = shiftstartService.totalCountByNormalSearch(a);
		List ssls = shiftstartService.getNormalShiftStartBySearch(a);
		ArrayList als = new ArrayList();
		for (int i = 0; i < ssls.size(); i++) {
			Map<String, Object> na = new HashMap<String, Object>();
			ShiftStart o = (ShiftStart) ssls.get(i);
			na.put("id", o.getId());
			na.put("lmid", o.getLmid());
			na.put("linename", o.getLinename());
			na.put("shiftcode", o.getShiftcode());
			na.put("starttime", o.getPunctualstart());
			// 查余票 按lineid,shiftcode,ticketdate
			Map<String, Object> b = new HashMap<String, Object>();
			b.put("lmid", o.getLmid());
			b.put("shiftcode", o.getShiftcode());
			b.put("ticketdate", DateUtil.getCurrentDateString());
			b.put("stid", stid);
			List stls = ticketstoreService.getTicketStoreByLST(b);
			TicketStore st = new TicketStore();
			if (stls.size() > 0) {
				st = (TicketStore) stls.get(0);
			} else {
				st = null;
			}
			if (st != null) {
				na.put("balancequantity", st.getBalanceQuantity());
			} else {
				na.put("balancequantity", 0);
			}
			na.put("originstid", o.getOriginstid());
			na.put("originstname", o.getOriginstname());
			na.put("starriveid", o.getStarriveid());
			na.put("starrivename", o.getStarrivename());
			na.put("originstarttime", o.getOriginstarttime());
			na.put("currstationname", o.getCurrstationname());
			na.put("platenum", o.getPlatenum());
			na.put("startmemo", o.getStartmemo());
			// 查本站已售 ，按ridedate,lineid,shiftnum,统计sale_order的quantity
			Long soldquantity = saleorderticketService.getSoldQuantity(b);
			// Long soldquantity = saleorderService.getSoldQuantity(b);
			if (soldquantity == null) {
				na.put("sold", 0);
			} else {
				na.put("sold", soldquantity);
			}
			na.put("isstart", o.getIsstart());
			na.put("isstartname", o.getIsstartname());
			na.put("istemp", o.getIsTemp());
			na.put("iscancel", o.getIscancel());
			na.put("iscancelname", o.getIscancelname());
			als.add(na);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		result.put("data", als);
		result.put("totalcount", totalcount);
		return JSON.toJSONString(result);
	}

	// 根据班次，当前站找ID，默认当前日期
	@RequestMapping(value = "/api/station/getShiftStartID", method = RequestMethod.POST)
	@ResponseBody
	public String getShiftStartID(HttpServletRequest request) throws Exception {
		// Admin admin = (Admin) request.getSession().getAttribute("admin");
		String shiftcode = request.getParameter("shiftcode");
		String currstationid = request.getParameter("currstationid");
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("shiftcode", shiftcode.substring(0, 5));
		a.put("currstationid", currstationid);
		a.put("ridedate", DateUtil.getCurrentDateString());
		ShiftStart ahf = null;
		// 如果是临时班次（最后一个字母为Z），不要当前站，查询班次，istemp=1
		String findthree = shiftcode.substring(2, 3);
		if (findthree.equals("Z")) {
			ahf = shiftstartService.getShiftStartByTemp(a);
		} else {
			ahf = shiftstartService.getShiftStartByCRS(a);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		if (ahf != null) {
			result.put("status", APIStatus.SUCCESS_STATUS);
			result.put("info", APIStatus.SUCCESS_INFO);
			result.put("data", ahf);
		} else {
			result.put("status", "09");
			result.put("info", "没有找到数据");
		}
		return JSON.toJSONString(result);
	}

	// 班次详情
	@RequestMapping(value = "/api/station/shiftStartDetail", method = RequestMethod.POST)
	@ResponseBody
	public String shiftStartDetail(HttpServletRequest request) throws Exception {
		// Admin admin = (Admin) request.getSession().getAttribute("admin");
		String id = request.getParameter("id");
		String stid = request.getParameter("stid");
		ShiftStart ss = shiftstartService.find(id);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id", ss.getId());
		result.put("lmid", ss.getLmid());
		result.put("linename", ss.getLinename());
		result.put("platenum", ss.getPlatenum());
		result.put("driverid", ss.getDriverid());
		result.put("drivername", ss.getDrivername());
		result.put("driveridii", ss.getDriveridii());
		result.put("drivernameii", ss.getDrivernameii());
		result.put("nuclearload", ss.getNuclearload());
		result.put("punctualstart", ss.getPunctualstart());
		result.put("originstname", ss.getOriginstname());
		result.put("currstationname", ss.getCurrstationname());
		result.put("starrivename", ss.getStarrivename());
		result.put("shiftcode", ss.getShiftcode());
		result.put("allticketnum", ss.getAllticketnum());
		result.put("halfticketnum", ss.getHalfticketnum());
		result.put("freeticketnum", ss.getFreeticketnum());
		result.put("consignquantity", ss.getConsignQuantity());
		result.put("consignsum", ss.getConsignSum());
		result.put("passengerquantity", ss.getPassengerQuantity());
		result.put("passengersum", ss.getPassengerSum());
		result.put("iscancel", ss.getIscancel());
		result.put("iscancelname", ss.getIscancelname());

		// 根据班次号取票价
		Map<String, Object> tm = new HashMap<String, Object>();
		tm.put("shiftcode", ss.getShiftcode());
		tm.put("ticketdate", DateUtil.getCurrentDateString());
		List tlls = ticketlineService.getTicketLineByShiftCode(tm);
		result.put("tlls", tlls);

		// 根据lmid找线路司机数量
		int driverquantity = 1;
		LineManage lm = linemanageService.find(ss.getLmid());
		if (lm != null) {
			driverquantity = lm.getDriverquantity();
		}
		result.put("driverquantity", driverquantity);

		// 查已售 ，按ridedate,lineid,shiftnum,统计sale_order的quantity
		Map<String, Object> c = new HashMap<String, Object>();
		c.put("ridedate", DateUtil.getCurrentDateString());
		c.put("lmid", ss.getLmid());
		c.put("shiftcode", ss.getShiftcode());
		c.put("ststartid", stid);
		c.put("ticketdate", DateUtil.getCurrentDateString());

		List stls = ticketstoreService.getTicketStoreByLST(c);
		TicketStore st = new TicketStore();
		if (stls.size() > 0) {
			st = (TicketStore) stls.get(0);
		} else {
			st = null;
		}
		if (st != null) {
			result.put("balancequantity", st.getBalanceQuantity());
		} else {
			result.put("balancequantity", 0);
		}
		result.put("isstart", ss.getIsstart());
		result.put("isstartname", ss.getIsstartname());
		result.put("memo", ss.getMemo());

		// 途经站,手工加入起点站和终点站
		List<Map> wayls = new ArrayList<Map>();
		Map<String, Object> s = new LinkedHashMap();
		s.put("stid", ss.getOriginstid());
		s.put("stname", ss.getOriginstname());
		s.put("sortnum", 0);
		// wayls.add(s);
		wayls.addAll(linemanageService.getWayStation(ss.getLmid()));

		wayls.add(0, s);
		// wayls.add(e);
		ArrayList awayls = new ArrayList();
		int allpeople = 0;
		int allpackage = 0;// 托运行李、乘客行李
		String strmemo = "";
		for (int i = 0; i < wayls.size(); i++) {
			Map<String, Object> aw = new HashMap<String, Object>();
			aw.put("stid", wayls.get(i).get("stid"));
			aw.put("stname", wayls.get(i).get("stname"));
			aw.put("sortnum", wayls.get(i).get("sortnum"));
			// 找全票，半票，免票，查找条件：currstationid,ridedate,shiftcode
			Map<String, Object> fss = new HashMap<String, Object>();
			fss.put("currstationid", aw.get("stid"));
			fss.put("ridedate", DateUtil.getCurrentDateString());
			fss.put("shiftcode", ss.getShiftcode());
			ShiftStart ahf = shiftstartService.getShiftStartByCRS(fss);
			if (ahf != null) {
				aw.put("allticketnum", ahf.getAllticketnum());
				aw.put("halfticketnum", ahf.getHalfticketnum());
				aw.put("freeticketnum", ahf.getFreeticketnum());
				aw.put("consignquantity", ahf.getConsignQuantity());
				aw.put("passengerquantity", ahf.getPassengerQuantity());
				aw.put("memo", ahf.getMemo());
				if (ahf.getMemo() != null && !ahf.getMemo().equals("null") && !ahf.getMemo().equals("")) {
					strmemo += aw.get("stname") + "：" + ahf.getMemo() + "\n";
				}
				// aw.put("memo", ahf.getMemo());
				aw.put("isstart", ahf.getIsstart());
				allpeople += ahf.getCurrpeople();// 累计车上人数
				allpackage += ahf.getConsignQuantity() + ahf.getPassengerQuantity();
			} else {
				aw.put("allticketnum", 0);
				aw.put("halfticketnum", 0);
				aw.put("freeticketnum", 0);
				aw.put("consignquantity", 0);
				aw.put("passengerquantity", 0);
				aw.put("memo", "");
				aw.put("isstart", 0);
				allpeople += 0;
				allpackage += 0;
			}
			awayls.add(aw);
		}

		result.put("memo", strmemo);
		result.put("shiftstart", awayls);
		// 车上总人数
		result.put("allpeople", allpeople);
		// 总行李数
		result.put("allpackage", allpackage);

		// 统计未取票人数
		Long notake = saleorderService.getNoTakeQuantity(c);
		if (notake == null) {
			result.put("notake", 0);
		} else {
			result.put("notake", notake);
		}

		// 当前站销售总票数
		int saleallticket = 0;
		// 订单购票人员列表
		List tls = saleorderService.getAlreadyTake(c);
		ArrayList atls = new ArrayList();
		for (int a = 0; a < tls.size(); a++) {
			Map<String, Object> at = new HashMap<String, Object>();
			SaleOrder o = (SaleOrder) tls.get(a);

			Map<String, Object> b = new HashMap<String, Object>();
			b.put("ticketcode", o.getTicketCode());
			int ticketcount = saleorderticketService.getValidateTicketCountBySOID(b);

			at.put("id", o.getId());
			// at.put("statusname", o.getStatusName());
			at.put("lName", o.getLName());
			at.put("lMobile", o.getLMobile());
			at.put("sTArriveName", o.getStarrivename());
			at.put("quantity", ticketcount);
			at.put("iDCode", o.getIDCode());
			at.put("status", o.getStatus());
			at.put("statusName", o.getStatusName());

			saleallticket += ticketcount;
			atls.add(at);
		}

		// 当前站销售总票数
		result.put("saleallticket", saleallticket);
		// 获取当前站未验票信息，条件：班次，出发站（当前站），出行日期
		long notrain = saleorderticketService.getNoOnTrain(c);
		result.put("notrain", notrain);

		result.put("ordermsg", atls);
		result.put("istemp", ss.getIsTemp());
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 发车--正常班次
	@RequestMapping(value = "/api/station/shiftStart", method = RequestMethod.POST)
	@ResponseBody
	public String shiftStart(HttpServletRequest request) throws Exception {
		// Admin admin = (Admin) request.getSession().getAttribute("admin");
		Map<String, Object> amap = (Map<String, Object>) request.getAttribute("amap");
		String userid = amap.get("userid").toString();
		String realname = amap.get("realname").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> a = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String stid = request.getParameter("stid");
		a.put("id", id);
		a.put("platenum", request.getParameter("platenum"));
		a.put("driverid", request.getParameter("driverid"));
		a.put("drivername", request.getParameter("drivername"));
		a.put("driveridii", request.getParameter("driveridii"));
		a.put("drivernameii", request.getParameter("drivernameii"));
		a.put("nuclearload", request.getParameter("nuclearload"));
		a.put("currpeople", request.getParameter("currpeople"));
		a.put("actualstart", DateUtil.getCurrentHourMunite());
		a.put("memo", request.getParameter("memo"));
		a.put("allticketnum", request.getParameter("allticketnum"));
		a.put("halfticketnum", request.getParameter("halfticketnum"));
		a.put("freeticketnum", request.getParameter("freeticketnum"));
		a.put("consignquantity", request.getParameter("consignquantity"));
		a.put("consignsum", request.getParameter("consignsum"));
		a.put("passengerquantity", request.getParameter("passengerquantity"));
		a.put("passengersum", request.getParameter("passengersum"));
		a.put("isstart", request.getParameter("isstart"));
		a.put("isstartname", request.getParameter("isstartname"));
		a.put("makeid", userid);
		a.put("makename", realname);
		a.put("makedate", DateUtil.getCurrentDateTime());
		ShiftStart ss = shiftstartService.find(id);
		// if (stid.equals(ss.getOriginstid())) {// 始发站发车
		int s = shiftstartService.toStart(a);
		// 补齐该班次的其他发车站的信息，车牌，司机，核载,条件：班次号,线路ID,出行日期
		Map<String, Object> c = new HashMap<String, Object>();
		c.put("platenum", a.get("platenum"));
		c.put("driverid", a.get("driverid"));
		c.put("drivername", a.get("drivername"));
		c.put("driveridii", a.get("driveridii"));
		c.put("drivernameii", a.get("drivernameii"));
		c.put("nuclearload", a.get("nuclearload"));
		c.put("shiftcode", ss.getShiftcode());
		c.put("lmid", ss.getLmid());
		c.put("ridedate", ss.getRidedate());
		c.put("startmemo", ss.getCurrstationname() + "已发车");
		int comp = shiftstartService.completeShiftStart(c);

		// } else {// 途经站发车
		// int s = shiftstartService.toStart(a);
		// }
		if (request.getParameter("isstart").equals("1")) {
			// 修改发车备注
			int sm = shiftstartService.updStartMemo(c);
		}

		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 修改班次
	@RequestMapping(value = "/api/station/modifyShiftStart", method = RequestMethod.POST)
	@ResponseBody
	public String modifyShiftStart(HttpServletRequest request) throws Exception {
		// Admin admin = (Admin) request.getSession().getAttribute("admin");
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> a = new HashMap<String, Object>();
		String id = request.getParameter("id");
		// String stid = request.getParameter("stid");
		a.put("id", id);
		a.put("currpeople", request.getParameter("currpeople"));
		a.put("allticketnum", request.getParameter("allticketnum"));
		a.put("halfticketnum", request.getParameter("halfticketnum"));
		a.put("freeticketnum", request.getParameter("freeticketnum"));
		a.put("consignquantity", request.getParameter("consignquantity"));
		a.put("consignsum", request.getParameter("consignsum"));
		a.put("passengerquantity", request.getParameter("passengerquantity"));
		a.put("passengersum", request.getParameter("passengersum"));
		int mod = shiftstartService.modifyShiftStart(a);

		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 生成临时班次编码
	@RequestMapping(value = "/api/station/generTempShiftCode", method = RequestMethod.POST)
	@ResponseBody
	public String generTempShiftCode(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String currstationid = request.getParameter("currstationid");
		String cityarriveid = request.getParameter("cityarriveid");
		String starriveid = request.getParameter("starriveid");
		if (currstationid == null || cityarriveid == null || starriveid == null) {
			result.put("status", "09");
			result.put("info", "参数错误");
			return JSON.toJSONString(result);
		}
		// 根据当前站找出发城市
		CityStation cs = citystationService.find(currstationid);
		Map<String, Object> a = new HashMap<String, Object>();
		// a.put("ststartid", currstationid);
		a.put("citystartid", cs.getParentid());
		a.put("cityarriveid", cityarriveid);
		a.put("starriveid", starriveid);
		LineManage lm = linemanageService.getLineidBySCS(a);
		// System.out.println("lm=======" + lm);
		String tmplineid = "";
		if (lm == null || lm.getLineid() == null || lm.getLineid().equals("")) {
			tmplineid = "ACZ";
		} else {
			tmplineid = lm.getLineid();

		}
		String shiftcode = tmplineid.substring(0, 2) + "Z";
		Map<String, Object> aa = new HashMap<String, Object>();
		aa.put("threecode", shiftcode);
		Long lc = shiftstartService.getTempShiftCodeCount(aa);
		shiftcode = shiftcode + NumberCreate.createNumber((lc + 1), 2);
		result.put("tempcode", shiftcode);
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);

		return JSON.toJSONString(result);
	}

	// 发车--临时加班
	@RequestMapping(value = "/api/station/tempShiftStart", method = RequestMethod.POST)
	@ResponseBody
	public String tempShiftStart(HttpServletRequest request) throws Exception {
		// Admin admin = (Admin) request.getSession().getAttribute("admin");
		Map<String, Object> amap = (Map<String, Object>) request.getAttribute("amap");
		Map<String, Object> result = new HashMap<String, Object>();
		ShiftStart ss = new ShiftStart();
		String id = request.getParameter("id");
		String stid = request.getParameter("stid");
		String stname = request.getParameter("stname");
		ss.setIsTemp(RequestTool.getInt(request, "istemp", 0));
		ss.setShiftcode(request.getParameter("shiftcode"));
		ss.setRidedate(DateUtil.getCurrentDateString());
		ss.setLmid(0);// Integer.parseInt(request.getParameter("lmid")));

		String currstationid = request.getParameter("currstationid");
		ss.setStarriveid(request.getParameter("starriveid"));
		if (ss.getIsTemp() == 1) {
			// 根据站点获取父ID，再根据父ID获取城市名称
			CityStation cs = citystationService.find(currstationid);
			CityStation ps = citystationService.find(cs.getParentid());
			// 根据到达站点查城市
			CityStation as = citystationService.find(ss.getStarriveid());
			CityStation ds = citystationService.find(as.getParentid());

			ss.setLinename(ps.getCityname() + "-" + ds.getCityname());
		} else {
			ss.setLinename(request.getParameter("linename"));
		}
		ss.setOriginstid(request.getParameter("originstid"));
		ss.setOriginstname(request.getParameter("originstname"));
		ss.setStarrivename(request.getParameter("starrivename"));
		ss.setOriginstarttime(request.getParameter("originstarttime"));
		ss.setPlatenum(request.getParameter("platenum"));
		ss.setDriverid(request.getParameter("driverid"));
		ss.setDrivername(request.getParameter("drivername"));
		ss.setDriveridii(request.getParameter("driveridii"));
		ss.setDrivernameii(request.getParameter("drivernameii"));
		ss.setNuclearload(RequestTool.getInt(request, "nuclearload", 45));
		ss.setCurrpeople(RequestTool.getInt(request, "currpeople", 0));
		ss.setCurrstationid(request.getParameter("currstationid"));
		ss.setCurrstationname(request.getParameter("currstationname"));
		ss.setPunctualstart(request.getParameter("punctualstart"));
		ss.setActualstart(request.getParameter("actualstart"));
		ss.setIsstart(RequestTool.getInt(request, "isstart", 0));
		ss.setIsstartname(request.getParameter("isstartname"));
		ss.setMemo(request.getParameter("memo"));
		ss.setAllticketnum(RequestTool.getInt(request, "allticketnum", 0));
		ss.setHalfticketnum(RequestTool.getInt(request, "halfticketnum", 0));
		ss.setFreeticketnum(RequestTool.getInt(request, "freeticketnum", 0));
		ss.setConsignQuantity(RequestTool.getInt(request, "consignquantity", 0));
		ss.setConsignSum(RequestTool.getBigDecimal(request, "consignsum", new BigDecimal("0")));
		ss.setPassengerQuantity(RequestTool.getInt(request, "passengerquantity", 0));
		ss.setPassengerSum(RequestTool.getBigDecimal(request, "passengersum", new BigDecimal("0")));
		ss.setIscancel(0);
		ss.setIscancelname("");
		ss.setMakeid(amap.get("userid").toString());
		ss.setMakename(amap.get("realname").toString());
		ss.setMakedate(DateUtil.getCurrentDateTime());

		if (ss.getIsTemp() == 1) {// 起点新增，并发车
			// 临时加班,当istemp=1时，增加始发站发车，
			int s = shiftstartService.save(ss);
			if (s == 1) {
				result.put("id", ss.getId());
				result.put("status", APIStatus.SUCCESS_STATUS);
				result.put("info", APIStatus.SUCCESS_INFO);
			} else if (s == 0) {
				result.put("status", APIStatus.SYSTEM_ERROR_STATUS);
				result.put("info", APIStatus.SYSTEM_ERROR_INFO);
			}
		} else if (ss.getIsTemp() == 2) {// 临时非起点发车
			ShiftStart tmpss = shiftstartService.find(id);
			tmpss.setPunctualstart(DateUtil.getCurrentHourMunite());
			tmpss.setActualstart(String.valueOf(DateUtil.getCurrentHourMunite()));
			tmpss.setMakeid(amap.get("userid").toString());
			tmpss.setMakename(amap.get("realname").toString());
			tmpss.setMakedate(DateUtil.getCurrentDateTime());
			tmpss.setPlatenum(ss.getPlatenum());
			tmpss.setDriverid(ss.getDriverid());
			tmpss.setDrivername(ss.getDrivername());
			tmpss.setDriveridii(ss.getDriveridii());
			tmpss.setDrivernameii(ss.getDrivernameii());
			tmpss.setNuclearload(ss.getNuclearload());
			tmpss.setCurrpeople(ss.getCurrpeople());
			tmpss.setCurrstationid(stid);
			tmpss.setCurrstationname(stname);
			tmpss.setMemo(ss.getMemo());
			tmpss.setAllticketnum(ss.getAllticketnum());
			tmpss.setHalfticketnum(ss.getHalfticketnum());
			tmpss.setFreeticketnum(ss.getFreeticketnum());
			tmpss.setConsignQuantity(ss.getConsignQuantity());
			tmpss.setConsignSum(ss.getConsignSum());
			tmpss.setPassengerQuantity(ss.getPassengerQuantity());
			tmpss.setPassengerSum(ss.getPassengerSum());
			tmpss.setIsTemp(ss.getIsTemp());
			tmpss.setIscancel(0);
			tmpss.setIscancelname("");
			int ts = shiftstartService.save(tmpss);
			if (ts == 1) {
				result.put("id", tmpss.getId());
				result.put("status", APIStatus.SUCCESS_STATUS);
				result.put("info", APIStatus.SUCCESS_INFO);
			} else if (ts == 0) {
				result.put("status", APIStatus.SYSTEM_ERROR_STATUS);
				result.put("info", APIStatus.SYSTEM_ERROR_INFO);
			}
		}
		// 修改发车备注
		if (RequestTool.getInt(request, "isstart", 0) == 1) {
			Map<String, Object> c = new HashMap<String, Object>();
			c.put("shiftcode", ss.getShiftcode());
			c.put("ridedate", ss.getRidedate());
			c.put("startmemo", ss.getCurrstationname() + "已发车");
			int sm = shiftstartService.updStartMemo(c);
		}
		return JSON.toJSONString(result);
	}

	// 临时班次详情
	@RequestMapping(value = "/api/station/tempShiftStartDetail", method = RequestMethod.POST)
	@ResponseBody
	public String tempShiftStartDetail(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id");
		ShiftStart ss = shiftstartService.find(id);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id", ss.getId());
		result.put("lmid", ss.getLmid());
		result.put("linename", ss.getLinename());
		result.put("shiftcode", ss.getShiftcode());
		result.put("ridedate", ss.getRidedate());
		result.put("originstname", ss.getOriginstname());
		result.put("starrivename", ss.getStarrivename());
		result.put("originstarttime", ss.getOriginstarttime());
		result.put("currstationname", ss.getCurrstationname());
		result.put("platenum", ss.getPlatenum());
		result.put("driverid", ss.getDriverid());
		result.put("drivername", ss.getDrivername());
		result.put("driveridii", ss.getDriveridii());
		result.put("drivernameii", ss.getDrivernameii());
		result.put("nuclearload", ss.getNuclearload());
		result.put("punctualstart", ss.getPunctualstart());
		result.put("allticketnum", ss.getAllticketnum());
		result.put("halfticketnum", ss.getHalfticketnum());
		result.put("freeticketnum", ss.getFreeticketnum());
		result.put("consignquantity", ss.getConsignQuantity());
		result.put("consignsum", ss.getConsignSum());
		result.put("passengerquantity", ss.getPassengerQuantity());
		result.put("passengersum", ss.getPassengerSum());
		// 根据lmid找线路司机数量
		int driverquantity = 2;
		LineManage lm = linemanageService.find(ss.getLmid());
		if (lm != null) {
			driverquantity = lm.getDriverquantity();
		}
		result.put("driverquantity", driverquantity);

		// 按shiftcode,ridedate,lineid查找所有班次
		Map<String, Object> c = new HashMap<String, Object>();
		c.put("ridedate", DateUtil.getCurrentDateString());
		c.put("lmid", ss.getLmid());
		c.put("shiftcode", ss.getShiftcode());
		List tmpshiftstart = shiftstartService.getShiftStartBySRL(c);
		String strmemo = "";
		Integer allpeople = 0;
		Integer allpackage = 0;
		ArrayList awayls = new ArrayList();
		for (int i = 0; i < tmpshiftstart.size(); i++) {
			Map<String, Object> aw = new HashMap<String, Object>();
			ShiftStart ahf = (ShiftStart) tmpshiftstart.get(i);
			aw.put("stid", ahf.getCurrstationid());
			aw.put("stname", ahf.getCurrstationname());
			aw.put("allticketnum", ahf.getAllticketnum());
			aw.put("halfticketnum", ahf.getHalfticketnum());
			aw.put("freeticketnum", ahf.getFreeticketnum());
			aw.put("consignquantity", ahf.getConsignQuantity());
			aw.put("passengerquantity", ahf.getPassengerQuantity());
			if (ahf.getMemo() != null && !ahf.getMemo().equals("null") && !ahf.getMemo().equals("")) {
				strmemo += aw.get("stname") + "：" + ahf.getMemo() + "\n";
			}
			// aw.put("memo", ahf.getMemo());
			aw.put("isstart", ahf.getIsstart());
			allpeople += ahf.getCurrpeople();// 累计车上人数
			allpackage += ahf.getConsignQuantity() + ahf.getPassengerQuantity();
			awayls.add(aw);
		}

		// 查已售 ，按ridedate,lineid,shiftnum,统计sale_order的quantity
		result.put("shiftstart", awayls);
		result.put("memo", strmemo);

		// 车上总人数
		result.put("allpeople", allpeople);
		// 总行李
		result.put("allpackage", allpackage);
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 取消、启用班次
	@RequestMapping(value = "/api/station/cancelShiftStart", method = RequestMethod.POST)
	@ResponseBody
	public String cancelShiftStart(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String shiftcode = request.getParameter("shiftcode");
		Integer iscancel = RequestTool.getInt(request, "iscancel", 1);
		String iscancelname = request.getParameter("iscancelname");
		// String memo = request.getParameter("memo");
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("shiftcode", shiftcode);
		a.put("iscancel", iscancel);
		a.put("iscancelname", iscancelname);
		a.put("ridedate", DateUtil.getCurrentDateString());
		// a.put("memo", memo);
		int cancel = shiftstartService.cancelShiftStart(a);
		Integer tmpiscancel = 0;
		String tmpiscancelname = "";
		if (iscancel == 2) {
			tmpiscancel = 1;
			tmpiscancelname = "已取消";
			Map<String, Object> c = new HashMap<String, Object>();
			c.put("isapprove", 2);
			c.put("shiftcode", shiftcode);
			c.put("ticketdate", DateUtil.getCurrentDateString());
			c.put("isrelease", 0);
			ticketstoreService.cancelTicketStore(c);
		} else {
			tmpiscancel = 0;
			tmpiscancelname = "已恢复";
			Map<String, Object> c = new HashMap<String, Object>();
			c.put("isapprove", 1);
			c.put("shiftcode", shiftcode);
			c.put("ticketdate", DateUtil.getCurrentDateString());
			c.put("isrelease", 1);
			ticketstoreService.cancelTicketStore(c);
		}
		Map<String, Object> b = new HashMap<String, Object>();
		b.put("shiftcode", shiftcode);
		b.put("iscancel", tmpiscancel);
		b.put("iscancelname", tmpiscancelname);
		b.put("ridedate", DateUtil.getCurrentDateString());
		int changeiscancel = shiftstartService.changeIsCancel(b);
		if (cancel > 0) {
			result.put("status", APIStatus.SUCCESS_STATUS);
			result.put("info", APIStatus.SUCCESS_INFO);
		} else {
			result.put("status", APIStatus.SYSTEM_ERROR_STATUS);
			result.put("info", APIStatus.SYSTEM_ERROR_INFO);
		}
		return JSON.toJSONString(result);
	}

	// 班次添加备注
	@RequestMapping(value = "/api/station/addShiftStartMemo", method = RequestMethod.POST)
	@ResponseBody
	public String addShiftStartMemo(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String memo = request.getParameter("memo");
		String type = request.getParameter("type");
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("id", id);
		a.put("memo", memo);
		int dealmemo = 0;
		if (type.equals("0")) {
			dealmemo = shiftstartService.addShiftStartMemo(a);
		} else {
			dealmemo = shiftstartService.updShiftStartMemo(a);
		}
		if (dealmemo > 0) {
			result.put("status", APIStatus.SUCCESS_STATUS);
			result.put("info", APIStatus.SUCCESS_INFO);
		} else {
			result.put("status", APIStatus.SYSTEM_ERROR_STATUS);
			result.put("info", APIStatus.SYSTEM_ERROR_INFO);
		}
		return JSON.toJSONString(result);
	}

	// 按司机列表发车班次
	@RequestMapping(value = "/api/station/listShiftStartByDriver", method = RequestMethod.POST)
	@ResponseBody
	public String listShiftStartByDriver(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("driverid", request.getParameter("driverid"));
		a.put("ridedate", DateUtil.getCurrentDateString());
		List lss = shiftstartService.getShiftStartByDriver(a);

		result.put("linedata", lss);
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 按班次号查找各站点的发车信息
	@RequestMapping(value = "/api/station/getShiftStartByShiftCode", method = RequestMethod.POST)
	@ResponseBody
	public String getShiftStartByShiftCode(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		// 查找余票
		Map<String, Object> c = new HashMap<String, Object>();
		c.put("lmid", request.getParameter("lmid"));
		c.put("shiftcode", request.getParameter("shiftcode"));
		c.put("ticketdate", DateUtil.getCurrentDateString());
		c.put("ridedate", DateUtil.getCurrentDateString());
		List stls = ticketstoreService.getTicketStoreByLST(c);
		TicketStore st = new TicketStore();
		if (stls.size() > 0) {
			st = (TicketStore) stls.get(0);
		} else {
			st = null;
		}
		if (st != null) {
			result.put("balancequantity", st.getBalanceQuantity());
		} else {
			result.put("balancequantity", 0d);
		}
		int startstatus = 1;// 0未发，1已发
		// 站点及状态，按班次、日期查，
		List ssls = shiftstartService.getShiftStartBySRL(c);
		ArrayList bld = new ArrayList();
		for (int s = 0; s < ssls.size(); s++) {
			Map<String, Object> ss = new HashMap<String, Object>();
			ShiftStart o = (ShiftStart) ssls.get(s);
			ss.put("id", o.getId());
			ss.put("currstationid", o.getCurrstationid());
			ss.put("currstationname", o.getCurrstationname());
			ss.put("punctualstart", o.getPunctualstart());
			ss.put("isstart", o.getIsstart());
			ss.put("isstartname", o.getIsstartname());
			if (o.getIsstart() == 0) {
				startstatus = 0;
			}
			if (o.getIscancel() == 1) {
				startstatus = 1;
			}
			// 找站点已售
			Map<String, Object> d = new HashMap<String, Object>();
			d.put("lmid", request.getParameter("lmid"));
			d.put("shiftcode", request.getParameter("shiftcode"));
			d.put("ticketdate", DateUtil.getCurrentDateString());
			d.put("stid", o.getCurrstationid());
			long salequantity = saleorderticketService.getSoldQuantity(d);
			ss.put("salequantity", salequantity);
			ss.put("isstart", o.getIsstart());
			ss.put("isstartname", o.getIsstartname());
			ss.put("iscancel", o.getIscancel());
			ss.put("iscancelname", o.getIscancelname());
			bld.add(ss);
		}
		result.put("startstatus", startstatus);
		result.put("stationdata", bld);
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}
	// // 按司机列表发车信息
	// @RequestMapping(value = "/api/station/listShiftStartByDriver", method =
	// RequestMethod.POST)
	// @ResponseBody
	// public String listShiftStartByDriver(HttpServletRequest request) throws
	// Exception {
	// Map<String, Object> result = new HashMap<String, Object>();
	// // 按司机查班次，group by
	// Map<String, Object> a = new HashMap<String, Object>();
	// a.put("driverid", request.getParameter("driverid"));
	// a.put("ridedate", DateUtil.getCurrentDateString());
	// List<Map> lss = shiftstartService.getShiftStartByDriver(a);
	// ArrayList ald = new ArrayList();
	// for (int i = 0; i < lss.size(); i++) {
	// Map<String, Object> aw = new HashMap<String, Object>();
	// aw.put("lmid", lss.get(i).get("lmid"));
	// aw.put("shiftcode", lss.get(i).get("shiftcode"));
	// aw.put("linename", lss.get(i).get("linename"));
	// aw.put("originstarttime", lss.get(i).get("originstarttime"));
	// aw.put("isstart", lss.get(i).get("isstart"));
	// aw.put("isstartname", lss.get(i).get("isstartname"));
	// aw.put("iscancel", lss.get(i).get("iscancel"));
	// aw.put("iscancelname", lss.get(i).get("iscancelname"));
	// // 查找余票
	// Map<String, Object> c = new HashMap<String, Object>();
	// c.put("lmid", aw.get("lmid"));
	// c.put("shiftcode", aw.get("shiftcode"));
	// c.put("ticketdate", DateUtil.getCurrentDateString());
	// c.put("ridedate", DateUtil.getCurrentDateString());
	// List stls = ticketstoreService.getTicketStoreByLST(c);
	// TicketStore st = new TicketStore();
	// if (stls.size() > 0) {
	// st = (TicketStore) stls.get(0);
	// } else {
	// st = null;
	// }
	// if (st != null) {
	// aw.put("balancequantity", st.getBalanceQuantity());
	// } else {
	// aw.put("balancequantity", 0d);
	// }
	// int startstatus = 1;// 0未发，1已发
	// // 站点及状态，按班次、日期查，
	// List ssls = shiftstartService.getShiftStartBySRL(c);
	// ArrayList bld = new ArrayList();
	// for (int s = 0; s < ssls.size(); s++) {
	// Map<String, Object> ss = new HashMap<String, Object>();
	// ShiftStart o = (ShiftStart) ssls.get(s);
	// ss.put("id", o.getId());
	// ss.put("currstationid", o.getCurrstationid());
	// ss.put("currstationname", o.getCurrstationname());
	// ss.put("punctualstart", o.getPunctualstart());
	// ss.put("isstart", o.getIsstart());
	// ss.put("isstartname", o.getIsstartname());
	// if (o.getIsstart() == 0) {
	// startstatus = 0;
	// }
	// if (o.getIscancel() == 1) {
	// startstatus = 1;
	// }
	// // 找站点已售
	// Map<String, Object> d = new HashMap<String, Object>();
	// d.put("lmid", aw.get("lmid"));
	// d.put("shiftcode", aw.get("shiftcode"));
	// d.put("ticketdate", DateUtil.getCurrentDateString());
	// d.put("stid", o.getCurrstationid());
	// long salequantity = saleorderService.getSoldQuantity(d);
	// ss.put("salequantity", saleorderService.getSoldQuantity(d));
	// ss.put("isstart", o.getIsstart());
	// ss.put("isstartname", o.getIsstartname());
	// ss.put("iscancel", o.getIscancel());
	// ss.put("iscancelname", o.getIscancelname());
	// bld.add(ss);
	// }
	// aw.put("startstatus", startstatus);
	// aw.put("stationdata", bld);
	// ald.add(aw);
	// }
	// result.put("linedata", ald);
	// result.put("status", APIStatus.SUCCESS_STATUS);
	// result.put("info", APIStatus.SUCCESS_INFO);
	// return JSON.toJSONString(result);
	// }

	// 线路数据报表
	@RequestMapping(value = "/api/station/lineDataReport", method = RequestMethod.POST)
	@ResponseBody
	public String lineDataReport(HttpServletRequest request) throws Exception {
		Map<String, Object> amap = (Map<String, Object>) request.getAttribute("amap");
		String userid = amap.get("userid").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		// //找出当前用户的线路
		// List alls = adminlineService.getAdminLine(userid);
		String ridedate = request.getParameter("ridedate");
		String currstationid = request.getParameter("currstationid");
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("ridedate", ridedate);
		a.put("currstationid", currstationid);
		a.put("userid", userid);
		if (currstationid == null || currstationid.equals("")) {
			List ls = shiftstartService.getShiftStartDataReportByDriver(a);
			result.put("linedata", ls);
		} else {
			List ls = shiftstartService.getShiftStartDataReport(a);
			result.put("linedata", ls);
		}

		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}
}

package com.hengyu.ticket.api.station;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.CityStation;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.SaleOrder;
import com.hengyu.ticket.entity.Shift;
import com.hengyu.ticket.entity.ShiftStart;
import com.hengyu.ticket.entity.TicketStore;
import com.hengyu.ticket.service.AdminLineService;
import com.hengyu.ticket.service.AdminService;
import com.hengyu.ticket.service.CityStationService;
import com.hengyu.ticket.service.LineManageService;
import com.hengyu.ticket.service.SaleOrderService;
import com.hengyu.ticket.service.SaleOrderTicketService;
import com.hengyu.ticket.service.ShiftService;
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

	@Autowired
	private ShiftService shiftService;
	
	@Autowired
	private AdminService adminService;
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
			na.put("id",o.getId());
			na.put("lmid", o.getLmid());
			if(StringUtils.isEmpty(o.getLinename())){
				Shift shift = shiftService.find(o.getShiftid());
				na.put("linename",shift.getLinename());
			}else{
				na.put("linename", o.getLinename());
			}
			na.put("shiftID", o.getShiftid());
			na.put("starttime", o.getPunctualstart());
			// 查余票 按lineid,shiftcode,ticketdate
			Map<String, Object> b = new HashMap<String, Object>();
			b.put("lmid", o.getLmid());
			//b.put("shiftcode", o.getShiftcode());//换为shiftID
			b.put("shiftID", o.getShiftid());
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
			//查询始发站等信息
			Shift shift = shiftService.find(o.getShiftid());
			na.put("originstid", shift.getOriginstid());//始发站ID没有
			na.put("originstname",shift.getOriginstname());//始发站没有 
			na.put("starriveid", shift.getStarriveid());//目的地ID没有
			na.put("starrivename",shift.getStarrivename());//目的地名称
			// na.put("currstationname", o.getCurrstationname());
			na.put("originstarttime",shift.getOriginstarttime());//始发时间没有
			na.put("shiftcode",shift.getShiftcode());
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
			na.put("id",o.getId());
			na.put("lmid", o.getLmid());
			if(StringUtils.isEmpty(o.getLinename())){
				Shift shift = shiftService.find(o.getShiftid());
				na.put("linename",shift.getLinename());
			}else{
				na.put("linename", o.getLinename());
			}
			na.put("starttime", o.getPunctualstart());
			// 查余票 按lineid,shiftcode,ticketdate
			Map<String, Object> b = new HashMap<String, Object>();
			b.put("lmid", o.getLmid());
			b.put("shiftID",o.getShiftid());
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
			Shift shift = shiftService.find(o.getShiftid());
			na.put("originstid", shift.getOriginstid());//始发站ID没有
			na.put("originstname",shift.getOriginstname());//始发站没有 
			na.put("starriveid", shift.getStarriveid());//目的地ID没有
			na.put("starrivename",shift.getStarrivename());//目的地名称
			na.put("originstarttime",shift.getOriginstarttime());
			na.put("shiftcode",shift.getShiftcode());
			na.put("currstationname", o.getCurrstationname());
			na.put("platenum", o.getPlatenum());
			na.put("startmemo", o.getStartmemo());
			// 查本站已售 ，按ridedate,lineid,shiftnum,统计sale_order的quantity
			b.put("shiftID", shift.getId());
			Long soldquantity = saleorderticketService.getSoldQuantity(b);
			// Long soldquantity = saleorderService.getSoldQuantity(b);
			if (soldquantity == null) {
				na.put("sold", 0);
			} else {
				na.put("sold", soldquantity);
			}
			na.put("isstart", o.getIsstart());
			na.put("isstartname", o.getIsstartname());
			na.put("istemp", o.getIstemp());
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
			na.put("id",o.getId());
			na.put("lmid", o.getLmid());
			if(StringUtils.isEmpty(o.getLinename())){
				Shift shift = shiftService.find(o.getShiftid());
				na.put("linename",shift.getLinename());
			}else{
				na.put("linename", o.getLinename());
			}
			na.put("starttime", o.getPunctualstart());
			// 查余票 按lineid,shiftcode,ticketdate
			Map<String, Object> b = new HashMap<String, Object>();
			b.put("lmid", o.getLmid());
			//b.put("shiftcode", o.getShiftcode());
			b.put("shiftID",o.getShiftid());
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
			Shift shift = shiftService.find(o.getShiftid());
			na.put("originstid", shift.getOriginstid());//始发站ID没有
			na.put("originstname",shift.getOriginstname());//始发站没有 
			na.put("starriveid", shift.getStarriveid());//目的地ID没有
			na.put("starrivename",shift.getStarrivename());//目的地名称
			na.put("originstarttime",shift.getOriginstarttime());
			na.put("shiftcode",shift.getShiftcode());
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
			na.put("istemp", o.getIstemp());
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
		//ShiftStart ahf = null;
		// 如果是临时班次（最后一个字母为Z），不要当前站，查询班次，istemp=1
		String findthree = shiftcode.substring(2, 3);
		Map<String,Object> map = new HashMap<>();
		if (findthree.equals("Z")) {
			//ahf = shiftstartService.getShiftStartByTemp(a);
			map = shiftstartService.getMap_ShiftStartByTemp(a);
		} else {
			//ahf = shiftstartService.getShiftStartByCRS(a);
			map = shiftstartService.getMap_ShiftStartByCRS(a);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		if (map != null && map.size()>0) {
			result.put("status", APIStatus.SUCCESS_STATUS);
			result.put("info", APIStatus.SUCCESS_INFO);
			result.put("data", map);
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
		result.put("id",ss.getId());
		result.put("lmid", ss.getLmid());
		Shift shift = shiftService.find(ss.getShiftid());
		if(StringUtils.isEmpty(ss.getLinename())){
			result.put("linename",shift.getLinename());
		}else{
			result.put("linename", ss.getLinename());
		}
	
		result.put("platenum", ss.getPlatenum());
		result.put("driverid", ss.getDriverid());
		result.put("drivername", ss.getDrivername());
		result.put("driveridii", ss.getDriveridii());
		result.put("drivernameii", ss.getDrivernameii());
		result.put("nuclearload", ss.getNuclearload());
		result.put("punctualstart", ss.getPunctualstart());
		result.put("originstname", shift.getOriginstname());
		result.put("currstationname", ss.getCurrstationname());
		result.put("starrivename", shift.getStarrivename());
		//String shiftCode = shiftService.findCodeById(ss.getShiftid());
		result.put("shiftcode",shift.getShiftcode());
		result.put("allticketnum", ss.getAllticketnum());
		result.put("halfticketnum", ss.getHalfticketnum());
		result.put("freeticketnum", ss.getFreeticketnum());
		result.put("consignquantity", ss.getConsignquantity());
		result.put("consignsum", ss.getConsignsum());
		result.put("passengerquantity", ss.getPassengerquantity());
		result.put("passengersum", ss.getPassengersum());
		result.put("iscancel", ss.getIscancel());
		result.put("iscancelname", ss.getIscancelname());
		// 根据班次号取票价
		Map<String, Object> tm = new HashMap<String, Object>();
		tm.put("shiftcode",shift.getShiftcode());
		tm.put("ticketdate", DateUtil.getCurrentDateString());
		//List tlls = ticketlineService.getTicketLineByShiftCode(tm);
		List<Map> tlls = ticketlineService.getTicketLineByShiftId(shift.getId());
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
		//String shift_code = shiftService.findCodeById(ss.getShiftid());
		c.put("ridedate", DateUtil.getCurrentDateString());
		c.put("lmid", ss.getLmid());
		c.put("shiftID", ss.getShiftid());
		c.put("ststartid", stid);
		c.put("shiftcode",shift.getShiftcode());
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
		/*Map<String, Object> s = new LinkedHashMap();
		//Shift shift = shiftService.find(ss.getShiftid());
		s.put("stid", shift.getOriginstid());
		s.put("stname", shift.getOriginstname());
		s.put("sortnum", 0);*/
		// wayls.add(s);
		wayls.addAll(linemanageService.getWayStation(ss.getLmid()));

		//wayls.add(0, s);
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
			fss.put("shiftcode",shift.getShiftcode());
			ShiftStart ahf = shiftstartService.getShiftStartByCRS(fss);
			if (ahf != null) {
				aw.put("allticketnum", ahf.getAllticketnum());
				aw.put("halfticketnum", ahf.getHalfticketnum());
				aw.put("freeticketnum", ahf.getFreeticketnum());
				aw.put("consignquantity", ahf.getConsignquantity());
				aw.put("passengerquantity", ahf.getPassengerquantity());
				aw.put("memo", ahf.getMemo());
				if (ahf.getMemo() != null && !ahf.getMemo().equals("null") && !ahf.getMemo().equals("")) {
					strmemo += aw.get("stname") + "：" + ahf.getMemo() + "\n";
				}
				// aw.put("memo", ahf.getMemo());
				aw.put("isstart", ahf.getIsstart());
				allpeople += ahf.getCurrpeople();// 累计车上人数
				allpackage += ahf.getConsignquantity() + ahf.getPassengerquantity();
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
			b.put("ticketcode", o.getTicketcode());
			int ticketcount = saleorderticketService.getValidateTicketCountBySOID(b);

			at.put("id", o.getId());
			// at.put("statusname", o.getStatusName());
			at.put("lName", o.getLname());
			at.put("lMobile", o.getLmobile());
			at.put("sTArriveName", o.getStarrivename());
			at.put("quantity", ticketcount);
			at.put("iDCode", o.getIdcode());
			at.put("status", o.getStatus());
			at.put("statusName", o.getStatusname());

			saleallticket += ticketcount;
			atls.add(at);
		}

		// 当前站销售总票数
		result.put("saleallticket", saleallticket);
		// 获取当前站未验票信息，条件：班次，出发站（当前站），出行日期
		long notrain = saleorderticketService.getNoOnTrain(c);
		result.put("notrain", notrain);

		result.put("ordermsg", atls);
		result.put("istemp", ss.getIstemp());
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
		//
		Shift shift = shiftService.find(ss.getShiftid());
		c.put("shiftcode",shift.getShiftcode());
		c.put("lmid", ss.getLmid());
		c.put("ridedate",shift.getRidedate());
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
		Shift sh = new Shift();
		String id = request.getParameter("id");
		String stid = request.getParameter("stid");
		String stname = request.getParameter("stname");
		sh.setShiftcode(request.getParameter("shiftcode"));//班次号
		sh.setRidedate(DateUtil.getCurrentDateString());//出行日期
		sh.setLmid(0);// Integer.parseInt(request.getParameter("lmid")));线路
		sh.setStatus(0);
		Admin admin = adminService.find(amap.get("userid").toString());
		sh.setTranscompany(admin.getCompanyname());//运输公司
		sh.setTcid(admin.getTcid());//运输公司ID
		sh.setMakedate(DateUtil.getCurrentDateTime());
		sh.setCitystartid(request.getParameter("citystartid"));
		sh.setCitystartname(request.getParameter("citystartname"));
		sh.setCityarriveid(request.getParameter("cityarriveid"));
		sh.setCityarrivename(request.getParameter("cityarrivename"));
		sh.setOriginstid(request.getParameter("originstid"));//始发站ID
		sh.setOriginstname(request.getParameter("originstname"));//始发站名称
		sh.setStarriveid(request.getParameter("starriveid"));//目的站ID
		sh.setStarrivename(request.getParameter("starrivename"));//目的站名称
		sh.setOriginstarttime(request.getParameter("originstarttime"));//始发时间
		String currstationid = request.getParameter("currstationid");
		ss.setIstemp(RequestTool.getInt(request, "istemp", 0));
		if (ss.getIstemp() == 1) {
			// 根据站点获取父ID，再根据父ID获取城市名称
			CityStation cs = citystationService.find(currstationid);
			CityStation ps = citystationService.find(cs.getParentid());
			// 根据到达站点查城市
			CityStation as = citystationService.find(sh.getStarriveid());
			CityStation ds = citystationService.find(as.getParentid());
			sh.setLinename(ps.getCityname() + "-" + ds.getCityname());
			ss.setLinename(ps.getCityname() + "-" + ds.getCityname());//线路名称
		} else {
			sh.setLinename(request.getParameter("linename"));//线路名称
			ss.setLinename(request.getParameter("linename"));
		}
		ss.setLmid(0);
		ss.setPlatenum(request.getParameter("platenum"));//车牌
		ss.setDriverid(request.getParameter("driverid"));//司机ID
		ss.setDrivername(request.getParameter("drivername"));//司机姓名
		ss.setDriveridii(request.getParameter("driveridii"));//司机2
		ss.setDrivernameii(request.getParameter("drivernameii"));//司机2姓名
		ss.setNuclearload(RequestTool.getInt(request, "nuclearload", 45));//核载
		ss.setCurrpeople(RequestTool.getInt(request, "currpeople", 0));//当前人数
		ss.setCurrstationid(request.getParameter("currstationid"));//当前车站ID
		ss.setCurrstationname(request.getParameter("currstationname"));//当前车站名称
		ss.setPunctualstart(request.getParameter("punctualstart"));//正点发车时间
		ss.setActualstart(request.getParameter("actualstart"));//实际发车时间
		ss.setIsstart(RequestTool.getInt(request, "isstart", 0));//是否发车
		ss.setIsstartname(request.getParameter("isstartname"));//是否发车
		ss.setMemo(request.getParameter("memo"));//备注
		ss.setAllticketnum(RequestTool.getInt(request, "allticketnum", 0));//全票人数
		ss.setHalfticketnum(RequestTool.getInt(request, "halfticketnum", 0));//半票人数
		ss.setFreeticketnum(RequestTool.getInt(request, "freeticketnum", 0));//免票人数
		ss.setConsignquantity(RequestTool.getInt(request, "consignquantity", 0));//托运行李件数
		ss.setConsignsum(RequestTool.getBigDecimal(request, "consignsum", new BigDecimal("0")));//托运行李金额
		ss.setPassengerquantity(RequestTool.getInt(request, "passengerquantity", 0));//乘客行李件数
		ss.setPassengersum(RequestTool.getBigDecimal(request, "passengersum", new BigDecimal("0")));//乘客行李金额
		ss.setIscancel(0);//取消发班
		ss.setIscancelname("");//取消发班名称
		ss.setMakeid(amap.get("userid").toString());
		ss.setMakename(amap.get("realname").toString());
		ss.setMakedate(DateUtil.getCurrentDateTime());

		if (ss.getIstemp() == 1) {// 起点新增，并发车
			int save = shiftService.save(sh);
			ss.setShiftid(sh.getId());
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
		} else if (ss.getIstemp() == 2) {// 临时非起点发车
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
			tmpss.setConsignquantity(ss.getConsignquantity());
			tmpss.setConsignsum(ss.getConsignsum());
			tmpss.setPassengerquantity(ss.getPassengerquantity());
			tmpss.setPassengersum(ss.getPassengersum());
			tmpss.setIstemp(ss.getIstemp());
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
		Shift shift = shiftService.find(ss.getShiftid());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("id", ss.getId());
		result.put("lmid", ss.getLmid());
		result.put("linename", shift.getLinename());
		result.put("shiftcode", shift.getShiftcode());
		result.put("ridedate", shift.getRidedate());
		result.put("originstname", shift.getOriginstname());
		result.put("starrivename", shift.getStarrivename());
		result.put("originstarttime", shift.getOriginstarttime());
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
		result.put("consignquantity", ss.getConsignquantity());
		result.put("consignsum", ss.getConsignsum());
		result.put("passengerquantity", ss.getPassengerquantity());
		result.put("passengersum", ss.getPassengersum());
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
		c.put("shiftcode", shift.getShiftcode());
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
			aw.put("consignquantity", ahf.getConsignquantity());
			aw.put("passengerquantity", ahf.getPassengerquantity());
			if (ahf.getMemo() != null && !ahf.getMemo().equals("null") && !ahf.getMemo().equals("")) {
				strmemo += aw.get("stname") + "：" + ahf.getMemo() + "\n";
			}
			// aw.put("memo", ahf.getMemo());
			aw.put("isstart", ahf.getIsstart());
			allpeople += ahf.getCurrpeople();// 累计车上人数
			allpackage += ahf.getConsignquantity() + ahf.getPassengerquantity();
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
		Assert.notNull(shiftcode);
		Assert.notNull(iscancelname);
		// String memo = request.getParameter("memo");
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("shiftcode", shiftcode);
		a.put("iscancel", iscancel);
		a.put("iscancelname", iscancelname);
		a.put("ridedate", DateUtil.getCurrentDateString());
		// a.put("memo", memo);

		int rows = shiftService.cancelOrEableShift(a);
		//int cancel = shiftstartService.cancelShiftStart(a);
		int changeiscancel = shiftstartService.changeIsCancel(a);
		if (changeiscancel > 0 && rows>0) {
			result.put("status", APIStatus.SUCCESS_STATUS);
			result.put("info", APIStatus.SUCCESS_INFO);
		} else {
			result.put("status", APIStatus.SYSTEM_ERROR_STATUS);
			result.put("info", APIStatus.SYSTEM_ERROR_INFO);
		}
		return JSON.toJSONString(result);
		/*Integer tmpiscancel = 0;
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
		*/
		
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
		c.put("shiftID", request.getParameter("shiftID"));
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
			d.put("shiftID", o.getShiftid());
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

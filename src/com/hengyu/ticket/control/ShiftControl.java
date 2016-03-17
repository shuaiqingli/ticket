package com.hengyu.ticket.control;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hengyu.ticket.util.ExcelHanlder;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.CityStation;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.ShiftStart;
import com.hengyu.ticket.service.CityStationService;
import com.hengyu.ticket.service.ShiftStartService;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.RequestTool;

@Controller
@RequestMapping("/user")
public class ShiftControl {
	
	@Autowired
	private ShiftStartService shiftService;
	@Autowired
	private CityStationService cs;

	//班次列表
	@RequestMapping("shiftlist")
	public String shiftList(Page page,String date,String starttime,String shiftcode,String ststartid,String starriveid,String citystartid,String cityarriveid,Model m,HttpServletRequest req) throws Exception{
		Map<String,String> map = new HashMap<String, String>();
		if(StringUtils.isEmpty(date)){
			date = DateHanlder.getCurrentDate();
		}
		if(StringUtils.isEmpty(starttime)){
			starttime = "00:00";
		}
//		page.setPageSize(15);
		map.put("date", date);
		map.put("shiftcode", shiftcode);
		map.put("starttime", starttime);
		map.put("ststartid", ststartid);
		map.put("starriveid", starriveid);
		map.put("cityarriveid", cityarriveid);
		map.put("citystartid", citystartid);
		page.setParam(map);
		page.setAdmin(req.getSession().getAttribute(Const.LOGIN_USER));
		
		shiftService.findShiftList(page);
		
		m.addAttribute(page);
		
		List<CityStation> citys = cs.findAllCity();
		if(citys!=null){
			m.addAttribute("citys",citys);
		}
		
		return "user/shift_list";
	}
	
	//详情
	@RequestMapping("startShiftDetail")
	public void startShiftDetail(Integer id,Writer w) throws Exception{
		Assert.isTrue(id!=null,"班次编号不合法！");
		ShiftStart ss = shiftService.find(id);
		Assert.isTrue(ss!=null,"没有查询到班次详情！");
		APIUtil.toJSONString(ss, w);
	}
	
	
	//按线路统计，时间范围统计人数，行李
	@RequestMapping("statisticShiftStartByLine")
	public String statisticShiftStartByLine(Page page, String begindate, String enddate, Integer export, Model m, HttpServletRequest req, HttpServletResponse resp) throws Exception{
		Map<String, Object> map = RequestTool.paramsAsMap(req);
		if(StringUtils.isEmpty(begindate)){
			map.put("begindate", DateHanlder.getCurrentDate());
		}
		if(StringUtils.isEmpty(enddate)){
			map.put("enddate", DateHanlder.getCurrentDate());
		}
		if(export!=null&&export==1){
			page.setPageSize(Integer.MAX_VALUE);
		}
		page.setAdmin(req.getSession().getAttribute(Const.LOGIN_USER));
		page.setParam(map);
		shiftService.findStatisticShiftStartByLine(page);
		if(export(export,page,req,resp)){
			return null;
		}
		m.addAttribute(page);
		return "user/statisticShiftStartByLine";
	}

	public boolean export(Integer export,Page page,HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if(export!=null&&export==1){
			String[] titles = {"线路号","线路","始发站","终点站","总人数","全票","半票","免票","托运数量","托运金额","乘客超重件数","乘客超重金额"};
			String[] columns = {"lineid","linename","ststartname","starrivename","allpeople","allticketnum","halfticketnum","freeticketnum",
					"consignquantity","consignsum","passengerquantity","passengersum"};
			XSSFWorkbook book = ExcelHanlder.exportExcel(null, titles, columns, "线路人数统计", page.getData());
			ExcelHanlder.writer(book,"线路人数统计.xlsx",resp,req);
			return true;
		}
		return false;
	}
	
}

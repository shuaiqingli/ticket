package com.hengyu.ticket.control;

import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.CityStation;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.TicketLine;
import com.hengyu.ticket.entity.TicketStore;
import com.hengyu.ticket.exception.BaseException;
import com.hengyu.ticket.service.CityStationService;
import com.hengyu.ticket.service.LineManageService;
import com.hengyu.ticket.service.ShiftStartService;
import com.hengyu.ticket.service.TicketLineService;
import com.hengyu.ticket.service.TicketStoreService;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.ExcelHanlder;

@Controller
@RequestMapping("/user")
public class TicketStoreControl {
	
	//票库
	@Autowired
	private TicketStoreService tss;
	@Autowired
	private LineManageService lms;
	@Autowired
	private CityStationService csService;
	@Autowired
	private TicketLineService tls;
	@Autowired
	private ShiftStartService shifService;

	//余票列表
	@RequestMapping("ticketquantitys")
	@SuppressWarnings("rawtypes")
	public String ticketline(HttpServletRequest req,Page page,TicketStore ts,Model m) throws Exception{
		if(!StringUtils.isNotEmpty(ts.getTicketDate())){
			ts.setTicketDate(DateHanlder.getCurrentDate());
		}
		List<CityStation> citys = csService.findAllCity();
		Admin admin = (Admin) req.getSession().getAttribute(Const.LOGIN_USER);
		page.setAdmin(admin);
		page.setParam(ts);
		List list = tss.findQuantityBalance(page);
		page.setData(list);
		m.addAttribute(page);
		m.addAttribute("citys", citys);
		return "user/ticketquantitys";
	}
	
	//班次详情
	@RequestMapping("shiftdetail")
	public void shiftDetail(Page page,TicketLine tl,Writer w) throws Exception{
		page.setPageSize(Integer.MAX_VALUE);
		tl.setIscustomer(0);
		page.setParam(tl);
		List<?> list = tls.findTicketList(page, false);
		page.setData(list);
		w.write(JSON.toJSONString(page));
		w.close();
	}
	
	//取消班次，取消车票线路
	@RequestMapping("cancelShiftTicketLine")
	@ResponseBody
	public String cancelShiftTicketLine(String lineid,String shiftcode,String ticketdate,String iscancelname,String iscancel) throws Exception{
		int r = Const.ERROR_CODE;
		if(StringUtils.isNotEmpty(lineid)&&StringUtils.isNotEmpty(shiftcode)
				&&StringUtils.isNotEmpty(ticketdate)&&StringUtils.isNotEmpty(iscancelname)
				&&StringUtils.isNotEmpty(iscancel)){
			Map<String,String> map = new HashMap<String,String>();
			map.put("lineid", lineid);
			map.put("shiftcode", shiftcode);
			map.put("ridedate", ticketdate);
			map.put("iscancel", iscancel);
			map.put("iscancelname", iscancelname);
			r = shifService.cancelShiftStartTicketLine(map);
		}
		return r+"";
	}
	
	//取消班次
	@RequestMapping("cancelTicketStoreShift")
	public void cancelShift(Integer id,Integer status,Writer w) throws Exception{
		Assert.notNull(id,"编号不能为空！");
		Assert.isTrue(status==1||status==2,"非法取消状态！");
		int r = tss.updateCancelShiftStart(id, status);
		APIUtil.toJSONString(r, w);
	}
	
	//审核车票
	@RequestMapping("approveTicketStoreTicketLine")
	public void approveTicketStoreTicketLine(Integer lmid,String begindate,String enddate,Integer isrelease,Writer w) throws Exception{
		if(lmid==null||StringUtils.isNotEmpty(begindate)==false){
			APIUtil.toJSONString(-1, w);
		}else{
			int r = tss.updateTicketStore(lmid, begindate, enddate,isrelease);
			APIUtil.toJSONString(r, w);
		}
	}
	
	//	按日期查询票库，车票线路
	@RequestMapping("ticketmanage")
	public String ticketmanage(TicketStore ts,Model m) throws Exception{
		if(StringUtils.isNotBlank(ts.getTicketDate())==false){
			ts.setTicketDate(DateHanlder.getCurrentDate());
		}
		LineManage lm = lms.find(ts.getLmid());
		if(lm!=null){
			m.addAttribute("lm", lm);
			m.addAttribute("date", ts.getTicketDate());
			Map<String, Integer> dateinfo = tss.findAllApproveReleaseDates(lm.getId());
			m.addAttribute("dateinfo", dateinfo);
		}else{
			throw new BaseException("没有找到该线路...",500);
		}
		return "user/ticketmanage";
	}
	
	//车票列表
	@RequestMapping("tickemanagetlist")
	public void ticketlist(TicketStore ts,Writer w) throws Exception{
		List<TicketStore> list = tss.findticketmanage(ts);
		APIUtil.toJSON(w, list, null, null, false,true);
	}
	
	//按线路号，开始结束日期（按月查询）
	@RequestMapping("findApproveReleaseDates")
	public void findApproveReleaseDates(Integer lmid,String begindate,String enddate,Writer w) throws IOException{
		List<Map<String, String>> list = tss.findApproveReleaseDates(lmid, begindate, enddate);
		APIUtil.toJSONString(list, w);
	}
	
	//按日期，线路编号，查询导出excel班次信息
	@RequestMapping("exportShiftStartInfo")
	public void exportShiftStartInfo(Integer lmid,String begindate,String enddate,HttpServletRequest req,HttpServletResponse resp) throws Exception{
		
//		Assert.notNull(lmid,"线路编号不能为空！");
		Assert.hasText(begindate,"开始日期不能为空！");
		Assert.hasText(enddate,"结束日期不能为空！");
		
//		LineManage lm = lms.findByIdTcid(lmid, req.getAttribute(Const.LOGIN_USER));
		
		List<Map<String, String>> list = shifService.findExportExcelShiftByDate(lmid, begindate, enddate);
		
		//列名
		String[] columns = new String[]{"shiftcode","ridedate","weekday","originstarttime","punctualstart","linename",
				"currstationname","starrivename","platenum","drivername","drivernameii","allticketnum","halfticketnum","freeticketnum","memo"};
		
		//标题
		String[] titles = new String[]{"班次号","发车日期 ","星期","始发时间","发车时间","线路名称","出发站","终点站","车牌","司机","司机II","全票人数","半票人数","免票人数","备注"};
		
		final SimpleDateFormat df = DateHanlder.getDateFromat();
		XSSFWorkbook book = ExcelHanlder.exportExcel(titles, columns,"班次信息", list,new ExcelHanlder.ExcelTypeHanlder<Map<String,String>>() {
			@Override
			public Object dataNullHander(String column,Map<String,String> map) {
				String val = null;
				if(column!=null&&column.equals("weekday")){
					val = map.get("ridedate");
					if(val!=null){
						try {
							val = DateHanlder.getWeekDayStrByDate(df.parse(val));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
				return val;
			}
		});
		
		String name  = new StringBuffer().append("班次信息").append(begindate).append("至").append(enddate).append(".xlsx").toString();
        String userAgent = req.getHeader("User-Agent");  
        // name.getBytes("UTF-8")处理safari的乱码问题  
        byte[] bytes = userAgent.contains("MSIE") ? name.getBytes() : name.getBytes("UTF-8"); 
        // 各浏览器基本都支持ISO编码  
        name = new String(bytes, "ISO-8859-1"); 
        
		resp.setCharacterEncoding("UTF-8");
		resp.addHeader("Content-type"," application/octet-stream");
		resp.addHeader("Content-Disposition",new StringBuffer().append("attachment;filename=").append(name).toString());
		ServletOutputStream out = resp.getOutputStream();
		
		try {
			book.write(out);
		} finally {
			out.close();
		}
	}
	
	//重置审核状态
	@RequestMapping("resetTicketStoreByDateLMID")
	@ResponseBody
	public String resetTicketStoreByDateLMID(Integer lmid,String ticketdate) throws Exception{
		Assert.hasText(ticketdate,"日期不能为空！");
		Assert.notNull(lmid,"线路编号不能为空！");
		Assert.isTrue(tss.updateResetApprove(ticketdate, lmid, 0)>=1,"重置失败！");
		return "1";
	}
	
}

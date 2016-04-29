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

import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.service.*;
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
import com.hengyu.ticket.exception.BaseException;
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
    @Autowired
    private ShiftService shiftService;
    @Autowired
    private TicketService ticketService;

    //余票列表
    @RequestMapping("ticketquantitys")
    @SuppressWarnings("rawtypes")
    public String ticketline(HttpServletRequest req, Page page, TicketStore ts, Model m) throws Exception {
        if (!StringUtils.isNotEmpty(ts.getTicketdate())) {
            ts.setTicketdate(DateHanlder.getCurrentDate());
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
    public void shiftDetail(Page page, TicketLine tl, Writer w) throws Exception {
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
    public String cancelShiftTicketLine(String lineid, String shiftcode, String ticketdate, String iscancelname, String iscancel) throws Exception {
        int r = Const.ERROR_CODE;
        if (StringUtils.isNotEmpty(lineid) && StringUtils.isNotEmpty(shiftcode)
                && StringUtils.isNotEmpty(ticketdate) && StringUtils.isNotEmpty(iscancelname)
                && StringUtils.isNotEmpty(iscancel)) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("lineid", lineid);
            map.put("shiftcode", shiftcode);
            map.put("ridedate", ticketdate);
            map.put("iscancel", iscancel);
            map.put("iscancelname", iscancelname);
            r = shifService.cancelShiftStartTicketLine(map);
        }
        return r + "";
    }

    //	按日期查询票库，车票线路
    @RequestMapping("ticketmanage")
    public String ticketManage(TicketStore ts, Integer tplid, String promotionid, Model m) throws Exception {
        if (StringUtils.isNotBlank(ts.getTicketdate()) == false) {
            ts.setTicketdate(DateHanlder.getCurrentDate());
        }
        LineManage lm = lms.find(ts.getLmid());
        if (lm != null) {
            m.addAttribute("lm", lm);
            m.addAttribute("date", ts.getTicketdate());
            Map<String, Integer> dateinfo = tss.findAllApproveReleaseDates(lm.getId());
            m.addAttribute("dateinfo", dateinfo);
            List<Shift> shifts = shiftService.findShiftTicketManageList(lm.getId(), ts.getTicketdate(), ts.getTicketdate());
            if (tplid != null) {
                ticketService.matchSaleRulePriceRule(shifts, tplid, promotionid);
            }
            m.addAttribute("shifts", shifts);
        } else {
            throw new BaseException("没有找到该线路...", 500);
        }
        return "user/ticketmanage";
    }

    //更新发布状态
    @RequestMapping("updateReleaseStatus")
    @ResponseBody
    public String updateReleaseStatus(Integer lmid,String shiftcode ,String begindate, String enddate,Integer status) throws Exception {
        Assert.notNull(lmid);
        Assert.notNull(status);
        Assert.hasText(begindate);
        Assert.hasText(enddate);
        Assert.hasText(shiftcode);
        tss.updateReleaseStatusByDate(lmid,begindate,enddate,shiftcode,status,status==1?2:1);
        return "1";
    }

    //更新发布状态
    @RequestMapping("updateTicketStoreRelease")
    @ResponseBody
    public String updateTicketStoreRelease(Integer lmid, String begindate, String enddate) throws Exception {
        Assert.notNull(lmid);
        Assert.hasText(begindate);
        Assert.hasText(enddate);
        ticketService.updateReleaseTicketStore(lmid,begindate,enddate);
        return "1";
    }


    //按线路号，开始结束日期（按月查询）
    @RequestMapping("findApproveReleaseDates")
    public void findApproveReleaseDates(Integer lmid, String begindate, String enddate, Writer w) throws IOException {
        List<Map<String, String>> list = tss.findApproveReleaseDates(lmid, begindate, enddate);
        APIUtil.toJSONString(list, w);
    }

    //按日期，线路编号，查询导出excel班次信息
    @RequestMapping("exportShiftStartInfo")
    public void exportShiftStartInfo(Integer lmid, String begindate, String enddate, HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Assert.hasText(begindate, "开始日期不能为空！");
        Assert.hasText(enddate, "结束日期不能为空！");
        List<Map<String, String>> list = shifService.findExportExcelShiftByDate(lmid, begindate, enddate);
        //列名
        String[] columns = new String[]{"shiftcode", "ridedate", "weekday", "originstarttime", "punctualstart", "linename",
                "currstationname", "starrivename", "platenum", "drivername", "drivernameii", "allticketnum", "halfticketnum", "freeticketnum", "memo", "isstartname"};
        //标题
        String[] titles = new String[]{"班次号", "发车日期 ", "星期", "始发时间", "发车时间", "线路名称", "出发站", "终点站", "车牌", "司机", "司机II", "全票人数", "半票人数", "免票人数", "备注", "状态"};

        final SimpleDateFormat df = DateHanlder.getDateFromat();
        XSSFWorkbook book = ExcelHanlder.exportExcel(titles, columns, "班次信息", list, new ExcelHanlder.ExcelTypeHanlder<Map<String, String>>() {
            @Override
            public Object dataNullHander(String column, Map<String, String> map) {
                String val = null;
                if (column != null && column.equals("weekday")) {
                    val = map.get("ridedate");
                    if (val != null) {
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

        String name = new StringBuffer().append("班次信息").append(begindate).append("至").append(enddate).append(".xlsx").toString();
        ExcelHanlder.writer(book, name, resp, req);
    }

}

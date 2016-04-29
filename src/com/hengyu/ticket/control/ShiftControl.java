package com.hengyu.ticket.control;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.service.*;
import com.hengyu.ticket.util.ExcelHanlder;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.RequestTool;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class ShiftControl {

    @Autowired
    private ShiftStartService shiftStartService;
    @Autowired
    private CityStationService cs;
    @Autowired
    private ShiftService shiftService;
    @Autowired
    private LineManageService lineManageService;
    @Autowired
    private StationTimeRuleService stationTimeRuleService;
    @Autowired
    private LineSchedueService lineSchedueService;
    @Autowired
    private ScheduleTaskService scheduleTaskService;
    //排班
    @Autowired
    private LineSchedueService scheduleService;
    @Autowired
    private TicketService ticketService;

    //班次列表
    @RequestMapping("shiftlist")
    public String shiftList(Page page, Integer export, String starttime, String shiftcode, String ststartid,
                            String starriveid, String citystartid, String cityarriveid, String begindate, String enddate,
                            Model m, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isEmpty(begindate)) {
            begindate = DateHanlder.getCurrentDate();
        }
        if (StringUtils.isEmpty(enddate)) {
            enddate = begindate;
        }
        if (StringUtils.isEmpty(starttime)) {
            starttime = "00:00";
        }
//		page.setPageSize(15);
        map.put("begindate", begindate);
        map.put("enddate", enddate);
        map.put("shiftcode", shiftcode);
        map.put("starttime", starttime);
        map.put("ststartid", ststartid);
        map.put("starriveid", starriveid);
        map.put("cityarriveid", cityarriveid);
        map.put("citystartid", citystartid);
        page.setParam(map);
        page.setAdmin(req.getSession().getAttribute(Const.LOGIN_USER));
        if (export != null && export == 1) {
            page.setPageSize(Integer.MAX_VALUE);
        }
        shiftStartService.findShiftList(page);
        if (exportShiftStartList(export, page, req, resp)) {
            return "";
        }
        m.addAttribute(page);

        List<CityStation> citys = cs.findAllCity();
        if (citys != null) {
            m.addAttribute("citys", citys);
        }

        return "user/shift_list";
    }


    //修改发车状态
    @RequestMapping("updateShiftStartIsCancel")
    @ResponseBody
    public String updateShiftStartIsCancel(Long shiftid,Integer id, Integer iscancel) throws Exception {
        Assert.notNull(id);
        Assert.notNull(iscancel);
        Assert.notNull(shiftid);
        String iscancelname = iscancel == 0 ? "正常" : "已取消";
        Integer oldiscancel = iscancel == 0 ? 1 : 0;
        shiftStartService.updateShiftStartIsCancel(shiftid,id, iscancel, iscancelname, oldiscancel);
        return "1";
    }

    public boolean exportShiftStartList(Integer export, Page page, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (export != null && export == 1) {
            String[] titles = {"班车号", "线路", "出发站", "终点站", "全票", "半票", "免票", "托运行李件数", "托运行李金额",
                    "乘客行李件数", "乘客行李金额", "发车站务", "备注", "变更日志"};
            String[] columns = {"shiftcode", "linename", "currstationname", "starrivename", "allticketnum", "halfticketnum", "freeticketnum",
                    "consignquantity", "consignsum", "passengerquantity", "passengersum", "makename", "memo", "changelog"};
            XSSFWorkbook book = ExcelHanlder.exportExcel(null, titles, columns, "班车发车", page.getData());
            ExcelHanlder.writer(book, "班车发车.xlsx", resp, req);
            return true;
        }
        return false;
    }


    //班次管理
    @RequestMapping("shiftManage")
    public String shiftManage(Integer lmid, String date, Model model) throws Exception {
        Assert.notNull(lmid, "线路编号不能为空！");
        LineManage lm = lineManageService.find(lmid);
        if (StringUtils.isEmpty(date)) {
            date = DateHanlder.getCurrentDate();
        }
        List<Shift> shifts = shiftService.findList(lmid, date, date);
        SimpleDateFormat df = DateHanlder.getDateFromat();

        model.addAttribute("date", date);
        model.addAttribute("lm", lm);
        model.addAttribute("timeout", df.parse(date).compareTo(df.parse(df.format(new Date()))) < 0);
        if (shifts != null) {
            model.addAttribute("shifts", shifts);
        }

        Map<String, Object> result = scheduleService.checkForDistributeDriverAndPlate(lmid, date);
        model.addAllAttributes(result);

        return "user/lineShiftManage";
    }

    //获取排班任务
    @RequestMapping("getScheduleTask")
    public String getScheduleTask(Integer lmid, String begindate, String enddate, Model model) throws Exception {
        Assert.notNull(lmid, "线路编号不能为空！");
        Assert.hasText(begindate, "开始日期不能为空！");
        if (StringUtils.isEmpty(enddate)) {
            enddate = begindate;
        } else {
            SimpleDateFormat dateFromat = DateHanlder.getDateFromat();
            Assert.isTrue(dateFromat.parse(begindate).compareTo(dateFromat.parse(enddate)) <= 0, "开始日期不能大于结束日期！");
        }
        List<ScheduleTask> tasks = shiftService.getScueduleTask(lmid, begindate, enddate);
        Assert.isTrue(tasks.size() <= 30, "排班不能超过30天！");
        model.addAttribute("tasks", tasks);
        model.addAttribute("begindate", begindate);
        model.addAttribute("enddate", enddate);
        List<StationTimeRule> linerules = stationTimeRuleService.findByLMID(lmid);
        List<LineSchedue> scheduerules = lineSchedueService.findList(lmid);
        if (linerules != null) {
            model.addAttribute("linerules", linerules);
        }
        if (scheduerules != null) {
            model.addAttribute("scheduerules", scheduerules);
        }
        return "user/lineschedule";
    }

    //获取车票任务
    @RequestMapping("getTicketTask")
    public String getTicketTask(Integer lmid, String begindate, String enddate, Model model) throws Exception {
        Assert.notNull(lmid, "线路编号不能为空！");
        Assert.hasText(begindate, "开始日期不能为空！");
        if (StringUtils.isEmpty(enddate)) {
            enddate = begindate;
        } else {
            SimpleDateFormat dateFromat = DateHanlder.getDateFromat();
            Assert.isTrue(dateFromat.parse(begindate).compareTo(dateFromat.parse(enddate)) <= 0, "开始日期不能大于结束日期！");
        }
        List<ScheduleTask> tasks = shiftService.getTicketTask(lmid, begindate, enddate);
        Assert.isTrue(tasks.size() <= 30, "出票不能超过30天！");
        model.addAttribute("tasks", tasks);
        model.addAttribute("begindate", begindate);
        model.addAttribute("enddate", enddate);

        return "user/ticket_task";
    }

    //任务排班
    @RequestMapping("scheduleTask")
    @ResponseBody
    public String scheduleTask(ScheduleTask task, Integer lmid) throws Exception {
        Assert.notEmpty(task.getTasks(), "请勾选排班日期！");
        scheduleTaskService.saveTask(task.getTasks(), lmid, true, false);
        return "1";
    }

    //出票排班
    @RequestMapping("ticketTask")
    @ResponseBody
    public String ticketTask(ScheduleTask task, Integer lmid) throws Exception {
        Assert.notEmpty(task.getTasks(), "请勾选排班日期！");
        ticketService.updateMatchRule(lmid, task.getTasks());
        return "1";
    }

    //删除任务排班
    @RequestMapping("delScheduleTask")
    @ResponseBody
    public String delScheduleTask(String begindate, String enddate, Integer lmid) throws Exception {
        Assert.notNull(lmid, "线路编号不能为空！");
        Assert.hasText(begindate, "开始日期不能为空！");
        if (StringUtils.isEmpty(enddate)) {
            enddate = begindate;
        } else {
            SimpleDateFormat dateFromat = DateHanlder.getDateFromat();
            Assert.isTrue(dateFromat.parse(begindate).compareTo(dateFromat.parse(enddate)) <= 0, "开始日期不能大于结束日期！");
        }
        shiftService.delete(lmid, begindate, enddate);
        return "1";
    }

    //详情
    @RequestMapping("startShiftDetail")
    public void startShiftDetail(Integer id, Writer w) throws Exception {
        Assert.isTrue(id != null, "班次编号不合法！");
        ShiftStart ss = shiftStartService.getDetail(id);
        Assert.isTrue(ss != null, "没有查询到班次详情！");
        APIUtil.toJSONString(ss, w);
    }

    //更新班次状态
    @RequestMapping("updateShiftStatus")
    @ResponseBody
    public String updateShiftStatus(Integer lmid, String begindate, String enddate, Integer status, String shiftcode) throws Exception {
        Assert.notNull(lmid);
        Assert.notNull(status);
        Assert.hasText(begindate);
        Assert.hasText(enddate);
        Assert.hasText(shiftcode);
        shiftService.updateShiftStatus(lmid, begindate, enddate, status, shiftcode);
        return "1";
    }

    //更新班次时间
    @RequestMapping("updateShiftTime")
    @ResponseBody
    public String updateShiftTime(Integer lmid, String begindate, String enddate, String starttime, String oldstarttime, String shiftcode,String stid) throws Exception {
        Assert.notNull(lmid);
        Assert.hasText(stid);
        Assert.hasText(begindate);
        Assert.hasText(enddate);
        Assert.hasText(shiftcode);
        Assert.hasText(starttime);
        Assert.hasText(oldstarttime);
        Assert.isTrue(!starttime.equals(oldstarttime), "修改时间不能跟旧的时间相同！");
        shiftService.updateTime(lmid, begindate, enddate, starttime, oldstarttime, shiftcode,stid);
        return "1";
    }

    //查询已经生成车票的日期
    @RequestMapping("findShiftRideDates")
    public void findShiftRideDates(Integer lmid, String begindate, String enddate, Writer w) throws Exception {
        List<String> dates = shiftService.findShiftRideDates(lmid, begindate, enddate);
        APIUtil.toJSONString(dates, w);
    }


    //按线路统计，时间范围统计人数，行李
    @RequestMapping("statisticShiftStartByLine")
    public String statisticShiftStartByLine(Page page, String begindate, String enddate, Integer export, Model m, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Map<String, Object> map = RequestTool.paramsAsMap(req);
        if (StringUtils.isEmpty(begindate)) {
            map.put("begindate", DateHanlder.getCurrentDate());
        }
        if (StringUtils.isEmpty(enddate)) {
            map.put("enddate", DateHanlder.getCurrentDate());
        }
        if (export != null && export == 1) {
            page.setPageSize(Integer.MAX_VALUE);
        }
        page.setAdmin(req.getSession().getAttribute(Const.LOGIN_USER));
        page.setParam(map);
        shiftStartService.findStatisticShiftStartByLine(page);
        if (export(export, page, req, resp)) {
            return null;
        }
        m.addAttribute(page);
        return "user/statisticShiftStartByLine";
    }

    public boolean export(Integer export, Page page, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (export != null && export == 1) {
            String[] titles = {"线路号", "线路", "始发站", "终点站", "总人数", "全票", "半票", "免票", "托运数量", "托运金额", "乘客超重件数", "乘客超重金额"};
            String[] columns = {"lineid", "linename", "ststartname", "starrivename", "allpeople", "allticketnum", "halfticketnum", "freeticketnum",
                    "consignquantity", "consignsum", "passengerquantity", "passengersum"};
            XSSFWorkbook book = ExcelHanlder.exportExcel(null, titles, columns, "线路人数统计", page.getData());
            ExcelHanlder.writer(book, "线路人数统计.xlsx", resp, req);
            return true;
        }
        return false;
    }

}

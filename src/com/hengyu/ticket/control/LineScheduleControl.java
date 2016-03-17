package com.hengyu.ticket.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.service.ApproveTicketService;
import com.hengyu.ticket.service.LineManageService;
import com.hengyu.ticket.service.LineSchedueService;
import com.hengyu.ticket.service.TicketLineService;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.DateUtil;
import com.hengyu.ticket.util.NumberCreate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

//线路排班
@Controller
@RequestMapping("/user")
public class LineScheduleControl {

    Logger log = Logger.getLogger(LineScheduleControl.class);

    //排班
    @Autowired
    private LineSchedueService scheduleService;
    @Autowired
    private TicketLineService tls;
    //线路管理
    @Autowired
    private LineManageService lms;
    //审核车票
    @Autowired
    private ApproveTicketService ats;

    //删除排班
    @RequestMapping(value = "delschedule.do")
    @ResponseBody
    public String delLineSchedule(Integer isDel, Integer id) throws Exception {
        int result = -1;
        if (1 == isDel && id != null) {
            result = scheduleService.delete(id);
        }
        return String.valueOf(result);
    }

    //编辑
    @RequestMapping(value = "editlineschedule.do")
    @SuppressWarnings("all")
    public void lineScheduleEdit(LineSchedue ls,String lineid,Writer w) throws Exception {
        validateLineScheduleEdit(ls,lineid);
        int result = -1;
        if (ls != null && ls.getId() != null) {
            result = this.scheduleService.update(ls);
        } else if (ls != null && ls.getId() == null) {
            result = this.scheduleService.save(ls);
        }
    }

    //验证排班
    public void validateLineScheduleEdit(LineSchedue ls,String lineid) throws Exception {
        Assert.notNull(lineid,"线路号不能为空！");
        Assert.notNull(ls.getLmid(),"线路编号不能为空！");
        Assert.hasText(ls.getScheduname(),"排班名称不能为空！");
        List<LineScheduDetail> schedules = ls.getSchedules();
        List<LineScheduDetail> checkSchedule = new ArrayList<LineScheduDetail>();
        for (LineScheduDetail d : schedules ) {
            if(d.getIsdel()!=null&&d.getIsdel()==1){
                checkSchedule.add(d);
            }
        }

        SimpleDateFormat timeFromat = DateHanlder.getTimeFromat();

        int i = 0;
        for (LineScheduDetail d : checkSchedule ) {
            Assert.hasText(d.getStarttime(),"发车时间不能为空！");
            String shiftcode = new StringBuffer().append(lineid.toUpperCase()).append(NumberCreate.createNumber(i+1,2)).toString();
            d.setShiftcode(shiftcode);
            if(i>0){
                LineScheduDetail lsd = checkSchedule.get(i - 1);
                Assert.isTrue(timeFromat.parse(d.getStarttime()).compareTo(timeFromat.parse(lsd.getStarttime()))==1,
                        MessageFormat.format("{0}发车时间填写不正确",shiftcode));
            }
            i++;
        }
    }


    @RequestMapping(value = {"distributeDriverAndPlatePage.do"})
    public String distributeDriverAndPlatePage(String groupid, String date, Model m) {
        Assert.hasText(groupid, "线路不能为空");
        Date parsedDate = DateUtil.stringToDate(date);
        Assert.notNull(parsedDate, "日期不能为空");
        m.addAttribute("data", scheduleService.getDataForDistributeDriverAndPlate(groupid, parsedDate));
        return "user/distributeDriverAndPlate";
    }

    @RequestMapping(value = {"distributeDriverAndPlatePreview.do"})
    public void distributeDriverAndPlatePreview(String dataMap, Writer w) throws IOException {
        Assert.hasText(dataMap, "参数不能为空");
        APIUtil.toJSONString(scheduleService.previewForDistributeDriverAndPlate(JSON.parseObject(dataMap)), w);
    }

    @ResponseBody
    @RequestMapping(value = {"distributeDriverAndPlate.do"})
    public String distributeDriverAndPlate(String dataMap){
        Assert.hasText(dataMap, "参数不能为空");
        scheduleService.updateForDistributeDriverAndPlate(JSON.parseObject(dataMap));
        return "1";
    }

}

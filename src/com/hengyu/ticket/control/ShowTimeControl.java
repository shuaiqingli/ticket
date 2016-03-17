package com.hengyu.ticket.control;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.ShowTime;
import com.hengyu.ticket.service.ShowTimeService;
import com.hengyu.ticket.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class ShowTimeControl {

    @Autowired
    private ShowTimeService showTimeService;

    @RequestMapping("showTimeList.do")
    public String showTimeList(String keyword, Page page, Model m) {
        m.addAttribute("showtimeList", showTimeService.findShowTimeList(keyword, page));
        m.addAttribute("page", page);
        return "user/showtimeList";
    }

    @RequestMapping("showTimeEditPage.do")
    public String showTimeEditPage(Integer id, Model m) {
        if (id != null) {
            m.addAttribute("showtime", showTimeService.findShowTimeDetail(id));
        }
        return "user/showtimeEdit";
    }

    @ResponseBody
    @RequestMapping("showTimeEdit.do")
    public String showTimeEdit(ShowTime showTime, HttpServletRequest request) {
        Assert.hasText(showTime.getShowcontent(), "提示内容不能为空");
        Date beginDate = DateUtil.stringToDate(showTime.getBegindate());
        Date endDate = DateUtil.stringToDate(showTime.getEnddate());
        Assert.isTrue(beginDate != null, "无效开始时间");
        Assert.isTrue(endDate != null && !endDate.before(beginDate), "无效结束时间");
        showTime.setBegindate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getFirstDate(beginDate)));
        showTime.setEnddate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getLatestDate(endDate)));
        if (showTime.getId() == null) {
            Admin admin = (Admin) request.getSession().getAttribute(Const.LOGIN_USER);
            showTime.setMakeid(admin.getUserID());
            showTime.setMakename(admin.getRealName());
            showTime.setMakedate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            showTimeService.addShowTime(showTime);
        } else {
            showTimeService.updateShowTime(showTime);
        }
        return "1";
    }

    @ResponseBody
    @RequestMapping("showTimeDel.do")
    public String showTimeDel(Integer id) {
        Assert.notNull(id, "ID不能为空");
        showTimeService.delShowTime(id);
        return "1";
    }

    @ResponseBody
    @RequestMapping("bindLineListToShowTime.do")
    public String bindLineListToShowTime(Integer stid, String lmidList) {
        Assert.notNull(stid, "ID不能为空");
        Assert.hasText(lmidList, "线路不能为空");
        List<Integer> parsedLmidList = new ArrayList<Integer>();
        for (String lmid : lmidList.split(",")) {
            parsedLmidList.add(Integer.parseInt(lmid));
        }
        showTimeService.updateForBindLineListToShowTime(stid, parsedLmidList);
        return "1";
    }

    @ResponseBody
    @RequestMapping("unbindLineToShowTime.do")
    public String unbindLineToShowTime(Integer stid, Integer lmid) {
        Assert.notNull(stid, "ID不能为空");
        Assert.notNull(lmid, "线路不能为空");
        showTimeService.updateForUnbindLineToShowTime(stid, lmid);
        return "1";
    }

}

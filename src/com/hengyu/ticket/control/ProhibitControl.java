package com.hengyu.ticket.control;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.CityStation;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.Prohibit;
import com.hengyu.ticket.service.CityStationService;
import com.hengyu.ticket.service.ProhibitService;
import com.hengyu.ticket.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Wang Wu on 2015-12-29.
 */
@Controller
@RequestMapping("/admin")
public class ProhibitControl {

    @Autowired
    private ProhibitService prohibitService;
    @Autowired
    private CityStationService cityStationService;

    @ResponseBody
    @RequestMapping("prohibitDel.do")
    public String prohibitDel(Integer id) {
        Assert.notNull(id, "ID不能为空");
        prohibitService.delProhibit(id);
        return "1";
    }

    @RequestMapping("prohibitList.do")
    public String prohibitList(String stid, Page page, Model m) throws Exception {
        Assert.hasText(stid, "ID不能为空");
        m.addAttribute("station", cityStationService.find(stid));
        m.addAttribute("prohibitList", prohibitService.findProhibitList(stid, page));
        m.addAttribute("page", page);
        return "admin/prohibitList";
    }

    @RequestMapping("prohibitEditPage.do")
    public String prohibitEditPage(String stid, Integer id, Model m) throws Exception {
        Assert.hasText(stid, "ID不能为空");
        CityStation cityStation = cityStationService.find(stid);
        Assert.notNull(cityStation, "无效车站");
        m.addAttribute("station", cityStation);
        if (null != id) {
            Prohibit prohibit = prohibitService.findProhibit(id);
            Assert.isTrue(prohibit.getStid().equals(cityStation.getId()), "车站与禁售规则不匹配");
            m.addAttribute("prohibit", prohibitService.findProhibit(id));
        }
        return "admin/prohibitEdit";
    }

    @ResponseBody
    @RequestMapping("prohibitEdit.do")
    public String prohibitEdit(Prohibit prohibit, HttpServletRequest request) {
        Assert.hasText(prohibit.getStid(), "站点不能为空");
        Date beginDate = DateUtil.stringToDate(prohibit.getBegindate());
        Date endDate = DateUtil.stringToDate(prohibit.getEnddate());
        Assert.isTrue(beginDate != null, "无效开始时间");
        Assert.isTrue(endDate != null && !endDate.before(beginDate), "无效结束时间");
        prohibit.setBegindate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getFirstDate(beginDate)));
        prohibit.setEnddate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.getLatestDate(endDate)));
        Assert.isTrue(prohibit.getWeekdays() > 0 && prohibit.getWeekdays() < 128, "无效可选星期");

        Admin admin = (Admin) request.getSession().getAttribute(Const.LOGIN_USER);
        if (prohibit.getId() == null) {
            prohibit.setMakeid(admin.getUserID());
            prohibit.setMakename(admin.getRealName());
            prohibit.setMakedate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            prohibitService.saveProhibit(prohibit);
        } else {
            prohibitService.updateProhibit(prohibit);
        }
        return "1";
    }

    @ResponseBody
    @RequestMapping("bindLineListToProhibit.do")
    public String bindLineListToProhibit(Integer ptid, String lineidList) {
        Assert.notNull(ptid, "禁售规则不能为空");
        Assert.hasText(lineidList, "线路不能为空");
        List<Integer> parsedLineidList = new ArrayList<Integer>();
        for (String lineid : lineidList.split(",")) {
            parsedLineidList.add(Integer.parseInt(lineid));
        }
        prohibitService.updateForBindLineListToProhibit(ptid, parsedLineidList);
        return "1";
    }

    @ResponseBody
    @RequestMapping("unbindLineToProhibit.do")
    public String unbindLineToProhibit(Integer id) {
        Assert.notNull(id, "ID不能为空");
        prohibitService.updateForUnbindLineToProhibit(id);
        return "1";
    }

    @ResponseBody
    @RequestMapping("bindTimeToProhibit.do")
    public String bindTimeToProhibit(Integer ptid, String begintime, String endtime) {
        Assert.notNull(ptid, "禁售规则不能为空");
        Assert.isTrue(isValidTime(begintime), "无效开始时间");
        Assert.isTrue(isValidTime(endtime), "无效结束时间");
        Assert.isTrue(begintime.compareTo(endtime) < 0, "开始时间必须小于结束时间");
        prohibitService.updateForBindTimeToProhibit(ptid, begintime, endtime);
        return "1";
    }

    @ResponseBody
    @RequestMapping("unbindTimeToProhibit.do")
    public String unbindTimeToProhibit(Integer id) {
        Assert.notNull(id, "ID不能为空");
        prohibitService.updateForUnbindTimeToProhibit(id);
        return "1";
    }

    private boolean isValidTime(String time) {
        return !StringUtils.isBlank(time) && time.matches("^(([0-1][0-9])|2[0-3]):[0-5][0-9]$");
    }

    @ResponseBody
    @RequestMapping("prohibitApply.do")
    public String prohibitApply(Integer id) throws Exception {
        return prohibitService.updateForTask(id).toString();
    }

    @RequestMapping("prohibitTicketLineList.do")
    public String prohibitTicketLineList(String startCityID, String arriveCityID, String startStationID, String arriveStationID, String beginDate, String endDate, String startTime, String endTime, String keyword, Page page, Model m) throws Exception {
        List<String> startStationIDList = new ArrayList<String>();
        List<String> arriveStationIDList = new ArrayList<String>();
        m.addAttribute("cityList", cityStationService.findAllCity());
        if(StringUtils.isNotBlank(startCityID)){
            List<CityStation> cityStationList = findStationList(startCityID);
            if(null != cityStationList && cityStationList.size() > 0){
                for(CityStation cityStation : cityStationList){
                    startStationIDList.add(cityStation.getId());
                }
            }
            m.addAttribute("startStationList", cityStationList);
        }
        if(StringUtils.isNotBlank(arriveCityID)){
            List<CityStation> cityStationList = findStationList(arriveCityID);
            if(null != cityStationList && cityStationList.size() > 0){
                for(CityStation cityStation : cityStationList){
                    arriveStationIDList.add(cityStation.getId());
                }
            }
            m.addAttribute("arriveStationList", cityStationList);
        }
        if(StringUtils.isNotBlank(startStationID)){
            startStationIDList.clear();
            startStationIDList.add(startStationID);
        }
        if(StringUtils.isNotBlank(arriveStationID)){
            arriveStationIDList.clear();
            arriveStationIDList.add(arriveStationID);
        }
        m.addAttribute("ticketLineList", prohibitService.findProhibitTicketLineList(startStationIDList.size()==0 ? null : startStationIDList, arriveStationIDList.size()==0 ? null : arriveStationIDList, StringUtils.isBlank(beginDate) ? null : beginDate, StringUtils.isBlank(endDate) ? null : endDate, StringUtils.isBlank(startTime) ? null : startTime, StringUtils.isBlank(endTime) ? null : endTime, StringUtils.isBlank(keyword) ? null : Arrays.asList(keyword.split("\\|")), page));
        m.addAttribute("page", page);
        return "admin/prohibitTicketLineList";
    }

    @ResponseBody
    @RequestMapping("openTicketLineList.do")
    public String openTicketLineList(String ticketLineIDList) {
        Assert.hasText(ticketLineIDList, "ID不能为空");
        List<Integer> parsedTicketLineIDList = new ArrayList<Integer>();
        for (String ticketLineID : ticketLineIDList.split(",")) {
            parsedTicketLineIDList.add(Integer.parseInt(ticketLineID));
        }
        prohibitService.updateForOpenTicketLineList(parsedTicketLineIDList);
        return "1";
    }

    @ResponseBody
    @RequestMapping("stationListForProhibit.do")
    public String stationListForProhibit(String cityid) throws Exception {
        Assert.hasText(cityid, "ID不能为空");
        return JSON.toJSONString(findStationList(cityid));
    }

    private List<CityStation> findStationList(String cityid) throws Exception {
        CityStation cityStation = new CityStation();
        cityStation.setParentid(cityid);
        Page page = new Page();
        page.setPageSize(1000000);
        page.setParam(cityStation);
        return cityStationService.findList(page);
    }
}

package com.hengyu.ticket.service;

import com.hengyu.ticket.dao.ProhibitDao;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.Prohibit;
import com.hengyu.ticket.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by Wang Wu on 2016-01-11.
 */
@Service
public class ProhibitService {
    private static Logger logger = Logger.getLogger(ProhibitService.class);

    @Autowired
    private ProhibitDao prohibitDao;

    public Prohibit findProhibit(Integer id) {
        Prohibit prohibit = prohibitDao.findProhibit(id);
        Assert.notNull(prohibit, "无效禁售规则");
        prohibit.setProhibitTimeList(prohibitDao.findProhibitTimeList(id));
        prohibit.setProhibitLineList(prohibitDao.findProhibitLineList(id));
        return prohibit;
    }

    public List<Prohibit> findProhibitList(String stid, Page page) {
        page.setTotalCount(prohibitDao.findProhibitTotal(stid));
        return prohibitDao.findProhibitList(stid, page);
    }

    public void saveProhibit(Prohibit prohibit) {
        Assert.isTrue(prohibitDao.saveProhibit(prohibit) == 1, "操作失败");
    }

    public void updateProhibit(Prohibit prohibit) {
        Assert.isTrue(prohibitDao.updateProhibit(prohibit) == 1, "操作失败");
    }

    public void delProhibit(Integer id) {
        Assert.isTrue(prohibitDao.delProhibit(id) == 1, "操作失败");
        prohibitDao.unbindAllTimeToProhibit(id);
        prohibitDao.unbindAllLineToProhibit(id);
    }

    public void updateForBindTimeToProhibit(Integer ptid, String begintime, String endtime) {
        Assert.isTrue(prohibitDao.bindTimeToProhibit(ptid, begintime, endtime) == 1, "操作失败");
    }

    public void updateForUnbindTimeToProhibit(Integer id) {
        Assert.isTrue(prohibitDao.unbindTimeToProhibit(id) == 1, "操作失败");
    }

    public void updateForBindLineListToProhibit(Integer ptid, List<Integer> lmidList) {
        Prohibit prohibit = prohibitDao.findProhibit(ptid);
        Assert.notNull(prohibit, "无效禁售规则");
        for (Integer lineid : lmidList) {
            Assert.isTrue(prohibitDao.checkForBindLineToProhibit(ptid, lineid) == 1, "无效线路");
            Assert.isTrue(prohibitDao.bindLineToProhibit(ptid, lineid) == 1, "操作失败");
        }
    }

    public void updateForUnbindLineToProhibit(Integer id) {
        Assert.isTrue(prohibitDao.unbindLineToProhibit(id) == 1, "操作失败");
    }

    public Integer updateForTask(Integer id) throws Exception {
        int numTotal = 0;
        logger.info("应用禁售规则开始...");
        List<Prohibit> prohibitList = prohibitDao.findProhibitListForApply(id);
        Assert.isTrue(id == null || prohibitList.size() == 1, "无效禁售规则");
        logger.info("共" + prohibitList.size() + "条有效禁售规则");
        if (prohibitList.size() > 0) {
            for (Prohibit prohibit : prohibitList) {
                logger.info("禁售规则ID为" + prohibit.getId());
                List<Map> timeList = prohibitDao.findProhibitTimeList(prohibit.getId());
                List<Map> lineList = prohibitDao.findProhibitLineList(prohibit.getId());
                if (timeList.size() == 0 || lineList.size() == 0) continue;
                String stid = prohibit.getStid();
                List<String> dateList = getValidDate(prohibit);
                if (dateList == null || dateList.size() == 0) continue;
                List<Integer> lmidList = new ArrayList<Integer>();
                for (Map line : lineList) {
                    lmidList.add((Integer) line.get("ID"));
                }
                int num = prohibitDao.updateForApplyProhibit(stid, timeList, lmidList, dateList);
                numTotal += num;
                logger.info("根据禁售规则(ID" + prohibit.getId() + "),禁售行程" + num + "条");
            }
        }
        logger.info("应用禁售规则结束...");
        return numTotal;
    }

    public List<Map> findProhibitTicketLineList(List<String> startStationIDList, List<String> arriveStationIDList, String beginDate, String endDate, String startTime, String endTime, List<String> keywordList, Page page) {
        page.setTotalCount(prohibitDao.findProhibitTicketLineTotal(startStationIDList, arriveStationIDList, beginDate, endDate, startTime, endTime, keywordList));
        return prohibitDao.findProhibitTicketLineList(startStationIDList, arriveStationIDList, beginDate, endDate, startTime, endTime, keywordList, page);
    }

    public void updateForOpenTicketLineList(List<Integer> ticketLineIDList){
        Assert.isTrue(prohibitDao.openTicketLineList(ticketLineIDList) == ticketLineIDList.size(), "操作失败");
    }

    private List<String> getValidDate(Prohibit prohibit) throws ParseException {
        List<String> result = new ArrayList<String>();
        if (null == prohibit) return result;
        Calendar begindate = Calendar.getInstance();
        begindate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(prohibit.getBegindate()));
        Calendar enddate = Calendar.getInstance();
        enddate.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(prohibit.getEnddate()));
        Calendar currentdate = Calendar.getInstance();
        currentdate.setTime(begindate.getTime());
        while (currentdate.before(enddate)) {
            if (currentdate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && (prohibit.getWeekdays() & 64) > 0)
                result.add(DateUtil.formatDate(currentdate.getTime()));
            if (currentdate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && (prohibit.getWeekdays() & 1) > 0)
                result.add(DateUtil.formatDate(currentdate.getTime()));
            if (currentdate.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY && (prohibit.getWeekdays() & 2) > 0)
                result.add(DateUtil.formatDate(currentdate.getTime()));
            if (currentdate.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY && (prohibit.getWeekdays() & 4) > 0)
                result.add(DateUtil.formatDate(currentdate.getTime()));
            if (currentdate.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY && (prohibit.getWeekdays() & 8) > 0)
                result.add(DateUtil.formatDate(currentdate.getTime()));
            if (currentdate.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && (prohibit.getWeekdays() & 16) > 0)
                result.add(DateUtil.formatDate(currentdate.getTime()));
            if (currentdate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && (prohibit.getWeekdays() & 32) > 0)
                result.add(DateUtil.formatDate(currentdate.getTime()));
            currentdate.add(Calendar.DATE, 1);
        }
        return result;
    }
}

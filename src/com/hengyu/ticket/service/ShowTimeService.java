package com.hengyu.ticket.service;

import com.hengyu.ticket.dao.*;
import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Wang Wu on 2015-12-29.
 */
@Service
public class ShowTimeService {

    @Autowired
    private ShowTimeDao showTimeDao;
    @Autowired
    private LineManageDao lineManageDao;

    public List<ShowTime> findShowTimeList(String keyword, Page page){
        page.setTotalCount(showTimeDao.findShowTimeTotal(keyword));
        return showTimeDao.findShowTimeList(keyword, page);
    }

    public ShowTime findShowTimeDetail(Integer id){
        ShowTime showTime = showTimeDao.findShowTime(id);
        Assert.notNull(showTime, "无效线路提示");
        showTime.setLineManageList(lineManageDao.findLineListBySTID(id));
        return showTime;
    }

    public void addShowTime(ShowTime showTime){
        Assert.isTrue(showTimeDao.saveShowTime(showTime) == 1, "操作失败");
    }

    public void updateShowTime(ShowTime showTime){
        Assert.isTrue(showTimeDao.updateShowTime(showTime) == 1, "操作失败");
    }

    public void delShowTime(Integer id){
        Assert.isTrue(showTimeDao.delShowTime(id) == 1, "操作失败");
        showTimeDao.unbindAllLineToShowTime(id);
    }

    public void updateForBindLineListToShowTime(Integer stid, List<Integer> lmidList) {
        for (Integer lmid : lmidList) {
            Assert.isTrue(showTimeDao.bindLineToShowTime(lmid, stid) == 1, "操作失败");
        }
    }

    public void updateForUnbindLineToShowTime(Integer stid, Integer lmid) {
        Assert.isTrue(showTimeDao.unbindLineToShowTime(lmid, stid) == 1, "操作失败");
    }

    public String findShowContent(Integer lmid, String ridedate){
        return showTimeDao.findShowContent(lmid, ridedate);
    }
}

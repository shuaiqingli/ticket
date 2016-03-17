package com.hengyu.ticket.service;

import com.hengyu.ticket.dao.ActivityMsgDao;
import com.hengyu.ticket.entity.ActivityMsg;
import com.hengyu.ticket.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Wang Wu on 2015-12-29.
 */
@Service
public class ActivityMsgService {

    @Autowired
    private ActivityMsgDao activityMsgDao;

    public List<ActivityMsg> findActivityMsgList(String keyword, Integer amsort, Page page) {
        page.setTotalCount(activityMsgDao.findActivityMsgTotal(keyword, amsort));
        return activityMsgDao.findActivityMsgList(keyword, amsort, page);
    }

    public ActivityMsg findActivityMsg(Integer id) {
        return activityMsgDao.findActivityMsg(id);
    }

    public void addActivityMsg(ActivityMsg activityMsg) {
        Assert.isTrue(activityMsgDao.saveActivityMsg(activityMsg) == 1, "操作失败");
    }

    public void updateActivityMsg(ActivityMsg activityMsg) {
        Assert.isTrue(activityMsgDao.updateActivityMsg(activityMsg) == 1, "操作失败");
    }

    public void delActivityMsg(Integer id) {
        Assert.isTrue(activityMsgDao.delActivityMsg(id) == 1, "操作失败");
    }

    public List<ActivityMsg> findAvailableActivityMsgList(Integer type) {
        return activityMsgDao.findAvailableActivityMsgList(type);
    }

    public Integer findActivityMsgStatus(Integer amsort, String[] idList){
        return activityMsgDao.findActivityMsgStatus(amsort, idList);
    }

    public ActivityMsg findAvailableActivityMsg(Integer id) {
        return activityMsgDao.findAvailableActivityMsg(id);
    }
}

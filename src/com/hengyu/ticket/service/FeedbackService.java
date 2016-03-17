package com.hengyu.ticket.service;

import com.hengyu.ticket.dao.FeedbackDao;
import com.hengyu.ticket.dao.FeedbackDetailDao;
import com.hengyu.ticket.entity.Feedback;
import com.hengyu.ticket.entity.FeedbackDetail;
import com.hengyu.ticket.entity.Page;
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
public class FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;
    @Autowired
    private FeedbackDetailDao feedbackDetailDao;

    public List<Feedback> findFeedbackList(Feedback feedback, Page page) {
        page.setTotalCount(feedbackDao.findFeedbackTotal(feedback));
        return feedbackDao.findFeedbackList(feedback, page);
    }

    public Feedback findFeedback(Integer id) {
        Feedback feedback = feedbackDao.findFeedback(id);
        Assert.notNull(feedback, "无效问题");
        feedback.setFeedbackDetailList(feedbackDetailDao.findFeedbackDetailList(id));
        return feedback;
    }

    public void addFeedback(Feedback feedback) {
        Assert.isTrue(feedbackDao.saveFeedback(feedback) == 1, "操作失败");
        FeedbackDetail feedbackDetail = new FeedbackDetail();
        feedbackDetail.setFeedbackid(feedback.getId());
        feedbackDetail.setType(1);
        feedbackDetail.setContent(feedback.getContent());
        feedbackDetail.setReplyid(feedback.getCid());
        feedbackDetail.setReplyname(feedback.getCname());
        feedbackDetail.setIndex(1);
        feedbackDetail.setMakedate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Assert.isTrue(feedbackDetailDao.saveFeedbackDetail(feedbackDetail) == 1, "操作失败");
    }

    public void updateFeedback(Feedback feedback) {
        Assert.isTrue(feedbackDao.updateFeedback(feedback) == 1, "操作失败");
    }

    public void updateFeedbackForComplete(Integer id) {
        Feedback feedback = feedbackDao.findFeedback(id);
        Assert.isTrue(feedback != null && feedback.getStatus() == 1, "无效问题");
        feedback.setStatus(2);
        Assert.isTrue(feedbackDao.updateFeedback(feedback) == 1, "操作失败");
    }

    public void delFeedback(Integer id) {
        Assert.isTrue(feedbackDao.delFeedback(id) == 1, "操作失败");
        Assert.isTrue(feedbackDetailDao.delAllFeedbackDetail(id) > 0, "操作失败");
    }

    public void addFeedbackDetail(FeedbackDetail feedbackDetail) {
        Feedback feedback = feedbackDao.findFeedback(feedbackDetail.getFeedbackid());
        Assert.isTrue(feedback.getStatus() == 1, "问题已关闭");
        Integer index = feedbackDetailDao.findFeedbackDetailMaxIndex(feedbackDetail.getFeedbackid());
        Assert.isTrue(index > 0, "无效问题");
        feedbackDetail.setIndex(++index);
        Assert.isTrue(feedbackDetailDao.saveFeedbackDetail(feedbackDetail) == 1, "操作失败");
        if (feedbackDetail.getType() == 2) {
            feedback.setLastindexforcustomer(index);
            updateFeedback(feedback);
        }
    }

    public int findFeedbackStatusForCustomer(String cid) {
        return feedbackDao.findFeedbackStatusForCustomer(cid);
    }

    public int getAllFeedback() {
        return feedbackDao.getAllFeedback();
    }


    public void delFeedbackDetail(Integer id) {
        Assert.isTrue(feedbackDetailDao.delFeedbackDetail(id) == 1, "操作失败");
    }
}

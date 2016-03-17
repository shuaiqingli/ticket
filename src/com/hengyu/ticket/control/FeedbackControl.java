package com.hengyu.ticket.control;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.Feedback;
import com.hengyu.ticket.entity.FeedbackDetail;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Wang Wu on 2015-12-29.
 */
@Controller
@RequestMapping("/admin")
public class FeedbackControl {

    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping("feedbackList.do")
    public String feedbackList(Page page, Feedback feedback, Model m) {
        feedback.setCid(null);
        m.addAttribute("feedbackList", feedbackService.findFeedbackList(feedback, page));
        m.addAttribute("page", page);
        return "admin/feedbackList";
    }

    @ResponseBody
    @RequestMapping("feedbackDel.do")
    public String feedbackDel(Integer id) {
        Assert.notNull(id, "ID不能为空");
        feedbackService.delFeedback(id);
        return "1";
    }

    @RequestMapping("feedbackDetail.do")
    public String feedbackDetail(Integer id, Model m) {
        Assert.notNull(id, "ID不能为空");
        Feedback feedback = feedbackService.findFeedback(id);
        /*List<FeedbackDetail> feedbackDetailList = feedback.getFeedbackDetailList();
        Integer lastIndex = feedbackDetailList.get(feedbackDetailList.size() - 1).getIndex();
        if (feedback.getLastindexforcustomer() < lastIndex) {
            feedback.setLastindexforcustomer(lastIndex);
            feedbackService.updateFeedback(feedback);
        }*/
        m.addAttribute("feedback", feedback);
        return "admin/feedbackDetail";
    }

    @ResponseBody
    @RequestMapping("feedbackReply.do")
    public String feedbackReply(FeedbackDetail feedbackDetail, HttpServletRequest request) {
        Assert.notNull(feedbackDetail.getFeedbackid(), "ID不能为空");
        Assert.hasText(feedbackDetail.getContent(), "内容不能为空");
        Admin admin = (Admin) request.getSession().getAttribute(Const.LOGIN_USER);
        feedbackDetail.setType(2);
        feedbackDetail.setReplyid(admin.getUserID());
        feedbackDetail.setReplyname(admin.getRealName());
        feedbackDetail.setMakedate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        feedbackService.addFeedbackDetail(feedbackDetail);
        return "1";
    }

    @ResponseBody
    @RequestMapping("feedbackComplete.do")
    public String feedbackComplete(Integer id) {
        Assert.notNull(id, "ID不能为空");
        feedbackService.updateFeedbackForComplete(id);
        return "1";
    }

    @ResponseBody
    @RequestMapping("feedbackDetailDel.do")
    public String feedbackDetailDel(Integer id) {
        Assert.notNull(id, "ID不能为空");
        feedbackService.delFeedbackDetail(id);
        return "1";
    }
}

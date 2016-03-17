package com.hengyu.ticket.api.station;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.Feedback;
import com.hengyu.ticket.entity.FeedbackDetail;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.FeedbackService;
import com.hengyu.ticket.util.APIStatus;
import com.hengyu.ticket.util.RequestTool;

@Controller
public class APIFeedbackControl {

	@Autowired
	private FeedbackService feedbackService;

	// 留言
	@RequestMapping(value = "/api/station/listFeedback", method = RequestMethod.POST)
	@ResponseBody
	public String listFeedback(Feedback feedback, Page page, HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String content = request.getParameter("content");
		feedback.setCid(null);
		feedback.setContent(content);
		List<Feedback> fb = feedbackService.findFeedbackList(feedback, page);
		result.put("feedback", fb);
		result.put("waitfeedback", feedbackService.getAllFeedback());

		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 留言详情
	@RequestMapping(value = "/api/station/feedbackDetail", method = RequestMethod.POST)
	@ResponseBody
	public String feedbackDetail(Integer id, HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Feedback feedback = feedbackService.findFeedback(id);
		/*List<FeedbackDetail> feedbackDetailList = feedback.getFeedbackDetailList();
		Integer lastIndex = feedbackDetailList.get(feedbackDetailList.size() - 1).getIndex();
		if (feedback.getLastindexforcustomer() < lastIndex) {
			feedback.setLastindexforcustomer(lastIndex);
			feedbackService.updateFeedback(feedback);
		}*/
		result.put("feedback", feedback);
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 留言回复
	@RequestMapping(value = "/api/station/feedbackReply", method = RequestMethod.POST)
	@ResponseBody
	public String feedbackReply(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer feedbackid = RequestTool.getInt(request, "feedbackid", 0);
		String content = request.getParameter("content");
		FeedbackDetail feedbackDetail = new FeedbackDetail();
		feedbackDetail.setFeedbackid(feedbackid);
		feedbackDetail.setContent(content);

		Map<String, Object> amap = (Map<String, Object>) request.getAttribute("amap");
		String userid = amap.get("userid").toString();
		String realname = amap.get("realname").toString();
		// Admin admin = (Admin)
		// request.getSession().getAttribute(Const.LOGIN_USER);
		feedbackDetail.setType(2);
		feedbackDetail.setReplyid(userid);
		feedbackDetail.setReplyname(realname);
		feedbackDetail.setMakedate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		feedbackService.addFeedbackDetail(feedbackDetail);

		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 留言完成
	@RequestMapping(value = "/api/station/feedbackComplete", method = RequestMethod.POST)
	@ResponseBody
	public String feedbackComplete(Integer id, HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		feedbackService.updateFeedbackForComplete(id);

		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

	// 删除留言
	@RequestMapping(value = "/api/station/feedbackDetailDel", method = RequestMethod.POST)
	@ResponseBody
	public String feedbackDel(Integer id, HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		feedbackService.delFeedbackDetail(id);

		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

}

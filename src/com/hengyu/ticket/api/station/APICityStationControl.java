package com.hengyu.ticket.api.station;

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
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.service.CityStationService;
import com.hengyu.ticket.util.APIStatus;
import com.hengyu.ticket.util.DateUtil;
import com.hengyu.ticket.util.SecurityHanlder;

@Controller
public class APICityStationControl {

	@Autowired
	private CityStationService citystationService;

	// 选择城市站点
	@RequestMapping(value = "/api/station/selectStation", method = RequestMethod.POST)
	@ResponseBody
	public String selectStation(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String keywords = request.getParameter("keywords");
		String parentid = request.getParameter("parentid");
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("keywords", keywords);
		a.put("parentid", parentid);
		List pls = null;
		pls = citystationService.getCityStationByKeywords(a);
		result.put("data", pls);
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

}

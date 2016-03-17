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
import com.hengyu.ticket.service.DriverService;
import com.hengyu.ticket.util.APIStatus;

@Controller
public class APIDriverControl {
	// 车牌服务类
	@Autowired
	private DriverService driverService;

	// 发车
	@RequestMapping(value = "/api/station/selectDriver", method = RequestMethod.POST)
	@ResponseBody
	public String selectDriver(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String keywords = request.getParameter("keywords");
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("keywords", keywords);
		List pls = null;
		if (keywords != null && !keywords.equals("")) {
			pls = driverService.getDriverByKeywords(a);
		}
		result.put("data", pls);
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		return JSON.toJSONString(result);
	}

}

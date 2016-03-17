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
import com.hengyu.ticket.entity.SoftUpgrade;
import com.hengyu.ticket.service.SoftUpgradeService;

@Controller
public class APISoftUpgradeControl {

	// 版本服务类
	@Autowired
	private SoftUpgradeService softupgradeService;

	// 查找新版本
	@RequestMapping(value = "/api/public/getsoftupgrade", method = RequestMethod.POST)
	@ResponseBody
	public String getSoftUpgrade(HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List pls = softupgradeService.getNewVersion(0);
		if (pls.size() > 0) {
			SoftUpgrade su = (SoftUpgrade) pls.get(0);
			result.put("data", su);
			result.put("status", "00");
			result.put("info", "找到新版本");
		} else {
			result.put("status", "01");
			result.put("info", "没有发现新版本");
		}
		return JSON.toJSONString(result);
	}

}

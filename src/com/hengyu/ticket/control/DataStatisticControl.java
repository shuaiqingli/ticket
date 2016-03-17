package com.hengyu.ticket.control;

import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hengyu.ticket.service.DataStatisticService;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.WeixinHanlder;

@Controller
@RequestMapping(value={"/user","/public"})
public class DataStatisticControl {
	
	@Autowired
	private DataStatisticService dss;
	
	//统计获取数据
	@RequestMapping("getDataStatistic")
	public void getDataStatistic(HttpServletRequest req,Writer w) throws Exception{
		Map<String, Object> map = dss.getDataStatistic();
		Map<String, String> userlist = WeixinHanlder.getUserList(req);
		if(userlist!=null){
			String count = String.valueOf(userlist.get("total"));
			count = count==null ? "0":count;
			map.put("total", count);
		}
		APIUtil.toJSONString(map, w);
	}

}

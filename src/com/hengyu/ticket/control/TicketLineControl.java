package com.hengyu.ticket.control;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.TicketLine;
import com.hengyu.ticket.service.TicketLineService;
import com.hengyu.ticket.util.APIUtil;

@Controller
@RequestMapping("/user")
public class TicketLineControl {
	
	@Autowired
	private TicketLineService ticketLineService;
	
	
	//更新车票线路价格
	@RequestMapping("updateTicketLinePrice")
	@ResponseBody
	public String updatePrice(HttpServletRequest req,String json) throws Exception{
		int result = Const.ERROR_CODE;
		if(!StringUtils.isNotEmpty(json)){
			return result+"";
		}else{
			List ids = JSONArray.parseObject(APIUtil.getJSONVal("ids", json),List.class);
			List prices = JSONArray.parseObject(APIUtil.getJSONVal("prices", json),List.class);
			result = ticketLineService.updateprice(ids, prices);
		}
		return result+"";
		
	} 
	
	//获取该线路最后审核的日期
	@RequestMapping("getMaxApproveTicketDate")
	public void getMaxApproveTicketDate(TicketLine tl,Writer w) throws Exception{
		String date = ticketLineService.getCheckApproveTicketDate(tl);
		APIUtil.toJSONString(date, w);
	}
	

}

package com.hengyu.ticket.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.LineManageService;


@Controller
@RequestMapping("/user")
public class TickectConfigControl {
	
	//线路管理
	@Autowired
	private LineManageService lms;

	//票务设置
	@RequestMapping(value={"tickectconfig"})
	public String lineManage(Page page,LineManage lm,Model m,HttpServletRequest req) throws Exception{
		if(lm!=null){
			m.addAttribute(lm);
			page.setParam(lm);
		}
		page.setAdmin(req.getSession().getAttribute(Const.LOGIN_USER));
		m.addAttribute(lms.findList(page));
		return "user/tickectconfig";
	}
	
	
}

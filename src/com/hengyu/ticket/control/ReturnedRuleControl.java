package com.hengyu.ticket.control;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.RefundRule;
import com.hengyu.ticket.entity.RefundRuleTime;
import com.hengyu.ticket.exception.BaseException;
import com.hengyu.ticket.service.RefundRuleService;
import com.hengyu.ticket.service.RefundRuleTimeService;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.RequestTool;

/**
 * 退票规则列表
 * @author LGF
 *
 */
@Controller
@RequestMapping("user")
public class ReturnedRuleControl {
	
	@Autowired
	private RefundRuleService rrs;
	@Autowired
	private RefundRuleTimeService rrts;
	
	@ModelAttribute
	public void before(Integer id,Model m) throws Exception{
		if(id!=null){
			RefundRule rule = rrs.find(id);
			if(rule!=null){
				m.addAttribute(rule);
				m.addAttribute("rule",rule);
			}
		}
	}

	//退款规则列表
	@RequestMapping("returnedRuleList")
	public String returnedRuleList(Page page,Model m,HttpServletRequest req) throws Exception{
		Map<String, Object> params = RequestTool.paramsAsMap(req);
		page.setParam(params);
		List<RefundRule> list = rrs.findlist(page);
		page.setData(list);
		m.addAttribute(page);
		return "user/returned_rule_list";
	} 
	
	//详情
	@RequestMapping("returnedRuleEdit")
	public String editPage(RefundRule r,Model m,Page page) throws Exception{
		if(r!=null){
			page.setParam(new HashMap<String, Object>());
			List<RefundRuleTime> times = rrts.findByRule(r.getId());
			List<LineManage> lines = rrs.findLineManageByRule(page,r.getId(), 0);
			if(times!=null){
				m.addAttribute("times",times);
			}
			if(lines!=null){
				m.addAttribute("lines",lines);
			}
		}else{
			throw new BaseException(Const.HTTP_MSG_NOT_FOUND,404);
		}
		return "user/returned_rule_edit";
	}
	
	//选择线路
	@RequestMapping("selectRefundRuleLine")
	public String selectRefundRuleLine(Integer rrid,Integer type,Model m,Page page,HttpServletRequest req) throws Exception{
		Assert.isTrue(rrid!=null,"无效编号");
		Map<String, Object> params = RequestTool.paramsAsMap(req);
		page.setParam(params);
		page.setAdmin(req.getSession().getAttribute(Const.LOGIN_USER));
		List<LineManage> lines = rrs.findLineManageByRule(page,rrid, 1);
		page.setData(lines);
		m.addAttribute("lines",lines);
		m.addAttribute(page);
		return "user/select_line";
	}
	
	//编辑规则
	@RequestMapping("editReturnedRule")
	@ResponseBody
	public String edit(RefundRule rule,HttpServletRequest req) throws Exception{
		Admin admin = (Admin) req.getSession().getAttribute(Const.LOGIN_USER);
		SimpleDateFormat df = DateHanlder.getDateFromat();
		Assert.isTrue(df.parse(rule.getBegindate()).getTime()<df.parse(rule.getEnddate()).getTime(),"开始时间不能小于结束时间");
		if(rule!=null&&rule.getId()==null){
			rule.setMakeid(admin.getUserID());
			rule.setMakedate(DateHanlder.getCurrentDate());
			rule.setMakename(admin.getRealName());
			rrs.save(rule);
		}else if(rule!=null&&rule.getId()!=null){
			rrs.update(rule);
		}else{
			Assert.isTrue(false);
		}
		return "1";
	}
	
	//绑定线路列表
	@RequestMapping("bindRefundRuleLines")
	@ResponseBody
	public String bindLines(RefundRule rule) throws Exception{
		int r = rrs.saveLines(rule);
		return String.valueOf(r);
	}
	
	//绑定线路列表
	@RequestMapping("unbindRefundRuleLine")
	@ResponseBody
	public String unbindRefundRuleLine(Integer rrid,Integer lmid) throws Exception{
		Assert.notNull(rrid,"规则编号不能为空！");
		Assert.notNull(lmid,"线路编号不能为空！");
		int r = rrs.deleteline(rrid,lmid);
		return String.valueOf(r);
	}
	
	//删除规则
	@RequestMapping("deleteRefundRule")
	@ResponseBody
	public String delete(Integer id) throws Exception{
		Assert.notNull(id,"编号不能为空");
		return String.valueOf(rrs.delete(id));
	}
	
	
}

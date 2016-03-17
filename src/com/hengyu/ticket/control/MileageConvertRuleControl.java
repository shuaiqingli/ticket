package com.hengyu.ticket.control;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengyu.ticket.entity.CouponsRule;
import com.hengyu.ticket.entity.MileageConvertRule;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.MileageConvertRuleService;
import com.hengyu.ticket.util.DateUtil;
import com.hengyu.ticket.util.RequestTool;

@Controller
@RequestMapping("/admin")
public class MileageConvertRuleControl {

	// 优惠规则服务类
	@Autowired
	private MileageConvertRuleService mileageconvertruleService;

	// 列表里程兑换规则
	@RequestMapping("mileageconvertrulelist.do")
	public String couponsrulelist(Page page, Model m, HttpServletRequest request) throws Exception {
		String keywords = request.getParameter("keywords");
		page.setParam(keywords);
		mileageconvertruleService.findList(page);
		m.addAttribute(page);
		return "admin/mileageconvertrulelist";
	}

	// 新增里程换券规则
	@RequestMapping("mileageconvertruletoadd.do")
	public String mileageconvertruletoadd(HttpServletRequest request, Model m) throws Exception {
		return "admin/mileageconvertruleadd";
	}

	// 新增里程换券规则
	@RequestMapping("mileageconvertruleadd.do")
	@ResponseBody
	public String mileageconvertruleadd(MileageConvertRule m) throws Exception {
		if (m.getIsenable() == 0) {
			m.setIsenablename("否");
		} else if (m.getIsenable() == 1) {
			m.setIsenablename("是");
		}
		m.setMakedate(DateUtil.getCurrentDateTime());
		int crins = mileageconvertruleService.save(m);
		return String.valueOf(crins);
		// return "redirect:couponsrulelist.do";
	}

	// 修改里程换券规则
	@RequestMapping("mileageconvertruletoedit.do")
	public String mileageconvertruletoedit(HttpServletRequest request, Model m) throws Exception {
		Integer id = RequestTool.getInt(request, "id", 0);
		MileageConvertRule mc = mileageconvertruleService.find(id);
		m.addAttribute("mc", mc);
		return "admin/mileageconvertruleedit";
	}

	// 修改里程换券规则
	@RequestMapping("mileageconvertruleedit.do")
	@ResponseBody
	public String mileageconvertruleedit(MileageConvertRule cr) throws Exception {
		MileageConvertRule tmp = mileageconvertruleService.find(cr.getId());
		tmp.setIsenable(cr.getIsenable());
		if (cr.getIsenable() == 0) {
			tmp.setIsenablename("否");
		} else if (cr.getIsenable() == 1) {
			tmp.setIsenablename("是");
		}
		tmp.setMileagecvt(cr.getMileagecvt());
		tmp.setVprice(cr.getVprice());
		// cr.setMakedate(DateUtil.getCurrentDateTime());
		int crins = mileageconvertruleService.update(tmp);
		return String.valueOf(crins);
		// return "redirect:couponsrulelist.do";
	}

	// 删除优惠券规则
	@RequestMapping("mileageconvertruledel.do")
	public String mileageconvertruledel(HttpServletRequest request, Model m) throws Exception {
		Integer id = RequestTool.getInt(request, "id", 0);
		mileageconvertruleService.delete(id);
		return "redirect:mileageconvertrulelist.do";
	}

}

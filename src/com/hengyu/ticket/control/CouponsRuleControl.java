package com.hengyu.ticket.control;

import javax.servlet.http.HttpServletRequest;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.Coupons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengyu.ticket.entity.CouponsRule;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.CouponsRuleService;
import com.hengyu.ticket.util.DateUtil;
import com.hengyu.ticket.util.RequestTool;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
public class CouponsRuleControl {

	// 优惠规则服务类
	@Autowired
	private CouponsRuleService couponsruleService;

	// 列表优惠券规则
	@RequestMapping("couponsrulelist.do")
	public String couponsrulelist(Page page, Model m, HttpServletRequest request) throws Exception {
		String keywords = request.getParameter("keywords");
		page.setParam(keywords);
		couponsruleService.findList(page);
		m.addAttribute(page);
		return "admin/couponsrulelist";
	}

	// 新增优惠券规则
	@RequestMapping("couponsruletoadd.do")
	public String couponsruletoadd(HttpServletRequest request, Model m) throws Exception {
		return "admin/couponsruleadd";
	}

	// 新增优惠券规则
	@RequestMapping("couponsruleadd.do")
	@ResponseBody
	public String couponsruleadd(CouponsRule cr) throws Exception {
		if (cr.getVsort() == 1) {
			cr.setVsortname("注册送券");
		} else if (cr.getVsort() == 2) {
			cr.setVsortname("满额送券");
		} else if (cr.getVsort() == 3) {
			cr.setVsortname("满额送红包");
			cr.setIslimitgive(1);
		}
		if (cr.getIsenable() == 0) {
			cr.setIsenablename("否");
		} else if (cr.getIsenable() == 1) {
			cr.setIsenablename("是");
		}
		cr.setIsdel(0);
		cr.setMakedate(DateUtil.getCurrentDateTime());
		int crins = couponsruleService.save(cr);
		return String.valueOf(crins);
		// return "redirect:couponsrulelist.do";
	}

	// 修改优惠券规则
	@RequestMapping("couponsruletoedit.do")
	public String couponsruletoedit(HttpServletRequest request, Model m) throws Exception {
		Integer id = RequestTool.getInt(request, "id", 0);
		CouponsRule cr = couponsruleService.find(id);
		m.addAttribute("cr", cr);
		return "admin/couponsruleedit";
	}

	// 修改优惠券规则
	@RequestMapping("couponsruleedit.do")
	@ResponseBody
	public String couponsruleedit(CouponsRule cr) throws Exception {
		CouponsRule tmp = couponsruleService.find(cr.getId());
		tmp.setRulename(cr.getRulename());
		tmp.setVsort(cr.getVsort());
		if (cr.getVsort() == 1) {
			tmp.setVsortname("注册送券");
		} else if (cr.getVsort() == 2) {
			tmp.setVsortname("满额送券");
		} else if (cr.getVsort() == 3) {
			tmp.setVsortname("满额送红包");
			tmp.setIslimitgive(1);
		}
		tmp.setIsenable(cr.getIsenable());
		if (cr.getIsenable() == 0) {
			tmp.setIsenablename("否");
		} else if (cr.getIsenable() == 1) {
			tmp.setIsenablename("是");
		}
		tmp.setBegindate(cr.getBegindate());
		tmp.setEnddate(cr.getEnddate());
		tmp.setValidday(cr.getValidday());
		tmp.setBuysum(cr.getBuysum());
		tmp.setLowlimit(cr.getLowlimit());
		tmp.setVprice(cr.getVprice());
		// cr.setMakedate(DateUtil.getCurrentDateTime());
		int crins = couponsruleService.update(tmp);
		return String.valueOf(crins);
		// return "redirect:couponsrulelist.do";
	}

	// 删除优惠券规则
	@RequestMapping("couponsruledel.do")
	public String couponsruledel(HttpServletRequest request, Model m) throws Exception {
		Integer id = RequestTool.getInt(request, "id", 0);
		CouponsRule cr = couponsruleService.find(id);
		cr.setIsdel(1);
		couponsruleService.update(cr);
		return "redirect:couponsrulelist.do";
	}

	//送券列表页
	@RequestMapping("couponLineList.do")
	public String couponLineList(String keyword, Page page, Model m) {
		m.addAttribute("couponLineList", couponsruleService.findCouponLineList(keyword, page));
		m.addAttribute("page", page);
		return "admin/couponLineList";
	}

	//手动送券页
	@RequestMapping("couponAddPage.do")
	public String couponAddPage() {
		return "admin/couponAdd";
	}

	//手动送券
	@ResponseBody
	@RequestMapping("couponAdd.do")
	public String couponAdd(Coupons coupons, Integer validday, String remark, HttpServletRequest request) throws Exception {
		Assert.hasText(coupons.getCid(), "客户不能为空");
		Assert.hasText(coupons.getCname(), "客户不能为空");
		Assert.isTrue(coupons.getVprice() != null && coupons.getVprice().compareTo(new BigDecimal(0)) > 0, "无效面值");
		Assert.isTrue(coupons.getLowlimit() != null && coupons.getLowlimit().compareTo(new BigDecimal(0)) >= 0, "无效最低消费");
		Assert.isTrue(validday != null && validday > 0, "无效有效天数");
		coupons.setVouchercode(Long.toHexString(Long.valueOf(DateUtil.getCurrentLongDateTimeString())).toUpperCase());
		coupons.setVouchername(coupons.getVprice() + "元优惠券");
		coupons.setMemo("客服送券");
		coupons.setBegindate(DateUtil.getCurrentDateString());
		coupons.setEnddate(DateUtil.formatDate(
				DateUtil.calculatedays(DateUtil.getCurrentDate(), validday)));
		coupons.setIsuse(1);
		coupons.setIsusename("可用");
		coupons.setMakedate(DateUtil.getCurrentDateTime());
		couponsruleService.updateForAddCoupon(coupons, (Admin) request.getSession().getAttribute(Const.LOGIN_USER), remark);
		return "1";
	}

}

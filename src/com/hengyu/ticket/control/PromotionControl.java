package com.hengyu.ticket.control;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.Promotion;
import com.hengyu.ticket.service.LineManageService;
import com.hengyu.ticket.service.PromotionLineService;
import com.hengyu.ticket.service.PromotionService;
import com.hengyu.ticket.service.PromotionTimeService;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.DateUtil;
import com.hengyu.ticket.util.RequestTool;

@Controller
@RequestMapping("/user")
public class PromotionControl {
	// 促销规则服务类
	@Autowired
	private PromotionService promotionService;
	@Autowired
	private PromotionLineService promotionlineService;
	@Autowired
	private LineManageService linemanageService;
	@Autowired
	private PromotionTimeService promotiontimeService;

	// 列表促销规则
	@RequestMapping("promotionlist.do")
	public String promotionlist(Page page, Model m, HttpServletRequest request,Promotion p) throws Exception {
		LineManage lm = linemanageService.find(p.getLmid());
		if(lm!=null){
			m.addAttribute("lm", lm);
		}

        page.setParam(p);
        promotionService.findList(page);

        m.addAttribute(page);
		return "user/promotionlist";
	}

	// 新增促销规则
	@RequestMapping("promotiontoadd.do")
	public String couponsruletoadd(HttpServletRequest request, Model m,Promotion p) throws Exception {
		LineManage lm = linemanageService.find(p.getLmid());
		if(lm!=null){
			m.addAttribute("lm", lm);
		}
		return "user/promotionadd";
	}

	// 新增促销规则
	@RequestMapping("promotionadd.do")
	public void promotionadd(HttpServletRequest request, Promotion p,Integer[] weekdays,Integer[] couponpercent ,Writer w) throws Exception {
		/*Promotion e = promotionService.findChecketPromotionExists(p);
		if(e!=null){
			APIUtil.toJSONString("发现优惠规则日期冲突："+e.getBegindate()+" 至 "+e.getEnddate(), w);
			return;
			
		}*/
		Admin admin = (Admin) request.getSession().getAttribute(Const.LOGIN_USER);
		p.setId(DateUtil.getCurrentLongDateTimeString());
		if (p.getIsenable() == 0) {
			p.setIsenablename("否");
		} else if (p.getIsenable() == 1) {
			p.setIsenablename("是");
		}
		p.setMakeid(admin.getUserID());
		p.setMakename(admin.getRealName());
		p.setMakedate(DateUtil.getCurrentDateTime());
		p.setIsdel(0);
		// 添加时间
		String[] begintime = request.getParameterValues("begintime");
		String[] endtime = request.getParameterValues("endtime");
		double[] reducesum = null;
		if(begintime!=null&&begintime.length!=0){
			reducesum = RequestTool.getDoubles(request, "reducesum");
		}
		int sum = 0;
		if(weekdays!=null){
			for (int i = 0; i < weekdays.length; i++) {
				sum+=weekdays[i];
			}
		}
//		if(sum==0){
//			APIUtil.toJSONString(Const.ERROR_CODE.toString(),w);
//			return;
//		}
		p.setWeekdays(sum);
		int crins = promotionService.save(p,begintime,endtime,reducesum,couponpercent );
		APIUtil.toJSONString(String.valueOf(crins),w);
		// return "redirect:couponsrulelist.do";
	}

	// 修改促销规则
	@RequestMapping("promotiontoedit.do")
	public String promotiontoedit(HttpServletRequest request, Model m) throws Exception {
		String id = request.getParameter("id");
		Promotion p = promotionService.find(id);
		m.addAttribute("p", p);
		List tlist = promotiontimeService.getPromotionTimeByPID(id);
		m.addAttribute("tlist", tlist);
		return "user/promotionedit";
	}

	// 修改促销规则
	@RequestMapping("promotionedit.do")
	public void promotionedit(HttpServletRequest request,Promotion p,Integer[] couponpercent,Writer w) throws Exception {
		/*Promotion e = promotionService.findChecketPromotionExists(p);
		if(e!=null){
			APIUtil.toJSONString("发现优惠规则日期冲突："+e.getBegindate()+" 至 "+e.getEnddate(), w);
			return;
			
		}*/
		Promotion tmp = promotionService.find(p.getId());
		tmp.setPromotionname(p.getPromotionname());
		tmp.setBegindate(p.getBegindate());
		tmp.setEnddate(p.getEnddate());
		tmp.setWeekdays(p.getWeekdays());
		tmp.setConsamount(p.getConsamount());
        tmp.setIsdefault(p.getIsdefault());
//		tmp.setReducesum(p.getReducesum());
		tmp.setIsenable(p.getIsenable());
		if (p.getIsenable() == 0) {
			tmp.setIsenablename("否");
		} else if (p.getIsenable() == 1) {
			tmp.setIsenablename("是");
		}
		// 添加时间
		String[] begintime = request.getParameterValues("begintime");
		String[] endtime = request.getParameterValues("endtime");
		double[] reducesum = null;
		if(begintime!=null&&begintime.length!=0){
			reducesum = RequestTool.getDoubles(request, "reducesum");
		}
		int crins = promotionService.update(tmp,begintime,endtime,reducesum,couponpercent);
		APIUtil.toJSONString(String.valueOf(crins), w);
		// return "redirect:couponsrulelist.do";
	}

	// 促销规则详情
	@RequestMapping("promotiondetail.do")
	public String promotiondetail(HttpServletRequest request, Model m) throws Exception {
		String id = request.getParameter("id");
		Promotion p = promotionService.find(id);
		m.addAttribute("p", p);
		List tlist = promotiontimeService.getPromotionTimeByPID(id);
		m.addAttribute("tlist", tlist);
		// List plist = promotionlineService.getPromotionLineByPID(id);
		// System.out.println("---------------" + plist.size() + id);
		// ArrayList alist = new ArrayList();
		// for (int i = 0; i < plist.size(); i++) {
		// PromotionLine pl = new PromotionLine();
		// PromotionLine o = (PromotionLine) plist.get(i);
		// pl.setId(o.getId());
		// pl.setPid(o.getPid());
		// pl.setLmid(o.getLmid());
		// pl.setLineid(linemanageService.find(o.getLmid()).getLineid());
		// alist.add(pl);
		// }
		// m.addAttribute("alist", alist);
		return "user/promotiondetail";
	}

	// 删除促销规则
	@RequestMapping("promotiondel.do")
	public String promotiondel(HttpServletRequest request, Model m,Integer lmid,Integer type) throws Exception {
		String id = request.getParameter("id");
		Assert.notNull(id,"编号不能为空！");
		Assert.notNull(lmid,"线路编号不能为空！");
		Promotion cr = promotionService.find(id);
		cr.setIsdel(1);
		promotionService.update(cr,null,null,null,null);
        if(type!=null&&type==1){
		    return "redirect:lineadd.do?id="+lmid+"#6";
        }
        return "redirect:promotionlist.do?lmid="+lmid;
	}

	// 促销规则指定线路
	@RequestMapping("promotionline.do")
	public String promotionline(HttpServletRequest request, Model m) throws Exception {
		String id = request.getParameter("id");
		m.addAttribute("pid", id);
		return "user/promotionline";
	}

	// 读取线路
	@RequestMapping(value = "getlinemanagejson.do", method = RequestMethod.POST)
	@ResponseBody
	public String getlinemanagejson(HttpServletRequest request) throws Exception {
		int startpage = RequestTool.getInt(request, "startpage", 1);
		int pagesize = 20;
		Map<String, Object> a = new HashMap<String, Object>();
		a.put("startpage", startpage * pagesize);
		a.put("pagesize", pagesize);
		List lmlist = linemanageService.getAllLine(a);
		System.out.println("------------------" + JSON.toJSONString(lmlist));
		return JSON.toJSONString(lmlist);
	}

}

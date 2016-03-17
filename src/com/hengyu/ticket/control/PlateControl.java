package com.hengyu.ticket.control;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.service.CompanyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.service.BaseResourceService;
import com.hengyu.ticket.service.PlateService;

/**
 * 车牌管理
 * @author LGF
 *
 */
@Controller
@RequestMapping("/user")
public class PlateControl {
	
	@Autowired
	private PlateService plateService;
	
	//基本资源
	@Autowired
	private BaseResourceService brs;
	@Autowired
	private CompanyService companyService;
	
	//车牌号列表
	@RequestMapping(value="platemanage.do")
	public String driverPage(Page page,Plate p,Model m,HttpServletRequest req) throws Exception{
		page.setParam(p);
		page.setAdmin(req.getSession().getAttribute(Const.LOGIN_USER));
		page.setData(plateService.findlist(page));
		m.addAttribute(page);
		return "user/plate";
	}
	
	//编辑页面
	@RequestMapping("plateadd.do")
	public String editPlatePage(Plate p,Model m) throws Exception{
		if(p!=null&&p.getId()!=null){
			Plate d = plateService.find(p.getId());
			m.addAttribute(d);
		}
		
		Page page = new Page();
		page.setPageSize(Integer.MAX_VALUE);
		BaseResource bs = new BaseResource();
		bs.setTagname(Const.BASE_RESOURCE_CARMODEL);
		page.setParam(bs);
		List<BaseResource> rslist = brs.findlist(page);
		if(rslist!=null){
			m.addAttribute("cars",rslist);
		}
		
		return "user/plateedit";
	}
	
	//保存或更新
	@RequestMapping("editplate.do")
	@ResponseBody
	public String editPlate(Plate p,HttpServletRequest request) throws Exception{
		int result = -1;
		Admin  admin= (Admin) request.getSession().getAttribute(Const.LOGIN_USER);
		Assert.notNull(admin.getTcid(),"当前登录用户没有绑定公司，请绑定公司后操作！");
		if(p!=null){
			p.setTcid(admin.getTcid());
			p.setCompanyname(companyService.find(admin.getTcid()).getCompanyname());
			if(p.getCarmodelid()!=null){
				BaseResource br = brs.find(p.getCarmodelid());
				p.setCarmodelname(br.getTagsubvalue());
			}
			if(p.getId()!=null){
				result = plateService.update(p);
			}else{
				result = plateService.save(p);
			}
		}
		return String.valueOf(result);
	}
	
	//删除
	@RequestMapping("delplate.do")
	@ResponseBody
	public String delete(Integer id) throws Exception{
		Integer result = -1;
		if(id!=null){
			result = plateService.delete(id);
		}
		return String.valueOf(result);
	}

	//车牌选取页
	@RequestMapping(value = "selectPlate.do")
	public String selectPlate(Page page, Plate plate, Model m) {
		page.setParam(plate);
		page.setData(plateService.findListForBindLine(page));
		m.addAttribute(page);
		return "user/selectPlate";
	}
	
}

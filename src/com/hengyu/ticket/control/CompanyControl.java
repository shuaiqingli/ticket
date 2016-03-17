package com.hengyu.ticket.control;

import java.io.Writer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Company;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.CompanyService;
import com.hengyu.ticket.util.APIUtil;

@Controller
@RequestMapping("/admin")
public class CompanyControl {

	@Autowired
	private CompanyService cs;
	
	
	@ModelAttribute
	public void before(Integer id,Model m) throws Exception{
		if(id!=null){
			Company c = cs.find(id);
			if(c!=null){
				m.addAttribute(c);
			}else{
				m.addAttribute("isnull", true);
			}
		}
	}
	
	
	//公司列表
	@RequestMapping("companylist")
	public String companylist(Page page,Company c,String type,Model m,Writer w) throws Exception{
		page.setParam(c);
		cs.findList(page);
		if(type!=null&&type.equals("json")){
			APIUtil.toJSONString(page, w);
		}else{
			m.addAttribute(page);
		}
		return "admin/companylist";
	}
	
	//详情
	@RequestMapping("companydetail")
	public String companyeditpage(Company c,Model m) throws Exception{
		Assert.isTrue(!m.containsAttribute("isnull"), Const.HTTP_MSG_NOT_FOUND);
		return "admin/companyedit";
	}
	
	//详情
	@RequestMapping("companyedit")
	@ResponseBody
	public String companyedit(Company c,Model m) throws Exception{
		int r = -1;
		c.setPyname(PinyinHelper.convertToPinyinString(c.getCompanyname(), "", PinyinFormat.WITHOUT_TONE));
		if(c.getId()==null){
			r = cs.save(c);
		}else if(c.getId()!=null){
			r = cs.update(c);
		}
		return String.valueOf(r);
	}
	
}

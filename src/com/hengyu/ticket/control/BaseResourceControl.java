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
import com.hengyu.ticket.entity.BaseResource;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.service.BaseResourceService;
import com.hengyu.ticket.util.APIUtil;

@Controller
@RequestMapping(value={"/admin","/user"})
public class BaseResourceControl {

	//基本资源配置信息
	@Autowired
	private BaseResourceService brservice;
	
	@ModelAttribute
	public void befoe(BaseResource br,Model m) throws Exception{
		if(br!=null&&br.getId()!=null){
			BaseResource res = brservice.find(br.getId());
			if(res!=null){
				m.addAttribute("resource", res);
			}else{
				m.addAttribute("isnull", true);
			}
		}
	}
	
	@RequestMapping("reslist")
	public String resourcelist(Page page,String type,Model m,BaseResource br,Writer w) throws Exception{
		page.setParam(br);
		brservice.findlist(page);
		if(type!=null&&type.equals("json")){
			APIUtil.toJSONString(page, w);
		}
		return "admin/reslist";
	}
	
	//添加或更新页面
	@RequestMapping("resadd")
	public String editPage(Model m,@ModelAttribute("resource") BaseResource resource){
		Assert.isTrue(!m.containsAttribute("isnull"), Const.HTTP_MSG_NOT_FOUND);
		return "admin/resedit";
	}
	
	//添加或更新页面
	@RequestMapping("resedit")
	@ResponseBody
	public String edit(@ModelAttribute("resource") BaseResource br) throws Exception{
		int result = Const.ERROR_CODE;
		String py = PinyinHelper.convertToPinyinString(br.getTagsubvalue(), "", PinyinFormat.WITHOUT_TONE);
		br.setPyname(py);
		if(br!=null&&br.getId()==null){
			result = brservice.save(br);
		}else if(br!=null&&br.getId()!=null){
			result = brservice.update(br);
		}
		return String.valueOf(result); 
	}
	
	
}

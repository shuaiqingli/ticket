package com.hengyu.ticket.control;

import java.io.Writer;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.StaticShow;
import com.hengyu.ticket.service.StaticShowService;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequestMapping("/admin")
public class StaticShowControl {
	
	//静态资源
	@Autowired
	private StaticShowService staticService;
	
	@RequestMapping("staticpage")
	public String staticpage(Model m) throws Exception{
		return "admin/staticpage";
	}
	
	//获取所有静态页面
	@RequestMapping("staticinfos")
	public void staticinfos(Writer out) throws Exception{
		List<StaticShow> list = staticService.findAll();
//        for (StaticShow s  : list ) {
//            s.setShowcontent(HtmlUtils.htmlEscape(s.getShowcontent()));
//        }
        out.write(JSON.toJSONString(list));
		out.close();
	}
	
	//更新内容
	@RequestMapping("updatestaticinfo")
	@ResponseBody
	public String update(Integer id,String code) throws Exception{
		int ret = Const.ERROR_CODE;
		StaticShow ss = staticService.find(id);
		if(ss!=null){
			ss.setShowcontent(code);
			ret = staticService.update(ss);
		}
		return ret+"";
	}
	
	

}

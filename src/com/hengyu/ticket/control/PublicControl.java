package com.hengyu.ticket.control;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.CityStation;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.exception.BaseException;
import com.hengyu.ticket.service.AdminService;
import com.hengyu.ticket.service.CityStationService;
import com.hengyu.ticket.service.FuncModeService;
import com.hengyu.ticket.service.RelationStationService;
import com.hengyu.ticket.util.JSONHanlder;
import com.hengyu.ticket.util.Log;

/**
 * 公共路径访问
 * 
 * @author LGF
 *
 */
@Controller
@RequestMapping("/public")
public class PublicControl {

	// 管理员服务类
	@Autowired
	private AdminService as;
	// 菜单服务类
	@Autowired
	private FuncModeService fms;
	// 城市站点管理
	@Autowired
	private CityStationService css;

	@Autowired
	private RelationStationService rss;

	

	// 管理员登录，根据角色编号获取菜单
	@RequestMapping("login.do")
	@ResponseBody
	public String login(Admin admin,Integer remember, HttpServletRequest request,HttpServletResponse resp) throws Exception {
		Admin login = as.login(admin);
		if (login == null) {
			return "error";
		} else {
			Log.info(this.getClass(),"用户登录成功...");
			HttpSession session = request.getSession();
			session.setAttribute(Const.LOGIN_USER, login);
			if(remember!=null&&1==remember){
				//添加 Cookie,转换
				Cookie userid = new Cookie(Const.COOKIE_USERID,login.getUserID());
				Cookie password = new Cookie(Const.COOKIE_PASSWORD,login.getPassword());
				
				userid.setMaxAge(Integer.MAX_VALUE);
				password.setMaxAge(Integer.MAX_VALUE);
				userid.setPath("/");
				password.setPath("/");
				resp.addCookie(userid);
				resp.addCookie(password);
				Log.info(this.getClass(),"保存登录用户状态...");
			}
			return "success";
		}
	}

	// 根据城市id查询站点
	@RequestMapping(value = "stationlist.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public Object getAllCityStation(CityStation cs) throws Exception {
		if (cs == null || !StringUtils.isNotEmpty(cs.getParentid())) {
			return "{}";
		}
		Page page = new Page();
		page.setPageSize(Integer.MAX_VALUE);
		page.setParam(cs);
		css.findList(page);
		return JSONHanlder.getObjectAsString(page.getData());
	}
	
	
	@RequestMapping("test")
	public void test(Integer id,String name,Model m) throws Exception{
//		Log.info(this.getClass(),"***********",id);
//		Log.info(this.getClass(),"***********",name);
//		Log.info(this.getClass(),"***********"+m.asMap());
//		throw new BaseException("test",500);
		throw new Exception();
	}

	
}

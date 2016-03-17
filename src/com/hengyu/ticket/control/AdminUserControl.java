package com.hengyu.ticket.control;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hengyu.ticket.common.Const;

@Controller
@RequestMapping("/user")
public class AdminUserControl {

	/**
	 * 主页跳转
	 * @return
	 */
	@RequestMapping("main.do")
	public String main(){
		return "admin/main";
	}
	
	/**
	 * 退出登录
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping("logout.do")
	public String logout(HttpServletRequest req,HttpServletResponse resp) {
		//清除session ,清除 cookie
		req.getSession().invalidate();
		Cookie userid = new Cookie(Const.COOKIE_USERID,"");
		Cookie password = new Cookie(Const.COOKIE_PASSWORD,"");
		
		userid.setMaxAge(-1);
		password.setMaxAge(-1);
		
		
		userid.setPath("/");
		password.setPath("/");
		
		resp.addCookie(userid);
		resp.addCookie(password);
		
		return "login";
	}
	
}

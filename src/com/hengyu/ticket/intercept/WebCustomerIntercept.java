package com.hengyu.ticket.intercept;


import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.API;
import com.hengyu.ticket.entity.Customer;
import com.hengyu.ticket.exception.BaseException;
import com.hengyu.ticket.service.CustomerService;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.Log;
import com.hengyu.ticket.util.RequestTool;


/**
 * 普通管理员会话拦截器
 * @author LGF
 * 2015-09-28
 */
public class WebCustomerIntercept implements HandlerInterceptor{

	@Autowired
	private CustomerService cs;

	@Override
	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse resp, Object o, Exception e)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp,
			Object o, ModelAndView model) throws Exception {
	}

	@SuppressWarnings("all")
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object obj) throws Exception {
		boolean result = true;
		API api = new API();
		Customer cus = null;
		Object c = req.getSession().getAttribute(Const.LOGIN_CUSTOMER);
		if(Const.DEBUG){
			Log.info(this.getClass(),JSON.toJSONString(req.getCookies()==null?"没有cookie！":req.getCookies()));
		}
		if(c!=null){
			Log.info(this.getClass(),"========== 从Session中获取用户 ===========");
			cus = (Customer) c;
			req.setAttribute("cid", cus.getCid());
			req.setAttribute("mobile", cus.getMobile());
			req.setAttribute(Const.LOGIN_CUSTOMER, cus);
			req.setAttribute("token", cus.getToken());
		}else{
			result = false;
			//尝试自动登录
			String m = RequestTool.getCookieStr(req,Const.COOKIE_CUSTOMER_MOBILE);
			String p = RequestTool.getCookieStr(req,Const.COOKIE_CUSTOMER_PASSWORD);
			if(Const.DEBUG){
				Log.info(this.getClass(),"=========》 mobile:",m," pwd:",p);
				
			}
//			String openid = RequestTool.getCookieStr(req,"openid");
			cus = cs.getCustomerLogin(null, m, p);
			if(cus!=null){
				req.setAttribute("cid", cus.getCid());
				req.setAttribute("mobile", cus.getMobile());
				req.setAttribute(Const.LOGIN_CUSTOMER, cus);
				req.setAttribute("token", cus.getToken());
				String path =  "/";
				
				//清除消息红点cookie
				Cookie cookie = new Cookie("message_flag", null);
				cookie.setPath("/");
				cookie.setMaxAge(0);
				
				int age = 60*60*24*998;
				
				Cookie cid = new Cookie("cid", cus.getCid());
				cid.setPath(path);
				cid.setMaxAge(age);
				
				Cookie token = new Cookie("token", cus.getToken());
				token.setPath(path);
				token.setMaxAge(age);
				
				Cookie openid = new Cookie("openid", cus.getOpenid());
				openid.setPath(path);
				openid.setMaxAge(age);
				
				Cookie user = new Cookie("user",  URLEncoder.encode(JSON.toJSONString(cus),"UTF-8"));
				user.setPath(path);
//				user.setMaxAge(age);
				
				resp.addCookie(cookie);
				resp.addCookie(cid);
				resp.addCookie(user);
				resp.addCookie(token);
//				resp.addCookie(openid);
				result = true;
			}
		}
		if(result==false){
			//会话过期
			api.setCode(5008);
			api.getDatas().add(0);
			PrintWriter out = resp.getWriter();
			APIUtil.toJSONString(api,out);
		}
		return result;
	}
}

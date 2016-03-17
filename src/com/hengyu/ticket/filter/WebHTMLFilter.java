package com.hengyu.ticket.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Customer;
import com.hengyu.ticket.service.CustomerService;
import com.hengyu.ticket.util.Log;
import com.hengyu.ticket.util.RequestTool;

public class WebHTMLFilter implements Filter {

	private String[] urls;
	private String sessionErrorHtml;
	private CustomerService cs;
	
    public WebHTMLFilter() {
    	
    }

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();

		HttpServletResponse resp = (HttpServletResponse) response;
		if(req.getSession().getAttribute(Const.LOGIN_CUSTOMER)!=null){
			Cookie isLogin = new Cookie("customer_login","1");
			isLogin.setMaxAge(60*28);
			isLogin.setPath("/");
			resp.addCookie(isLogin);
		}else{
			Cookie isLogin = new Cookie("customer_login","0");
			isLogin.setMaxAge(-1000);
			isLogin.setPath("/");
			resp.addCookie(isLogin);
		}

		if(urls!=null){
			boolean ischeck = true;
			for (String url : urls) {
				if(uri.endsWith(url)){
					ischeck = false;
				}
			}
			if(ischeck){
				Customer cus = (Customer) req.getSession().getAttribute(Const.LOGIN_CUSTOMER);
				String m = RequestTool.getCookieStr(req,Const.COOKIE_CUSTOMER_MOBILE);
				String p = RequestTool.getCookieStr(req,Const.COOKIE_CUSTOMER_PASSWORD);
				if(Const.DEBUG){
					Log.info(this.getClass(),"=========》 mobile:",m," pwd:",p);
				}
				try {
					if(cus==null){
						cus = cs.getCustomerLogin(null, m, p);
						if(cus==null){
							Cookie href = new Cookie("location_href",uri);
							href.setPath("/");
							resp.addCookie(href);
							
							resp.sendRedirect(sessionErrorHtml);
							return;
						}
						req.setAttribute("cid", cus.getCid());
						req.setAttribute("mobile", cus.getMobile());
						req.setAttribute(Const.LOGIN_CUSTOMER, cus);
						req.getSession().setAttribute(Const.LOGIN_CUSTOMER, cus);
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
						
						Cookie user = new Cookie("user", URLEncoder.encode(JSON.toJSONString(cus),"UTF-8"));
						user.setPath(path);
//						user.setMaxAge(age);
						resp.addCookie(cookie);
						resp.addCookie(cid);
						resp.addCookie(user);
						resp.addCookie(token);
//						resp.addCookie(openid);
					}
				} catch (Exception e) {
					
				}
			}
		}
		chain.doFilter(request, response);
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		urls = fConfig.getInitParameter("public_html_url").replaceAll("\\s","").split(",");
		sessionErrorHtml = fConfig.getInitParameter("session_error_html").replaceAll("\\s","");
		ServletContext context = fConfig.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
		cs = ctx.getBean(CustomerService.class);
	}

}

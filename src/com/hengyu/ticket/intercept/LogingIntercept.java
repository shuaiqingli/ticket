package com.hengyu.ticket.intercept;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hengyu.ticket.common.Const;


/**
 * 日志拦截器
 * @author LGF
 * 2015-09-28
 */
public class LogingIntercept implements HandlerInterceptor{
	

	@Override
	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse resp, Object o, Exception e)
			throws Exception {
		if(req.getRequestURL().toString().endsWith(".html")||req.getRequestURL().toString().endsWith(".jsp")){
			return;
		}
		
//		Enumeration<String> headerNames = req.getHeaderNames();
//		System.out.println("================ head ================");
//		while(headerNames.hasMoreElements()){
//			String h = headerNames.nextElement();
//			System.out.println("********"+h+" : "+req.getHeader(h));
//		}
		
//		System.out.println("[afterCompletion]");
//		System.out.println("\tResponse status:\n\t\t "+resp.getStatus());
		if(Const.DEBUG){
			System.out.println("\tException:\t\t "+e);
			System.out.println("\n========================================= response end =========================================\n");
		}
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp,
			Object o, ModelAndView model) throws Exception {
		if(Const.DEBUG){
			if(req.getRequestURL().toString().endsWith(".html")||req.getRequestURL().toString().endsWith(".jsp")){
				return;
			}
			System.out.println("[params]");
			Map<String, String[]> params = req.getParameterMap();
			for (String key : params.keySet()) {
				String[] arrs = params.get(key);
				System.out.println("\t"+key+"="+Arrays.asList(arrs));
			}
			System.out.println("[postHandle]");
			if(model!=null){
				System.out.println("ModelAndView.name:\t\t "+model.getViewName());
			}
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object o) throws Exception {

		//判断是否是ajax请求
		resp.setCharacterEncoding("UTF-8");
		if(req.getHeader("x-requested-with")!=null){
			resp.setContentType("application/json;charset=UTF-8");
		}else if(req.getHeader("content-type")==null){
			resp.setContentType("text/plain;charset=UTF-8");
		}
		if(req.getRequestURL().toString().endsWith(".html")||req.getRequestURL().toString().endsWith(".jsp")){
			return true;
		}
		if(Const.DEBUG){
			System.out.println("\n========================================= request begin =========================================\n");
			System.out.println("[preHandle]");
			System.out.println("\tUser-Agent:\t\t "+req.getHeader("User-Agent"));
			System.out.println("\tAddr:\t\t "+req.getRemoteAddr());
			System.out.println("\tURL:\t\t "+req.getRequestURL());
			if(o instanceof HandlerMethod){
				HandlerMethod h = (HandlerMethod) o;
				System.out.println("\tHandlerMethod:t\t "+h.getBeanType().getName()+"."+h.getMethod().getName());
			}
		}
		return true;
	}

}

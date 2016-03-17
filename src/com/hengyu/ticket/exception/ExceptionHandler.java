package com.hengyu.ticket.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.common.LinkedMap;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.Customer;
import com.hengyu.ticket.entity.OperationLog;
import com.hengyu.ticket.service.OperationLogService;
import com.hengyu.ticket.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.API;

/**
 * 异常统一处理
 * @author LGF
 * 2015-12-16
 */
public class ExceptionHandler implements HandlerExceptionResolver  {

	@Autowired
	private OperationLogService logService;
	
	@Override
	public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object obj,
			Exception e) {

		exceptionRecord(e,req);

		e.printStackTrace();
		
		String ajax = req.getHeader("x-requested-with");
		String upload = req.getHeader("accept");
		
		Integer code = 500;
		String msg = "服务器忙，请稍后再试！";
		
		
		//自定义异常
		if(e instanceof BaseException){
			BaseException ex = (BaseException) e;
			code = ex.getCode();
			msg = StringUtils.isNotEmpty(ex.getMsg())&&ex.getMsg().length()<=30?ex.getMsg():msg;
		}
		//不是自定义异常
		else{
			msg = e.getMessage()!=null&&e.getMessage().length()<=30?e.getMessage():msg;
		}
		//ajax 或 app 访问
		if((ajax!=null&&!ajax.isEmpty())||(upload!=null&&upload.indexOf("multipart/form-data")!=-1)){
			
			Log.info(this.getClass(),"ajax或APP请求异常处理,或上传文件处理！");
			try {
				PrintWriter out = resp.getWriter();
				//app异常处理
				if(req.getHeader("requested-type")!=null&&req.getHeader("requested-type").equals("APP")){
					API api = new API();
					api.setMsg(msg);
					api.setCode(code);
					APIUtil.toJSON(api, out);
					return null;
				}
				//普通HTTP请求处理
				else{
					resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					resp.addHeader("code", code.toString());
					resp.addHeader("msg", msg);
					HashMap<String,String> exMap = new HashMap<String,String>();
					exMap.put("code", code.toString());
					exMap.put("msg", msg);
					APIUtil.toJSONString(exMap, out);
				}
				return null;
			} catch (IOException e1) {
				Log.info(this.getClass(),e1);
			}
		}else{
			ModelAndView m = new ModelAndView();
			Log.info(this.getClass(),"页面请求异常处理！");
			req.setAttribute("exception", e);
			req.setAttribute("code", code);
			req.setAttribute("msg", msg);
			// session 过期
			if(code!=null&&8000 ==code){
				Log.info(this.getClass(),"session 过期！");
				m.setViewName("redirect:../login.jsp");
				return m;
			}
		}
		return null;
	}


	/**
	 * 记录错误日志
	 * @param e
	 * @param req
	 * @throws Exception
     */
	public void exceptionRecord(Exception e,HttpServletRequest req) {
		OperationLog log = new OperationLog();
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] ss = e.getStackTrace();
		sb.append("Exception >> Message:").append(e.getMessage()).append("\n");
		for (StackTraceElement s : ss) {
			sb.append(s.getClassName()).append(".").append(s.getMethodName()).append("(").append(s.getLineNumber()).append(")").append("\n");
		}
		log.setOperation(new StringBuffer().append("interface|").append(req.getServletPath()).append("|params|").append(CollectionUtil.mapToString(req.getParameterMap())).toString());

		log.setMakedate(new Date());
		log.setRemark(sb.toString());
		log.setIp(CommonUtil.getRemoteAddr(req));
		log.setModule("exception");
		if(req.getSession().getAttribute(Const.LOGIN_USER)!=null){
			Admin admin = (Admin) req.getSession().getAttribute(Const.LOGIN_USER);
			log.setUserid(admin.getUserID());
			log.setUsername(admin.getMobile());
		}else if(req.getSession().getAttribute(Const.LOGIN_CUSTOMER)!=null){
			Customer c = (Customer) req.getSession().getAttribute(Const.LOGIN_CUSTOMER);
			log.setUserid(c.getCid());
			log.setUsername(c.getMobile());
		}else if(req.getAttribute(Const.LOGIN_CUSTOMER)!=null){
			Customer c = (Customer) req.getAttribute(Const.LOGIN_CUSTOMER);
			log.setUserid(c.getCid());
			log.setUsername(c.getMobile());
		}
		try {
			logService.save(log);
		} catch (Exception e1) {

		}
	}

}

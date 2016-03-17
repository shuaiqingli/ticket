package com.hengyu.ticket.intercept;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.API;
import com.hengyu.ticket.entity.Customer;
import com.hengyu.ticket.service.CustomerService;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.Log;


/**
 * 普通管理员会话拦截器
 * @author LGF
 * 2015-09-28
 */
public class CustomerTokenIntercept implements HandlerInterceptor{
	
	@Autowired
	private CustomerService customerService;
	

	@Override
	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse resp, Object o, Exception e)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp,
			Object o, ModelAndView model) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp,
			Object obj) throws Exception {
		boolean result = true;
		String token = req.getParameter("token");
		String mobile = req.getParameter("mobile");
		String cid = req.getParameter("cid");
		
		Customer cus = null;
		API api = new API();
		if(!StringUtils.isNotEmpty(token)||!StringUtils.isNotEmpty(mobile)||!StringUtils.isNotEmpty(cid)){
			Log.info(this.getClass(),"========== 参数错误 ===========");
			Log.info(this.getClass(),"token :　"+token);
			Log.info(this.getClass(),"mobile :　"+mobile);
			Log.info(this.getClass(),"cid :　"+cid);
			api.setCode(5009);
			result = false;
		}else{
			Object c = req.getSession().getAttribute(Const.LOGIN_CUSTOMER);
			if(c!=null&&c instanceof Customer){
				Log.info(this.getClass(),"========== 从Session中获取用户 ===========");
				cus = (Customer) c;
			}else{
				cus = customerService.find(cid);
			}
			if(cus==null){
				Log.info(this.getClass(),"========== 用户没有找到 ===========");
				api.setCode(5003);
				result = false;
			}else if(cus.getToken()==null||!cus.getToken().equals(token)||!cid.equals(cus.getCid())){
				Log.info(this.getClass(),"========== token错误 ===========");
				Log.info(this.getClass(),"=========="," customer ",JSON.toJSONString(cus));
				Log.info(this.getClass(),"=========="," cid:",cid," mobile:",mobile," token:",token);
				api.setCode(5008);
				result = false;
			}
			
		}
		if(result==false){
			PrintWriter out = resp.getWriter();
			APIUtil.toJSONString(api,out);
		}else{
			req.setAttribute("cid", cus.getCid());
			req.setAttribute("mobile", cus.getMobile());
			req.setAttribute(Const.LOGIN_CUSTOMER, cus);
			req.setAttribute("token", cus.getToken());
		}
		return result;
	}
}

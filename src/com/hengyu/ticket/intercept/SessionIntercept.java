package com.hengyu.ticket.intercept;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.exception.BaseException;

/**
 * 超级管理员会话拦截器
 * @author LGF
 * 2015-09-28
 */
public class SessionIntercept implements HandlerInterceptor{
	
	private static Logger log = Logger.getLogger(SessionIntercept.class);
	//不验证的URL后缀
	private List<String> unCheckSuffixURL = new ArrayList<String>(0);

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
		HttpSession session = req.getSession();
		Object user = session.getAttribute(Const.LOGIN_USER);
//		String uri = req.getRequestURI();
		String uri = req.getServletPath();
		log.error("uri :　"+ uri);
		//验证是否登录
		if(user == null ||! (user instanceof Admin)){
			log.error("没有登录，不能访问资源....");
			throw new BaseException(8000);
		}else{
			//权限验证
//			if(user instanceof Admin){
//				Admin admin = (Admin) user;
//				if(admin.getIsAdmin()!=null&&admin.getIsAdmin()==1){
//					return true;
//				}else{
//					log.error("不是超级管理员，不能访问....");
//				}
//			}
            return true;
		}
//		resp.sendRedirect("../login.jsp");
//		return false;
	}

	public List<String> getUnCheckSuffixURL() {
		return unCheckSuffixURL;
	}

	public void setUnCheckSuffixURL(List<String> unCheckSuffixURL) {
		this.unCheckSuffixURL = unCheckSuffixURL;
	}
	
	
}

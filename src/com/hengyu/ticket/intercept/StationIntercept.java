package com.hengyu.ticket.intercept;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.Driver;
import com.hengyu.ticket.service.AdminService;
import com.hengyu.ticket.service.DriverService;

/**
 * 站务API拦截器
 * 
 * @author LGF 2015-10-10
 */
public class StationIntercept implements HandlerInterceptor {

	@Autowired
	private AdminService adminService;
	@Autowired
	private DriverService driverService;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object obj) throws Exception {
		String token = req.getHeader("token");

		// 验证token
		Admin admin = adminService.getAdminByToken(token);
		Driver driver = driverService.getDriverByToken(token);
		Map<String, Object> amap = new HashMap<String, Object>();
		if (admin == null && driver == null) {
			System.out.println("没有找到用户");
			return false;
		} else {
//			System.out.println("验证成功");
			if (admin != null) {
				amap.put("userid", admin.getUserID());
				amap.put("realname", admin.getRealName());
				amap.put("mobile", admin.getMobile());
			} else if (driver != null) {
				amap.put("userid", driver.getId());
				amap.put("realname", driver.getDname());
				amap.put("mobile", driver.getDmobile());
			}
			req.setAttribute("amap", amap);
			return true;
		}
	}

}

package com.hengyu.ticket.intercept;


import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.exception.BaseException;
import com.hengyu.ticket.service.AdminService;
import com.hengyu.ticket.service.CarPositionService;
import com.hengyu.ticket.service.MakeConfService;
import com.hengyu.ticket.util.CollectionUtil;
import com.hengyu.ticket.util.CommonUtil;
import com.hengyu.ticket.util.PositionSocketUtil;
import com.hengyu.ticket.util.SmsUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 普通管理员会话拦截器
 *
 * @author LGF
 *         2015-09-28
 */
public class UserSessionIntercept implements HandlerInterceptor, InitializingBean {

    private static Logger log = Logger.getLogger(UserSessionIntercept.class);

    @Autowired
    private AdminService adminService;
    @Autowired
    private MakeConfService makeConfService;
    @Autowired
    private CarPositionService carPositionService;

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
        String uri = req.getServletPath();

        boolean loginFlag = true;
        boolean accessFlag = true;

        //验证是否登录
        if (user == null || !(user instanceof Admin)) {
            //尝试自动登录
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                try {
                    String userid = null;
                    String password = null;
                    for (Cookie cookie : cookies) {
                        if (Const.COOKIE_USERID.equals(cookie.getName())) {
                            userid = cookie.getValue();
                        } else if (Const.COOKIE_PASSWORD.equals(cookie.getName())) {
                            password = cookie.getValue();
                        }
                    }
                    Admin admin = new Admin();
                    admin.setMobile(userid);
                    admin.setPassword(password);
                    Admin login = adminService.login(admin);
                    if (login != null) {
                        req.getSession().setAttribute(Const.LOGIN_USER, login);
                    } else {
                        loginFlag = false;
                    }
                } catch (Exception e) {
                    loginFlag = false;
                }
            } else {
                loginFlag = false;
            }
        } else {
            //权限验证
            if (uri.startsWith("/admin")) {
                Admin admin = (Admin) user;
                if (admin.getIsAdmin() == null || admin.getIsAdmin() == 0) {
                    accessFlag = false;
                }
            }
        }
        writeOperationLog(req);
        if (!loginFlag) {
            throw new BaseException(8000);
        } else if (!accessFlag) {
            throw new BaseException();
        }
        return true;
    }

    private void writeOperationLog(HttpServletRequest request) {
        Admin admin = (Admin) request.getSession().getAttribute(Const.LOGIN_USER);
        if (null != admin) {
            MDC.put("userid", admin.getUserID());
            MDC.put("username", admin.getMobile());
        } else {
            MDC.put("userid", "-1");
            MDC.put("username", "guest");
        }
        MDC.put("module", "manage");
        MDC.put("ip", CommonUtil.getRemoteAddr(request));
        MDC.put("operation", "interface|" + request.getServletPath().split("\\?")[0]);
        MDC.put("remark", CollectionUtil.mapToString(request.getParameterMap()));
        log.info(MDC.get("operation"));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PositionSocketUtil.carPositionService = carPositionService;
        PositionSocketUtil.init();
        try {
            SmsUtil.Platform = makeConfService.find("Platform").getCurrentValue();
        } catch (Exception e) {
            log.error("读取短信配置失败");
            SmsUtil.Platform = 1;
        }
    }
}

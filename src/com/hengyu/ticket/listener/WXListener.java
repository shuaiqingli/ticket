package com.hengyu.ticket.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.util.WeixinHanlder;

/**
 * 微信 token 监听器
 * @author LGF
 *
 */
@WebListener
public class WXListener implements ServletContextListener {

    public WXListener() {
    	System.out.println("START COM.CODE.UTIL.WXLISTENER.WXLISTENER()");
    }
 
    public void contextInitialized(ServletContextEvent e)  { 
    	ServletContext servletContext = e.getServletContext();
		Const.CONTEXT_PATH = servletContext.getRealPath("/");
    	try {
			if (WeixinHanlder.wxConfig.get("wx_enable").equals("true")) {
				new WeixinHanlder.ThreadAccessToken(servletContext);
			}else{
				System.out.println("============= disable wx listener ================");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    public void contextDestroyed(ServletContextEvent e)  { 
    	System.out.println("destory com.code.util.WXListener.WXListener()");
    }
	
}

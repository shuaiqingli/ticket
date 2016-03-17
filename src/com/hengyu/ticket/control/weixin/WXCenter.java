package com.hengyu.ticket.control.weixin;

import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hengyu.ticket.service.WXSubscribeMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.config.WXSubscribe;
import com.hengyu.ticket.service.DataStatisticService;
import com.hengyu.ticket.util.Log;
import com.hengyu.ticket.util.RequestTool;
import com.hengyu.ticket.util.URLHanlder;
import com.hengyu.ticket.util.WeixinHanlder;

/**
 * 微信验证，页面跳转中心
 * @author LGF
 * 2015-10-17 
 */
@Controller
@RequestMapping("/wx")
public class WXCenter {
	
	@Autowired
	private WXSubscribe ss;
	@Autowired
	private DataStatisticService dss;
	@Autowired
	private WXSubscribeMsgService wxMsgService;
	
	
	/**
	 * 微信服务器接入验证
	 * @param req
	 * @param out
	 */
	@RequestMapping("access")
	public void wxServerAccess(HttpServletRequest req,Writer out){
		String method = req.getMethod();
		if("GET".equalsIgnoreCase(method)){
			String signature,timestamp,nonce,echostr;
			signature = req.getParameter("signature");
			timestamp = req.getParameter("timestamp");
			nonce = req.getParameter("nonce");
			echostr = req.getParameter("echostr");
			Log.info(this.getClass(),"-------- 微信服务器验证 --------");
			try {
				if(WeixinHanlder.checkSgin(signature, timestamp, nonce)){
					out.write(echostr);
					out.close();
					Log.info(this.getClass(),"-------- 签名验证成功 --------");
				}else{
					Log.info(this.getClass(),"-------- 签名验证失败 --------");
					Log.info(this.getClass(),"error invoke method failure ");
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.info(e);
			}
		}else{
			try {
				// 用户发送消息
				Map<String, String> map = WeixinHanlder.XML.toMap(req.getInputStream());
				Log.info(map);
				if(map!=null){
					String event = map.get("Event");
					String type = map.get("MsgType");
					String tousername = map.get("ToUserName");
					String fromusername = map.get("FromUserName");
					String content = map.get("Content");
					String key = map.get("EventKey");
					//用户关注
					if("subscribe".equalsIgnoreCase(event)){
						userSubscribe(out,map, tousername, fromusername);
						try {
							dss.updateWXFollow();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else if((key!=null&&key.equals("kefu"))||(content!=null&&(content.contains("客服")||content.toLowerCase().contains("kefu")))){
						kefuService(fromusername,tousername,out);
					}
				}
			} catch (Exception e){

			}
		}
	}


	//客服消息接入
	public void kefuService(String touser,String fromuser,Writer out) throws IOException {
//						<xml>
//						<ToUserName><![CDATA[touser]]></ToUserName>
//						<FromUserName><![CDATA[fromuser]]></FromUserName>
//						<CreateTime>1399197672</CreateTime>
//						<MsgType><![CDATA[transfer_customer_service]]></MsgType>
//						</xml>
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[").append(touser).append("]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[").append(fromuser).append("]]></FromUserName>");
		sb.append("<CreateTime>").append(System.currentTimeMillis()/1000).append("</CreateTime>");
		sb.append("<MsgType><![CDATA[transfer_customer_service]]></MsgType>");
		sb.append("</xml>");
		try {
			out.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
	
	//用户关注推送消息
	public void userSubscribe(Writer out,Map<String,String> msg,String to,String from) throws Exception{
		/*
		<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[news]]></MsgType>
		<ArticleCount>2</ArticleCount>
		<Articles>
		<item>
		<Title><![CDATA[title1]]></Title> 
		<Description><![CDATA[description1]]></Description>
		<PicUrl><![CDATA[picurl]]></PicUrl>
		<Url><![CDATA[url]]></Url>
		</item>
		</Articles>
		</xml> 
		*/
		Map<String,Object> map = new HashMap<String,Object>();
		
		Map<String,Object> items = new HashMap<String,Object>();
		List<Map<String, String>> msgs = wxMsgService.findSubscribeMsg();
		if(msgs==null||msgs.size()==0){
			return;
		}
		items.put("item",msgs);
		int size = msgs.size();
		
		map.put("ToUserName", from);
		map.put("FromUserName",to);
		map.put("CreateTime", String.valueOf(System.currentTimeMillis()/1000));
		map.put("MsgType", "news");
		map.put("ArticleCount",size);
		map.put("Articles", items);
		try {
			String sendmsg = WeixinHanlder.XML.toXML(map);
			Log.info(this.getClass(),sendmsg);
			out.write(sendmsg);
		} catch (IOException e) {
			throw e;
		}finally{
			if(out!=null){out.flush();out.close();}
		}
		
	}
	
	/**
	 * 微信授权获取授权码
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @CookieValue(value="openid") String openid
	 */
	@RequestMapping("center")
	public void center(HttpServletRequest req,HttpServletResponse resp ) throws IOException{
		Log.info(this.getClass(),"===== 微信菜单进入 =====");
		//获取授权码后，跳转的url
		String url = req.getParameter("url");
		//openid不为空，不用授权
//		String openid = RequestTool.getCookieStr(req, "openid");
//		if(openid!=null&&openid.trim().isEmpty()==false&&openid.equals("null")==false&&openid.length()>10){
//			Log.info(this.getClass(), "openid不为空，不用授权,",openid);
//			String send = req.getParameter("send");
//			if(send==null){
//				send = "index.html";
//			}else{
//				if(send.toLowerCase().startsWith("http")){
//					url = send+"?openid="+openid;
//				}else{
//					url = WeixinHanlder.wxConfig.get("wx_domain")+send+"?openid="+openid;
//				}
//			}
//			resp.sendRedirect(url);
//			return;
//		}
		if(url==null||url.isEmpty()){
			url = WeixinHanlder.wxConfig.getProperty("wx_domain")+"userinfo?"+req.getParameter("send");
		}
		Log.info(this.getClass(),"send url :　"+url);
		//状态，（标记根 state 判断跳转页面）
		String state = req.getParameter("state");
		Log.info(this.getClass(),"跳转url : " , url);
		Log.info(this.getClass(),"state : " , state);
		String wxgrant = WeixinHanlder.wxConfig.getProperty("wx_base_grant_url");
		wxgrant = MessageFormat.format(wxgrant, URLEncoder.encode(url,"UTF-8"),state);
		Log.info(this.getClass(),"wxgrant url :　",wxgrant);
		resp.sendRedirect(wxgrant);
	}
	
	/**
	 * 微信，根据授权码获取用户基本信息，openid等
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("userinfo")
	public void getUserInfo(HttpServletRequest req,HttpServletResponse resp ) throws IOException{
		//		正确时返回的JSON数据包如下：
		//
		//		{
		//		   "access_token":"ACCESS_TOKEN",
		//		   "expires_in":7200,
		//		   "refresh_token":"REFRESH_TOKEN",
		//		   "openid":"OPENID",
		//		   "scope":"SCOPE",
		//		   "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
		//		}
		Properties config = WeixinHanlder.wxConfig;
		String code = req.getParameter("code");
		String state = req.getParameter("state");
		String send = req.getParameter("send");
		String userInfo = URLHanlder.post(MessageFormat.format(config.getProperty("wx_get_user_info"), code));
		String url = config.get(("wx_state"+state))==null?"":config.get(("wx_state"+state)).toString();
		Map<String,String> map = JSON.parseObject(userInfo, Map.class);
		//获取openid
		String openid = map.get("openid");
		Cookie ck = new Cookie("openid", openid);
		ck.setPath("/");
		ck.setMaxAge(60*60*24*15);
		resp.addCookie(ck);
		Log.info(this.getClass(),"用户信息：",userInfo);	
		Log.info(this.getClass(),"跳转页面url：",send);	
		try {
			if(send!=null&&send.isEmpty()==false){
				resp.sendRedirect((send.toLowerCase().startsWith("http")?send:config.get("wx_domain"))+url+"?openid="+openid+"&t="+new Date().getTime());
				return;
			}else if(state==null||state.trim().isEmpty()){
				resp.sendRedirect(config.get("wx_domain")+"index.html?openid="+openid+"&t="+new Date().getTime());
				return;
			}
			Integer.parseInt(state);
			resp.sendRedirect(config.get("wx_domain")+url+"?openid="+openid+"&t="+new Date().getTime());
		} catch (Exception e) {
			resp.sendRedirect(config.get("wx_domain")+state+"?openid="+openid+"&t="+new Date().getTime());
		}
	}
	
	/**
	 * 微信js 功能签名验证
	 * @param req
	 * @param out
	 * @throws IOException
	 */
	@RequestMapping("jsticketsign")
	public void getJSTicketSign(HttpServletRequest req,Writer out) throws IOException{
		String url = req.getParameter("url");
		String jsapi_ticket = "";
		try {
			Object obj = req.getServletContext().getAttribute("wx_ticket");
			jsapi_ticket = obj==null?"":obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> sign = WeixinHanlder.sign(jsapi_ticket, url);
		sign.put("appId", WeixinHanlder.APPID);
		String string = JSON.toJSONString(sign);
		out.write(string);
		out.close();
	}
	
	//获取token
	@RequestMapping("token")
	public void getToken(HttpServletRequest req,Writer out,String app_secret) throws IOException{
		Map<String,Object> token = new HashMap<String,Object>();
		if(app_secret==null||!app_secret.equals(WeixinHanlder.APPSECRET)){
			token.put("msg", "app_secret error ...");
		}else{
			token.put("msg", "success");
			token.put("wx_access_token", req.getServletContext().getAttribute("wx_access_token"));
			token.put("wx_js_ticket", req.getServletContext().getAttribute("wx_ticket"));
		}
		out.write(JSON.toJSONString(token));
		out.close();
	}
}

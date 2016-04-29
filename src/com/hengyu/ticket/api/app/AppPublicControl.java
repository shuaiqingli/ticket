package com.hengyu.ticket.api.app;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.service.*;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.util.APIUtil;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.Log;
import com.hengyu.ticket.util.RequestTool;
import com.hengyu.ticket.util.SecurityHanlder;
import com.hengyu.ticket.util.SmsUtil;
import com.hengyu.ticket.util.URLHanlder;
import com.hengyu.ticket.util.VerifyCodeUtils;
import com.hengyu.ticket.util.WeixinHanlder;

@Controller
@RequestMapping("/public/api")
public class AppPublicControl {

	// 短信
	@Autowired
	private SmsHistoryService smsService;
	// 用户
	@Autowired
	private CustomerService customerService;
	// 城市列表
	@Autowired
	private CityStationService cityStationService;
	// 车票线路
	@Autowired
	private TicketLineService ticketLineService;
	// 优惠券规则
	@Autowired
	private CouponsRuleService couponsruleService;
	// 优惠券
	@Autowired
	private CouponsService couponsService;
	//静态资源
	@Autowired
	private StaticShowService staticService;
	//线路管理
	@Autowired
	private LineManageService lms;
	@Autowired
	private FeedbackService feedbackService;
	@Autowired
	private ActivityMsgService activityMsgService;
	@Autowired
	private LineManageStationService lmss;
	@Autowired
	private SoftUpgradeService softUpgradeService;

	// 获取验证码
	@RequestMapping(value = "getVerificationCode", method = { RequestMethod.GET, RequestMethod.POST })
	public void getVerificationCode(String mobile,String type, Writer out) throws IOException {
		API api = new API();
		try {
			if (StringUtils.isNotEmpty(mobile) == false || mobile.length() != 11) {
				api.setCode(5006);
			} else {
				// 发送消息
				boolean isok = SmsUtil.sendVerifyCode(mobile, smsService,type);
				if (isok == false) {
					api.setCode(5000);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			api.setCode(5000);
		}
		APIUtil.toJSON(api,out);
	}
	
	
	//检查用户是否注册
	@RequestMapping("checkCustomerRegister")
	public void checkCustomerRegister(Customer customer,Writer w) throws Exception{
		API api = new API();
		Customer c = customerService.findByMobile(customer.getMobile());
		if (c != null) {
			api.setCode(5002);
		}
		APIUtil.toJSON(api, w);
	}
	

	// 用户注册
	@RequestMapping("customerRegister")
	public void customerRegister(Customer customer, String code, HttpServletRequest req, Writer out)
			throws IOException {
		API api = new API();
		try {
			SmsHistory sms = smsService.findlastByMobile(customer.getMobile());
			if (sms!=null&&code.equals(sms.getVerifyCode())) {
				
				if (System.currentTimeMillis() - DateHanlder.getDateTimeFromat().parse(sms.getMakeDate()).getTime() >= 1000
						* 60 * 30) {
					api.setCode(5005);
				} else {
					Customer c = customerService.findByMobile(customer.getMobile());
					if (c != null) {
						api.setCode(5002);
					} else {
						customer.setRank(0);
						Object openid = RequestTool.getCookie(req, "openid");
						customer.setCid(DateHanlder.getCurrentDateTimeNumber());
						customer.setPassword(SecurityHanlder.md5(customer.getPassword()));
						customer.setMakedate(DateHanlder.getCurrentDateTime());
						customer.setMileage(0);

						//根据经纬度获取顾客地址
						String lat = RequestTool.getCookieStr(req,"lat");
						String lng = RequestTool.getCookieStr(req,"lng");
						customer.setLat(lat);
						customer.setLng(lng);
						Log.info(this.getClass(),"=========== 根据经纬度获取地址 ========");
						Log.info(this.getClass(),"lat:",lat);
						Log.info(this.getClass(),"lng:",lng);
						if(StringUtils.isNotEmpty(lat)&&StringUtils.isNotEmpty(lng)){
							String addrapi = "http://api.map.baidu.com/geocoder?location="+lat+","+lng+"&output=json&type=wgs84";
							String addrjson = URLHanlder.post(addrapi);
							Log.info(this.getClass(),"===== 百度地图API返回结果：",addrjson);
							String addr = APIUtil.getJSONVal("result.formatted_address",addrjson);
							Log.info(this.getClass(),"===== 格式化后的地址结果：",addr);
							customer.setRegaddr(addr);
						}

						if (openid != null && !openid.toString().isEmpty() && customer.getOpenid() == null) {
							customer.setOpenid(openid.toString());
						}
						if(customer.getOpenid()!=null&&!customer.getOpenid().trim().isEmpty()){
							// 获取微信用户信息
							Map<String, String> info = WeixinHanlder.getUserInfoByUnionId(req, customer.getOpenid());
							if (info != null) {
								customer.setCname(WeixinHanlder.formatNickName(info.get("nickname"), null));
								customer.setHeadphoto(info.get("headimgurl"));
							}
						}
						customer.setMoney(new BigDecimal(0));
						customer.setIntegral(0);
						customer.setCname(StringUtils.isEmpty(customer.getCname())?customer.getMobile():customer.getCname());
						customerService.save(customer);
						
					}
				}
			} else {
				api.setCode(5004);
			}
		} catch (Exception e) {
			e.printStackTrace();
			api.setCode(5000);
		}
		APIUtil.toJSON(api,out);
	}
	
	
	// 验证码验证
	@RequestMapping("checkVerification")
	public void checkVerification(Customer customer, String code, HttpServletRequest req, Writer out)
			throws IOException {
		API api = new API();
		try {
			SmsHistory sms = smsService.findlastByMobile(customer.getMobile());
			if (sms!=null&&code.equals(sms.getVerifyCode())) {
				if (System.currentTimeMillis() - DateHanlder.getDateTimeFromat().parse(sms.getMakeDate()).getTime() >= 1000
						* 60 * 30) {
					api.setCode(5005);
				} else {
					Log.info(this.getClass()," =====》 验证成功！");
				}
			} else {
				Log.info(this.getClass()," =====》 验证失败！");
				api.setCode(5004);
			}
		} catch (Exception e) {
			Log.info(this.getClass()," =====》 验证失败！",e);
			e.printStackTrace();
			api.setCode(5000);
		}
		APIUtil.toJSON(api,out);
	}

	// 用户修改密码
	@RequestMapping("customerUpdatePwd")
	public void customerUpdatePwd(Customer customer, String code, HttpServletRequest req, Writer out)
			throws IOException {
		API api = new API();
		try {
			SmsHistory sms = smsService.findlastByMobile(customer.getMobile());
			if (code.equals(sms.getVerifyCode())) {
				if (System.currentTimeMillis() - DateHanlder.getDateTimeFromat().parse(sms.getMakeDate()).getTime() >= 1000
						* 60 * 30) {
					api.setCode(5005);
				} else {
					Customer c = customerService.findByMobile(customer.getMobile());
					if (c == null) {
						api.setCode(5003);
					} else {
						c.setPassword(SecurityHanlder.md5(customer.getPassword()));
						customerService.update(c);
					}
				}
			} else {
				api.setCode(5004);
			}
		} catch (Exception e) {
			e.printStackTrace();
			api.setCode(5000);
		}
		APIUtil.toJSON(api,out);
	}

	// 用户登录
	@SuppressWarnings("unchecked")
	@RequestMapping("customerLogin")
	public void customerLogin(String mobile, String password, String openid, Writer out, HttpServletRequest req, HttpServletResponse response) throws Exception {
		API api = new API();
		try {
			if(openid==null||openid.trim().isEmpty()||openid.equals("null")){
				openid = null;
			}
			if(StringUtils.isNotEmpty(password)){
				password = SecurityHanlder.md5(password);
			}
			Customer cus = customerService.getCustomerLogin(openid,mobile,password);
			if (cus == null) {
				if(StringUtils.isEmpty(openid)){
					Assert.isTrue(false,"登录失败,用户名或密码错误！");
				}else{
					Assert.isTrue(false,"自动登录失败！");
				}
			} else{
				api.getDatas().add(cus);
				if(req.getHeader("WEB")!=null&&req.getHeader("WEB").equals("JCTICKET")){
					req.getSession().setAttribute(Const.LOGIN_CUSTOMER, cus);
					String path = "/";
					Cookie cookie = new Cookie("message_flag", null);
					cookie.setPath(path);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					
					int age = 60*60*24*900;
					
					Cookie cookie_password = new Cookie(Const.COOKIE_CUSTOMER_MOBILE, mobile);
					cookie_password.setPath(path);
					cookie_password.setMaxAge(age);
					response.addCookie(cookie_password);
					
					Cookie cookie_account = new Cookie(Const.COOKIE_CUSTOMER_PASSWORD, password);
					cookie_account.setPath(path);
					cookie_account.setMaxAge(age);

					Cookie cid = new Cookie("cid", cus.getCid());
					cid.setPath(path);
					cid.setMaxAge(age);

					Cookie token = new Cookie("token", cus.getToken());
					token.setPath(path);
					token.setMaxAge(age);

					Cookie user = new Cookie("user", URLEncoder.encode(JSON.toJSONString(cus),"UTF-8"));
					user.setPath(path);

					response.addCookie(cookie);
					response.addCookie(cid);
					response.addCookie(user);
					response.addCookie(token);

					response.addCookie(cookie_account);

				}else{
					cus.setToken(SecurityHanlder.md5(System.currentTimeMillis() / 1000 + mobile));
					if(StringUtils.isNotEmpty(openid)&&StringUtils.isEmpty(cus.getOpenid())){
						cus.setOpenid(openid);
					}
					customerService.update(cus);
				}
				cus.setPassword(null);
				Integer coupons = couponsService.findCountByCustomerId(cus.getCid());
				cus.setCoupons(coupons);
			}
		} catch (Exception e) {
			throw e;
		}
		APIUtil.toJSON(api,out);
	}

	// 获取所有城市
	@RequestMapping("getAllCity")
	public void getAllCity(Writer out, CityStation c) throws IOException {
		API api = new API();
		try {
			List<Object> citys = cityStationService.getAllCity(c);
			api.setDatas(citys);
		} catch (Exception e) {
			api.setCode(5000);
		}
		out.write(JSON.toJSONString(api).toString());
		out.close();
	}

	// 获取城市所有的站点
	@SuppressWarnings("all")
	@RequestMapping("getStationByCityId")
	public void getStationByCityId(Writer w,String begincityid,String endcityid) throws IOException {
		/*Socket socket = new Socket("127.0.0.1", 10036);
        socket.getOutputStream().write(new byte[]{(byte)0x5B,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x48,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x10,(byte)0x01,(byte)0x02,(byte)0x9F,(byte)0xD8,(byte)0x31,(byte)0x01,(byte)0x02,(byte)0x0F,(byte)0x01,(byte)0x02,(byte)0x9F,(byte)0xD8,(byte)0x31,(byte)0x97,(byte)0x7A,(byte)0x7B,(byte)0x35,(byte)0xDB,(byte)0xB8,(byte)0xA2,(byte)0x5E,(byte)0x02,(byte)0x99,(byte)0x86,(byte)0xA5,(byte)0x45,(byte)0x87,(byte)0x07,(byte)0x57,(byte)0xFB,(byte)0xB0,(byte)0x2A,(byte)0xBF,(byte)0x50,(byte)0x85,(byte)0xAB,(byte)0x5E,(byte)0x01,(byte)0x4E,(byte)0x48,(byte)0xDE,(byte)0xB2,(byte)0x55,(byte)0xB5,(byte)0xD6,(byte)0x5F,(byte)0xD7,(byte)0x61,(byte)0xDA,(byte)0xAC,(byte)0x6E,(byte)0x41,(byte)0x06,(byte)0x02,(byte)0xD0,(byte)0xCF,(byte)0x07,(byte)0x61,(byte)0x5A,(byte)0x02,(byte)0xC0,(byte)0x78,(byte)0x7B,(byte)0x90,(byte)0x5D});*/

		Assert.notNull(begincityid, "城市编号参数错误！");
		Assert.notNull(endcityid, "城市编号参数错误！");
		API api = new API();
		try {
			List<Object> bcitys = cityStationService.getStartStationByCityid(begincityid, endcityid,0);
			List<Object> ecitys = cityStationService.getStartStationByCityid(begincityid,endcityid,1);
			api.getDatas().add(bcitys);
			api.getDatas().add(ecitys);
		} catch (Exception e) {
			api.setCode(5000);
			e.printStackTrace();
		}
		APIUtil.toJSON(api, w);
	}

	// 获取出发城市站点
	@SuppressWarnings("all")
	@RequestMapping("getStartStationList")
	public void getStartStationList(Writer w,String begincityid,String endcityid, String endstationid) throws IOException {
		Assert.notNull(begincityid, "无效出发城市");
		API api = new API();
		try {
			api.getDatas().add(cityStationService.getStartStationList(begincityid, endcityid, endstationid));
		} catch (Exception e) {
			api.setCode(5000);
			e.printStackTrace();
		}
		APIUtil.toJSON(api, w);
	}

	// 获取达到城市站点
	@SuppressWarnings("all")
	@RequestMapping("getEndStationList")
	public void getEndStationList(Writer w,String endcityid,String begincityid, String beginstationid) throws IOException {
		Assert.notNull(endcityid, "无效到达城市");
		API api = new API();
		try {
			api.getDatas().add(cityStationService.getEndStationList(endcityid, begincityid, beginstationid));
		} catch (Exception e) {
			api.setCode(5000);
			e.printStackTrace();
		}
		APIUtil.toJSON(api, w);
	}

	// 获取车票线路列表
	@SuppressWarnings("rawtypes")
	@RequestMapping("getTicketLineList")
	public void getTicketLineList(Page page, TicketLine tl, Writer out) throws IOException {
		API api = new API();
		try {
			if ((tl == null || !StringUtils.isNotEmpty(tl.getCitystartid())
					|| !StringUtils.isNotEmpty(tl.getCityarriveid()) || 
					!StringUtils.isNotEmpty(tl.getTicketdate())) && tl.getId() == null) {
				api.setCode(5006);
			} else {
				page.setParam(tl);
				List datas = ticketLineService.findTicketList(page, true);
				api.setDatas(datas);
				if(tl.getId()==null){
					api.setPage(page);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			api.setCode(5000);
		}
		APIUtil.toJSON(out, api, null,null,false,true);
	}

	// 获取班次的站点列表
	@SuppressWarnings("rawtypes")
	@RequestMapping("getStationListForShift")
	public void getStationListForShift(Long id, Writer out) throws IOException {
		Assert.notNull(id, "无效班次");
		API api = new API();
		api.setDatas(ticketLineService.getStationListForShift(id));
		APIUtil.toJSON(out, api, null,null,false,true);
	}

	// 根据经纬度获取城市
	@RequestMapping("getCityByLocation")
	@SuppressWarnings("rawtypes")
	public void getCityByLocation(String location, Writer w) throws IOException {
		Log.info(this.getClass(),"============= 经纬度获取城市信息 " , location);
		try {
			if (APIUtil.isNotNull(w, location)) {
				String json = URLHanlder
						.post("http://api.map.baidu.com/geocoder?location=" + location + "&output=json");
				Log.info(this.getClass(),"============= 百度返回数据 " , json);
				if (json != null) {
					String city = APIUtil.getJSONVal("result.addressComponent.city", json);
					Log.info(this.getClass(),"============= 所在城市 " , city);
					if (city != null) {
						city = city.replace("市", "");
						CityStation c = new CityStation();
						c.setCityname(city);
						List citys = cityStationService.getAllCity(c);
						Map result = null;
						for (Object object : citys) {
							Map m = (Map) object;
							String cname = m.get("cityname").toString();
							if (cname.equals(city)) {
								result = m;
								break;
							}
						}
						APIUtil.APIToJSON(w, result);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//获取静态页面
	@RequestMapping("getStaticPage")
	public void getStaticPage(String id,Writer w) throws Exception{
		StaticShow o = staticService.find(id);
		APIUtil.APIToJSON(w, o);
	}
	
	//获取线路备注
	@SuppressWarnings("unchecked")
	@RequestMapping("getLineManageMemo")
	public void getLineManageMemo(Integer lmid,Writer w) throws Exception{
		LineManage lm = lms.find(lmid);
		Assert.isTrue(lm!=null,"线路编号不正确！");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memo", lm.getMemo()==null?"":lm.getMemo());
		map.put("lmid", lmid);
		API api = new API();
		api.getDatas().add(map);
		APIUtil.toJSON(api , w);
		
	}
	
	//获取站点地址
	@RequestMapping("getStationAddress")
	public void getStationAddress(String stationid,Writer w) throws Exception{
		Assert.hasText(stationid,"站点编号不能为空!");
		List<Map<String, String>> list = new ArrayList<Map<String,String>>(2);
		if(stationid.indexOf(",")!=-1){
			list.addAll(cityStationService.getAddressByIds(stationid.split(",")[0]));
			list.addAll(cityStationService.getAddressByIds(stationid.split(",")[1]));
		}else{
			list.addAll(cityStationService.getAddressByIds(stationid));
		}
		API api = new API();
		api.setDatas(list);
		APIUtil.toJSON(api, w);
	}

	//获取最新消息(用于导航栏红点显示)
	@SuppressWarnings("unchecked")
	@RequestMapping("message")
	public void message(String activityIDList, String noticeIDList, Writer w, HttpServletRequest request) {
		Customer customer = (Customer) request.getSession().getAttribute(Const.LOGIN_CUSTOMER);
		API api = new API();
		api.getDatas().add("success");
		if(null != customer){
			api.getDatas().add(feedbackService.findFeedbackStatusForCustomer(customer.getCid()));
		}else{
			api.getDatas().add(0);
		}
		api.getDatas().add(activityMsgService.findActivityMsgStatus(0, StringUtils.isBlank(activityIDList) ? null : activityIDList.split(",")));
		api.getDatas().add(activityMsgService.findActivityMsgStatus(1, StringUtils.isBlank(noticeIDList) ? null : noticeIDList.split(",")));
		APIUtil.toJSON(api, w);
	}
	
	//验证码图片
	@RequestMapping("verifyCode")
	public void getVerifyCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setContentType("image/jpeg");  
          
        //生成随机字串  
//        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);  
        String verifyCode = String.valueOf((1000+new Random().nextInt(9999))).substring(0,4);  
        //存入会话session  
        request.getSession().setAttribute("verifycode", verifyCode.toLowerCase());
        //生成图片  
        int w = 200, h = 80;  
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
	}
	
	
	//验证验证码
	@SuppressWarnings("unchecked")
	@RequestMapping("checkVerifyCode")
	public void checkVerifyCode(HttpServletRequest request,HttpServletResponse response,String verifycode,Writer w) throws IOException{
		Assert.hasText(verifycode,"请填写图片验证码!");
		Object code = request.getSession().getAttribute("verifycode");
		if(code!=null&&code.toString().equalsIgnoreCase(verifycode)){
			API api = new API();
			api.getDatas().add(verifycode);
			APIUtil.toJSON(api, w);
		}else{
			Assert.isTrue(false,"填写图片验证码错误!");
		}
		
	}

	//资讯数据,type，默认为0，指活动和消息；1指首页消息
	@SuppressWarnings("unchecked")
	@RequestMapping("news")
	public void news(Integer type, Writer w) {
		API api = new API();
		api.getDatas().add(activityMsgService.findAvailableActivityMsgList(type == null ? 0 : type));
		APIUtil.toJSON(api, w);
	}

	//资讯详情
	@SuppressWarnings("unchecked")
	@RequestMapping("newsDetail")
	public void newsDetail(Integer id, Writer w) {
		Assert.notNull(id, "ID不能为空");
		API api = new API();
		api.getDatas().add(activityMsgService.findAvailableActivityMsg(id));
		APIUtil.toJSON(api, w);
	}

	//获取线路途径站点
	@RequestMapping("getStationListByLineID")
	public void getStationListByLineID(Integer lmid,Writer w) throws Exception {
		Assert.notNull(lmid,"线路编号不能为空！");
		List<LineManageStation> stations = lmss.findBylMID(lmid);
		LineManage lm = lms.find(lmid);

		LineManageStation begin = new LineManageStation();
		begin.setStid(lm.getStstartid());
		begin.setStname(lm.getStstartname());

		LineManageStation end = new LineManageStation();
		end.setStid(lm.getStarriveid());
		end.setStname(lm.getStarrivename());

		stations.add(0,begin);
		stations.add(end);

		API api = new API();
		api.setDatas(stations);
		APIUtil.toJSON(w,api,new Class[]{LineManageStation.class},new String[]{"sTID,sTName"},true,false);

	}

	//按站点查询出发和到达城市
	@RequestMapping("getCityByStationID")
	public void getCityByStationID(String ststartid,String starriveid,Writer w) throws Exception {
		Assert.hasText(ststartid,"始发站点不能为空");
		Assert.hasText(starriveid,"到达站点不能为空");
		String pb = cityStationService.find(ststartid).getParentid();
		String pe = cityStationService.find(starriveid).getParentid();
		Assert.isTrue(StringUtils.isNotBlank(pb)&&StringUtils.isNotBlank(pb),"没有找到城市");
		CityStation begin = cityStationService.find(pb);
		CityStation end = cityStationService.find(pe);
		API api = new API();
		api.getDatas().add(begin);
		api.getDatas().add(end);
		APIUtil.toJSON(api,w);
	}

	//软件更新
	@RequestMapping("appUpgrade")
	public void getNewAPP(Writer w) throws Exception {
		API api = new API();
		List apps = softUpgradeService.getNewVersion(1);
		if(apps!=null&&apps.size()!=0){
			api.getDatas().add(apps.get(0));
		}
		APIUtil.toJSON(api,w);
	}

	//判断登录状态
	@RequestMapping("islogin")
	public void islogin(HttpServletRequest req,Writer w){
		Object cus = req.getSession().getAttribute(Const.LOGIN_CUSTOMER);
		API api = new API();
		if(cus==null){
			api.getDatas().add(0);
		}else{
			api.getDatas().add(1);
		}
		APIUtil.toJSON(api, w);
	}

    //获取线路退款备注
    @RequestMapping("getLineRefundRemark")
    public void getLineRefundRemark(Integer lmid,Writer w) throws Exception {
        Assert.notNull(lmid,"线路编号不能为空");
        LineManage lm = lms.findRefundRemark(lmid);
        Assert.notNull(lm,"没有找到线路！");
        APIUtil.APIToJSON(w,lm);
    }

	//获取线路代金券开关
	@RequestMapping("getLineManageCouponFlag")
	public void getLineManageCouponFlag(Integer lmid,Writer w) throws Exception {
		Assert.notNull(lmid,"线路编号不能为空");
		LineManage lm = lms.find(lmid);
		Assert.notNull(lm,"没有找到线路！");
		APIUtil.APIToJSON(w,lm);
	}

}

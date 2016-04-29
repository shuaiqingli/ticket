package com.hengyu.ticket.api.station;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.CityStation;
import com.hengyu.ticket.entity.Driver;
import com.hengyu.ticket.entity.RelationStation;
import com.hengyu.ticket.service.AdminService;
import com.hengyu.ticket.service.CityStationService;
import com.hengyu.ticket.service.DriverService;
import com.hengyu.ticket.service.RelationStationService;
import com.hengyu.ticket.util.APIStatus;
import com.hengyu.ticket.util.DateUtil;
import com.hengyu.ticket.util.SecurityHanlder;

@Controller
public class APIAdminControl {

	// 管理员服务类
	@Autowired
	private AdminService as;
	@Autowired
	private DriverService driverService;
	@Autowired
	private RelationStationService relationstationService;

	@Autowired
	private CityStationService cityStationService;
	
	// 站务登录
	@RequestMapping(value = "/api/public/stationLogin", method = RequestMethod.POST)
	@ResponseBody
	public String stationLogin(HttpServletRequest request) throws Exception {
		Map a = request.getParameterMap();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", APIStatus.SIGN_ERROR_STATUS);
		result.put("info", APIStatus.SYSTEM_ERROR_INFO);
		Admin login = as.getAPIStationLogin(a);
		Driver dl = driverService.getAPIStationLogin(a);
		if (login != null) {
			// 更新token
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("token", SecurityHanlder.md5(login.getMobile() + DateUtil.getCurrentDateTimeString()));
			p.put("mobile", login.getMobile());
			as.setToken(p);
			login.setToken(p.get("token").toString());

			// 站点
			List rsls = relationstationService.findByUser(login.getUserID());

			result.put("status", APIStatus.SUCCESS_STATUS);
			result.put("info", APIStatus.SUCCESS_INFO);
			result.put("isdriver", 0);
			result.put("data", login);
			result.put("station", rsls);
		} else if (dl != null) {
			// 更新token
			Map<String, Object> p = new HashMap<String, Object>();
			p.put("token", SecurityHanlder.md5(dl.getDmobile() + DateUtil.getCurrentDateTimeString()));
			p.put("mobile", dl.getDmobile());
			driverService.setToken(p);
			dl.setToken(p.get("token").toString());

			result.put("status", APIStatus.SUCCESS_STATUS);
			result.put("info", APIStatus.SUCCESS_INFO);
			result.put("isdriver", 1);
			result.put("data", dl);
		}
		return JSON.toJSONString(result);
	}

	// 修改密码
	@RequestMapping(value = "/api/station/changePwd", method = RequestMethod.POST)
	@ResponseBody
	public String changePwd(HttpServletRequest request) throws Exception {
		Map<String,Object> amap = (Map<String,Object>) request.getAttribute("amap");
		String newpassword = request.getParameter("newpassword");
		// System.out.println("newpassword========" + newpassword);
		Map<String, Object> result = new HashMap<String, Object>();

		Map<String, Object> a = new HashMap<String, Object>();
		a.put("userid", amap.get("userid"));
		a.put("password", newpassword);
		a.put("ischange", 1);
		int cp = as.changePwd(a);
		if (cp > 0) {
			result.put("status", APIStatus.SUCCESS_STATUS);
			result.put("info", APIStatus.SUCCESS_INFO);
		} else {
			result.put("status", APIStatus.SYSTEM_ERROR_STATUS);
			result.put("info", APIStatus.SYSTEM_ERROR_INFO);
		}
		return JSON.toJSONString(result);
	}

	// 列表站点
	@RequestMapping(value = "/api/station/listStation", method = RequestMethod.POST)
	@ResponseBody
	public String listStation(HttpServletRequest request) throws Exception {
//		Admin admin = (Admin) request.getSession().getAttribute("admin");
		Map amap = (Map) request.getAttribute("amap");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", APIStatus.SIGN_ERROR_STATUS);
		result.put("info", APIStatus.SYSTEM_ERROR_INFO);

		// 站点
		List<RelationStation> rsls = relationstationService.findByUser(amap.get("userid").toString());
		//stid是城市表id
		List<Map<String,Object>> resultList = new ArrayList<>();
		if(rsls!=null && rsls.size()>0){
			for(RelationStation res: rsls){
				Map<String,Object> map = new HashMap<>();
				String stid = res.getStid();
				CityStation city = cityStationService.find(stid);
				if(city!=null){
					//根据现有数据发现,id查询到的数据如果ParentID为空那么就是城市名称和城市ID,如果不为空 那么拿到ParentID再去查这个ID的ParentID为空的那个数据
					String parentid = city.getParentid();
					if(StringUtils.isNotEmpty(parentid)){
						Map<String,Object> resultMap = cityStationService.findCityNameAndIdByParentId(parentid);
						map.put("city_id",resultMap.get("id"));
						map.put("city_name",resultMap.get("city_name"));
					}else{
						map.put("city_id",city.getId());
						map.put("city_name",city.getCityname());
					}
					map.put("id",res.getId());
					map.put("stid",res.getStid());
					map.put("stname",res.getStname());
					map.put("userid",res.getUserid());
					
				}else{
					map.put("id",res.getId());
					map.put("stid",res.getStid());
					map.put("stname",res.getStname());
					map.put("userid",res.getUserid());
					map.put("city_id","");
					map.put("city_name","");
				}
				resultList.add(map);
			}
		}
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		result.put("station", resultList);
		return JSON.toJSONString(result);
	}

	//余票告警的班次列表
	@RequestMapping(value = "/api/station/shiftListForBalanceTicketWarn", method = RequestMethod.POST)
	@ResponseBody
	public String shiftListForBalanceTicketWarn(String shiftIDList, HttpServletRequest request) {
		List<Integer> parsedShiftIDList = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(shiftIDList)){
			for(String shiftID : shiftIDList.split(",")){
				parsedShiftIDList.add(Integer.parseInt(shiftID));
			}
		}
		Map amap = (Map) request.getAttribute("amap");
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", APIStatus.SUCCESS_STATUS);
		result.put("info", APIStatus.SUCCESS_INFO);
		result.put("shiftList", as.findShiftListForBalanceTicketWarn(amap.get("userid").toString(), parsedShiftIDList.size() > 0 ? parsedShiftIDList : null));
		return JSON.toJSONString(result);
	}

}

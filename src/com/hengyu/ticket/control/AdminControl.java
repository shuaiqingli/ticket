package com.hengyu.ticket.control;

import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hengyu.ticket.service.*;
import com.hengyu.ticket.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.CityStation;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.RelationStation;
import com.hengyu.ticket.entity.Role;
import com.hengyu.ticket.exception.BaseException;

@Controller
@RequestMapping("/admin")
public class AdminControl {

	// 管理员服务类
	@Autowired
	private AdminService as;
	// 菜单服务类
	@Autowired
	private FuncModeService fms;
	// 角色服务类
	@Autowired
	private RoleService rs;
	// 城市站点服务类
	@Autowired
	private CityStationService css;
	// 站点列表
	@Autowired
	private RelationStationService rss;
	@Autowired
	private LineManageService lineManageService;

	// 执行请求方法之前执行
	@ModelAttribute
	public void before(Model m, Admin admin) throws Exception {
		if (admin != null && StringUtils.isNotEmpty(admin.getUserID())) {
			admin = as.find(admin.getUserID());
			if (admin != null) {
				m.addAttribute(admin);
			}else{
				m.addAttribute("isnull",true);
			}
		}
	}

	/**
	 * 管理员列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("adminList.do")
	public String adminList(Page page, Admin admin, Model m, Writer w, String type) throws Exception {
		page.setParam(admin);
		as.findAdminList(page);
		m.addAttribute(page);
		if (type != null && type.equals("json")) {
			w.write(JSON.toJSONString(page));
			w.close();
		}
		return "admin/adminList";
	}

	/**
	 * 编辑管理员页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "adminEdit.do", "adminAdd.do" })
	public String editAdmin(Admin admin, Model m) throws Exception {
		Assert.isTrue(!m.containsAttribute("isnull"), Const.HTTP_MSG_NOT_FOUND);
		List<Role> roles = rs.findAll();
		List<CityStation> citys = css.findAllCity();
		if (roles != null) {
			m.addAttribute("roles", roles);
		}
		if (citys != null) {
			m.addAttribute("citys", citys);
		}
		if (admin != null && admin.getUserID() != null) {
			List<RelationStation> rslist = rss.findByUser(admin.getUserID());
			if (rslist != null) {
				StringBuffer ids = new StringBuffer();
				StringBuffer stationnames = new StringBuffer();
				int index = 0;
				for (RelationStation rs : rslist) {
					ids.append(rs.getStid());
					stationnames.append(rs.getStname());
					if (index != rslist.size() - 1) {
						ids.append(",");
						stationnames.append("、");
					}
					index++;
				}
				m.addAttribute("ids", ids.toString());
				m.addAttribute("stationnames", stationnames);
			}
			if (StringUtils.isNotEmpty(admin.getParentid()) && !admin.getUserID().equals(admin.getParentid())) {
				Admin parent = as.find(admin.getParentid());
				if (parent != null) {
					m.addAttribute("parent", parent);
				}
			}
			m.addAttribute("stationList", css.findListByUserid(admin.getUserID()));
			m.addAttribute("lineList", lineManageService.findListByUserid(admin.getUserID()));
		}
		return "admin/adminEdit";
	}

	// 添加管理员
	@RequestMapping(value = { "saveadmin.do" })
	@ResponseBody
	public String saveAdmin(Admin admin, String ids, String delids,Model m) throws Exception {
		Integer result = Const.ERROR_CODE;
		if (admin == null) {
			return result.toString();
		}
		// 是否超级管理员
		if (admin != null && admin.getRoleID() == null) {
			Log.info(this.getClass(),"---- supper admin ----");
			admin.setIsAdmin(1);
		} else {
			admin.setIsAdmin(0);
		}
		CityStation c = css.find(admin.getCityId());
		if (c != null) {
			admin.setCityname(c.getCityname());
		}
		if (StringUtils.isNotEmpty(admin.getParentid()) == false) {
			admin.setParentid(admin.getUserID());
		}
		Admin a = as.find(admin.getUserID());
		// 更新操作
		if (a != null) {
			result = as.update(admin, delids, ids);
			Log.info(this.getClass(),"---- update admin info result :　" , result);
		} else if (admin != null && a == null) {
			Assert.isTrue(as.find(admin.getUserID())==null,"该管理员已经存在！");
			admin.setMakedate(DateHanlder.getCurrentDateTime());
			result = as.save(admin, ids);
			Log.info(this.getClass(),"---- save admin info result :　" , result);
		}
		return String.valueOf(result);
	}


	// 修改密码
	@RequestMapping(value = "changePwd.do", method = RequestMethod.POST)
	@ResponseBody
	public String changePwd(HttpServletRequest request) throws Exception {
		String userid = request.getParameter("userid");
		String newpassword = SecurityHanlder.md5(request.getParameter("newpassword"));
		//System.out.println("newpassword========" + newpassword);
		Map<String, Object> result = new HashMap<String, Object>();

		Map<String, Object> a = new HashMap<String, Object>();
		a.put("userid", userid);
		a.put("password", newpassword);
		a.put("ischange", 0);
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

	//绑定站点到管理员
	@ResponseBody
	@RequestMapping("bindStationListToAdmin.do")
	public String bindStationListToAdmin(String userid, String stationidList) {
		Assert.hasText(userid, "管理员不能为空");
		Assert.hasText(stationidList, "站点不能为空");
		String[] parsedStationidList = stationidList.split(",");
		as.addStationList(userid, parsedStationidList);
		return "1";
	}

	//解绑站点到管理员
	@ResponseBody
	@RequestMapping("unbindStationToAdmin.do")
	public String unbindStationToAdmin(String userid, String stationid) {
		Assert.hasText(userid, "管理员不能为空");
		Assert.hasText(stationid, "站点不能为空");
		as.delStation(userid, stationid);
		return "1";
	}

	//绑定线路到管理员
	@ResponseBody
	@RequestMapping("bindLineListToAdmin.do")
	public String bindLineListToAdmin(String userid, String lineidList) {
		Assert.hasText(userid, "管理员不能为空");
		Assert.hasText(lineidList, "线路不能为空");
		String[] parsedLineidList = lineidList.split(",");
		as.addLineList(userid, parsedLineidList);
		return "1";
	}

	//解绑线路到管理员
	@ResponseBody
	@RequestMapping("unbindLineToAdmin.do")
	public String unbindLineToAdmin(String userid, String lineid) {
		Assert.hasText(userid, "管理员不能为空");
		Assert.hasText(lineid, "线路不能为空");
		as.delLine(userid, lineid);
		return "1";
	}

	@RequestMapping("adminDataList.do")
	public String adminDataList(Page page, String keyword, Model m) {
		m.addAttribute("adminDataList", as.findAdminDataList(keyword==null?null:keyword.trim(), page));
		m.addAttribute(page);
		return "admin/adminDataList";
	}

	@RequestMapping("findAdminListForBindCustomer.do")
	public String findAdminListForBindCustomer(Page page, String keyword, Model m) {
		m.addAttribute("adminList", as.findAdminListForBindCustomer(keyword, page));
		m.addAttribute(page);
		return "admin/selectAdminForCustomer";
	}

}

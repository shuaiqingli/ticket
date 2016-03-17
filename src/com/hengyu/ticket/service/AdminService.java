package com.hengyu.ticket.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hengyu.ticket.dao.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.CityStation;
import com.hengyu.ticket.entity.FuncMode;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.RelationStation;
import com.hengyu.ticket.util.SecurityHanlder;
import org.springframework.util.Assert;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;
	@Autowired
	private MakeConfDao makeConfDao;
	@Autowired
	private CityStationDao csdao;
	@Autowired
	private RelationStationDao rsdao;
	@Autowired
	private FuncModeService fms;
	@Autowired
	private RelationStationService rss;
	@Autowired
	private LineManageDao lineManageDao;

	/**
	 * 保存一个对象
	 * 
	 * @param admin
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(Admin admin, String stationids) throws Exception {
		// 是否存在
		Long count = adminDao.findByMobilCheck(admin.getMobile());
		if (count >= 1) {
			return Const.EXIST_CODE;
		}
		admin.setPassword(SecurityHanlder.md5(admin.getPassword()));
		// 保存站点列表
		if (StringUtils.isNotEmpty(stationids)) {
			String[] arr = stationids.split(",");
			for (String id : arr) {
				CityStation station = csdao.find(id);
				RelationStation relationStation = new RelationStation();
				relationStation.setStid(id);
				relationStation.setStname(station.getCityname());
				relationStation.setUserid(admin.getUserID());
				rsdao.save(relationStation);
			}
		}
		int result = adminDao.save(admin);
		return result;
	}

	/**
	 * 更新一个对象
	 * 
	 * @param admin
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	public int update(Admin admin, String delids, String ids) throws Exception {
		// 删除站点列表
		if (StringUtils.isNotEmpty(delids)) {
			Map map = new HashMap();
			map.put("userid", admin.getUserID());
			map.put("ids", delids.split(","));
			rsdao.deleteByIDS(map);
		}
		// 保存站点列表
		if (StringUtils.isNotEmpty(ids)) {
			List<RelationStation> list = rsdao.findByUser(admin.getUserID());
			String[] arr = ids.split(",");
			for (String id : arr) {
				boolean isappend = true;
				for (RelationStation r : list) {
					if (r.getStid().equals(id)) {
						isappend = false;
						break;
					}
				}
				if (isappend) {
					CityStation station = csdao.find(id);
					RelationStation relationStation = new RelationStation();
					relationStation.setStid(id);
					relationStation.setStname(station.getCityname());
					relationStation.setUserid(admin.getUserID());
					rsdao.save(relationStation);
				}
			}
		}
		return adminDao.update(admin);
	}

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param userID
	 *            主键
	 * @return 返回Admin对象
	 * @throws Exception
	 */
	public Admin find(String userID) throws Exception {
		return adminDao.find(userID);
	}

	/**
	 * 用户登录
	 * 
	 * @param admin
	 * @return
	 * @throws Exception
	 */
	public Admin login(Admin admin) throws Exception {
		Admin login = adminDao.login(admin);
		if(login!=null){
			Integer roleID = login.getRoleID();
			List<FuncMode> funcModes = fms.findMeunByRole(roleID);
			login.setMenus(funcModes);
			login.setStations(rss.findByUser(login.getUserID()));
		}
		return login;
	}

	/**
	 * 查询管理员列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public void findAdminList(Page page) throws Exception {
		List<Admin> list = adminDao.findAdminList(page);
		page.setData(list);
		Long totalCount = adminDao.totalCount(page);
		page.setTotalCount(totalCount);
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	// 按手机号密码查询
	public Admin getAPIStationLogin(Map a) throws Exception {
		return adminDao.getAPIStationLogin(a);
	}

	// 设置Token
	public void setToken(Map p) throws Exception {
		adminDao.setToken(p);
	}

	public Admin getAdminByToken(String token) throws Exception {
		return adminDao.getAdminByToken(token);
	}

	// 修改密码
	public int changePwd(Map a) throws Exception {
		return adminDao.changePwd(a);
	}

	public void addStationList(String userid, String[] stationidList) {
		for (String stationid : stationidList) {
			List<Map> stationList = adminDao.checkForBindStationToAdmin(userid, stationid);
			Assert.isTrue(stationList != null && stationList.size() == 1, "无效站点");
			Assert.isTrue(adminDao.bindStationToAdmin(userid, stationid, (String)stationList.get(0).get("CityName"), (String)stationList.get(0).get("ParentID")) == 1, "操作失败");
			List<Integer> lineidList = adminDao.getLineidListOfStationForBindAdmin(userid, stationid);
			if(lineidList != null && lineidList.size() > 0){
				for (Integer lineid : lineidList) {
					Assert.isTrue(adminDao.checkForBindLineToAdmin(userid, lineid.toString()) == 1, "无效线路");
					Assert.isTrue(adminDao.bindLineToAdmin(userid, lineid.toString()) == 1, "操作失败");
				}
			}
		}
	}

	public void delStation(String userid, String stationid) {
		Assert.isTrue(adminDao.unbindStationToAdmin(userid, stationid) == 1, "操作失败");
		adminDao.delInvalidLineList(userid);
	}

	public void addLineList(String userid, String[] lineidList) {
		for (String lineid : lineidList) {
			Assert.isTrue(adminDao.checkForBindLineToAdmin(userid, lineid) == 1, "无效线路");
			Assert.isTrue(adminDao.bindLineToAdmin(userid, lineid) == 1, "操作失败");
		}
	}

	public void delLine(String userid, String lineid) {
		Assert.isTrue(adminDao.unbindLineToAdmin(userid, lineid) == 1, "操作失败");
	}

	public List<Map> findAdminDataList(String keyword, Page page){
		page.setTotalCount(adminDao.findAdminDataTotal(keyword));
		return adminDao.findAdminDataList(keyword, page);
	}

	public List<Admin> findAdminListForBindCustomer(String keyword, Page page){
		page.setTotalCount(adminDao.findAdminTotalForBindCustomer(keyword));
		return adminDao.findAdminListForBindCustomer(keyword, page);
	}

	public List<Map> findShiftListForBalanceTicketWarn(String userid, List<Integer> shiftIDList){
		List<Map> lineMapList = lineManageDao.findListByUserid(userid);
		if(lineMapList == null || lineMapList.size() == 0) return new ArrayList<Map>();
		List<Integer> lmidList = new ArrayList<Integer>();
		for(Map lineMap : lineMapList){
			lmidList.add((Integer) lineMap.get("ID"));
		}
		return adminDao.findShiftListForBalanceTicketWarn(lmidList, shiftIDList);
	}
}

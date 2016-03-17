package com.hengyu.ticket.service;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.dao.DriverDao;
import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.Driver;
import com.hengyu.ticket.entity.MakeConf;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.util.SecurityHanlder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author 李冠锋 2015-10-08
 */
@Service
public class DriverService {

	@Autowired
	private DriverDao driverDao;
	@Autowired
	private MakeConfService mcf;

	/**
	 * 保存一个对象
	 *
	 * @param driver
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(Driver driver) throws Exception {
		driver.setPassword(SecurityHanlder.md5(driver.getPassword()));
		if(StringUtils.isNotEmpty(driver.getId())){
			return driverDao.save(driver);
		}
		return mcf.findMakeConf(driver, Const.NUMBER_SIX, new Execute<Integer, Driver>() {
			public Integer execute(MakeConf mc, Driver o, String id) throws Exception {
				o.setId(id);
				return driverDao.save(o);
			}
		});
	}

	/**
	 * 更新一个对象
	 *
	 * @param driver
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(Driver driver) throws Exception {
		return driverDao.update(driver);
	}

	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(String id) throws Exception {
		return driverDao.delete(id);
	}

	/**
	 * 根据主键查询一个对象
	 *
	 * @param id
	 *            主键
	 * @return 返回Driver对象
	 * @throws Exception
	 */
	public Driver find(String id) {
		return driverDao.find(id);
	}

	/**
	 * 司机列表
	 *
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<Driver> findlist(Page page) {
		Long totalCount = driverDao.totalCount(page);
		page.setTotalCount(totalCount);
		return driverDao.findlist(page);
	}

	public void updatePassword(String driverid, String password) {
		Assert.isTrue(driverDao.updatePassword(driverid, password) == 1, "操作失败");
	}

	public void setDriverDao(DriverDao driverDao) {
		this.driverDao = driverDao;
	}

	public List getDriverByKeywords(Map a) throws Exception {
		return driverDao.getDriverByKeywords(a);
	}

	public List<Map> findListByLineGroupID(String groupID) {
		return driverDao.findListByLineGroupID(groupID);
	}

	public List<Driver> findListForBindLine(Page page) {
		Long totalCount = driverDao.totalCountForBindLine(page);
		page.setTotalCount(totalCount);
		return driverDao.findListForBindLine(page);
	}

	public List<Map> findLineDataForDriverList(List<String> driveridList) {
		return driverDao.findLineDataForDriverList(driveridList);
	}

	// 按手机号密码查询
	public Driver getAPIStationLogin(Map a) throws Exception {
		return driverDao.getAPIStationLogin(a);
	}

	// 设置Token
	public void setToken(Map p) throws Exception {
		driverDao.setToken(p);
	}
	
	public Driver getDriverByToken(String token) throws Exception {
		return driverDao.getDriverByToken(token);
	}
}

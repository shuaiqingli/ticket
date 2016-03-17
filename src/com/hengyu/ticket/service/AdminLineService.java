package com.hengyu.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.AdminLineDao;
import com.hengyu.ticket.entity.AdminLine;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-12-30
 *
 */
@Service
public class AdminLineService {

	@Autowired
	private AdminLineDao adminLineDao;

	/**
	 * 保存一个对象
	 * 
	 * @param adminLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(AdminLine adminLine) throws Exception {
		return adminLineDao.save(adminLine);
	}

	/**
	 * 更新一个对象
	 * 
	 * @param adminLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(AdminLine adminLine) throws Exception {
		return adminLineDao.update(adminLine);
	}

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回AdminLine对象
	 * @throws Exception
	 */
	public AdminLine find(Integer id) throws Exception {
		return adminLineDao.find(id);
	}

	public void setAdminLineDao(AdminLineDao adminLineDao) {
		this.adminLineDao = adminLineDao;
	}

	// 按userid获取线路
	public List<AdminLine> getAdminLine(String userid) throws Exception {
		return adminLineDao.getAdminLine(userid);
	}

}

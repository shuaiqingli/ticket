package com.hengyu.ticket.service;

import com.hengyu.ticket.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.SoftUpgradeDao;
import com.hengyu.ticket.entity.SoftUpgrade;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-12-02
 *
 */
@Service
public class SoftUpgradeService {

	@Autowired
	private SoftUpgradeDao softUpgradeDao;

	/**
	 * 保存一个对象
	 * 
	 * @param softUpgrade
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(SoftUpgrade softUpgrade) throws Exception {
		return softUpgradeDao.save(softUpgrade);
	}

	/**
	 * 更新一个对象
	 * 
	 * @param softUpgrade
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(SoftUpgrade softUpgrade) throws Exception {
		return softUpgradeDao.update(softUpgrade);
	}

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回SoftUpgrade对象
	 * @throws Exception
	 */
	public SoftUpgrade find(Integer id) throws Exception {
		return softUpgradeDao.find(id);
	}

	public void setSoftUpgradeDao(SoftUpgradeDao softUpgradeDao) {
		this.softUpgradeDao = softUpgradeDao;
	}

	// 查找有没有新版本
	public List getNewVersion(Integer sort) throws Exception {
		return softUpgradeDao.getNewVersion(sort);
	}

	//获取App列表
	public List<SoftUpgrade> findlist(Page page) throws Exception {
		page.setTotalCount(softUpgradeDao.findlistCount(page));
		return softUpgradeDao.findlist(page);
	}
}

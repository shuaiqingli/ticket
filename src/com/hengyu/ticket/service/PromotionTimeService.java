package com.hengyu.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.PromotionTimeDao;
import com.hengyu.ticket.entity.PromotionTime;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-11-19
 *
 */
@Service
public class PromotionTimeService {

	@Autowired
	private PromotionTimeDao promotionTimeDao;

	/**
	 * 保存一个对象
	 * 
	 * @param promotionTime
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(PromotionTime promotionTime) throws Exception {
		return promotionTimeDao.save(promotionTime);
	}

	/**
	 * 更新一个对象
	 * 
	 * @param promotionTime
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(PromotionTime promotionTime) throws Exception {
		return promotionTimeDao.update(promotionTime);
	}

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回PromotionTime对象
	 * @throws Exception
	 */
	public PromotionTime find(Integer id) throws Exception {
		return promotionTimeDao.find(id);
	}

	public void setPromotionTimeDao(PromotionTimeDao promotionTimeDao) {
		this.promotionTimeDao = promotionTimeDao;
	}

	// 按促销PID查找时间段，
	public List getPromotionTimeByPID(String pid) throws Exception {
		return promotionTimeDao.getPromotionTimeByPID(pid);
	}

	// 按促销PID删除时间段
	public int delPromotionByPID(String pid) throws Exception {
		return promotionTimeDao.delPromotionByPID(pid);
	}

}

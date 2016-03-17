package com.hengyu.ticket.dao;

import java.util.List;

import com.hengyu.ticket.entity.PromotionTime;

/**
 * 
 * @author 李冠锋 2015-11-19
 *
 */
public interface PromotionTimeDao {

	/**
	 * 保存一个对象
	 * 
	 * @param promotionTime
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(PromotionTime promotionTime) throws Exception;

	/**
	 * 更新一个对象
	 * 
	 * @param promotionTime
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(PromotionTime promotionTime) throws Exception;

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回PromotionTime对象
	 * @throws Exception
	 */
	public abstract PromotionTime find(Integer id) throws Exception;

	// 按促销PID查找时间段，
	public abstract List getPromotionTimeByPID(String pid) throws Exception;

	// 按促销PID删除时间段
	public abstract int delPromotionByPID(String pid) throws Exception;

}

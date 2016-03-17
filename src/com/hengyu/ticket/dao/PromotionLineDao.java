package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.PromotionLine;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-11-14
 *
 */
public interface PromotionLineDao {

	/**
	 * 保存一个对象
	 * 
	 * @param promotionLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(PromotionLine promotionLine) throws Exception;

	/**
	 * 更新一个对象
	 * 
	 * @param promotionLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(PromotionLine promotionLine) throws Exception;

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回PromotionLine对象
	 * @throws Exception
	 */
	public abstract PromotionLine find(Integer id) throws Exception;

	// 按促销ID查线路
	public abstract List getPromotionLineByPID(String pid) throws Exception;
}

package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.TripPriceRule;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-11-14
 *
 */
public interface TripPriceRuleDao {
	
	/**
	 * 保存一个对象
	 * @param tripPriceRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(TripPriceRule tripPriceRule) throws Exception;
	
	/**
	 * 按行程价格删除
	 * @param tplid
	 * @return
	 * @throws Exception
	 */
	public abstract int deleteByTripPrice(int tplid) throws Exception;
	
	//删除
	public abstract int delete(int id) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param tripPriceRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(TripPriceRule tripPriceRule) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回TripPriceRule对象
	 * @throws Exception
	 */
	public abstract TripPriceRule find(Integer id) throws Exception;
	
	/**
	 * 按行程价格查询
	 * @param tplid
	 * @return
	 * @throws Exception
	 */
	public abstract List<TripPriceRule> findByTripPrice(Integer tplid) throws Exception;
	
}

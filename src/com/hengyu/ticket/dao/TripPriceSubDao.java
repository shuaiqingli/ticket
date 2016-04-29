package com.hengyu.ticket.dao;

import java.util.List;

import com.hengyu.ticket.entity.TripPriceRule;
import com.hengyu.ticket.entity.TripPriceSub;

/**
 * 
 * @author 李冠锋 2015-11-14
 *
 */
public interface TripPriceSubDao {
	
	/**
	 * 保存一个对象
	 * @param tripPriceSub
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int save(TripPriceSub tripPriceSub) throws Exception;
	
	//按规则删除
	int deleteByTripPriceRule(Integer tprid) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param tripPriceSub
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int update(TripPriceSub tripPriceSub) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回TripPriceSub对象
	 * @throws Exception
	 */
	TripPriceSub find(Integer id) throws Exception;
	
	/**
	 * 按规则查询详情
	 * @param tpr
	 * @return
	 * @throws Exception
	 */
	List<TripPriceSub> findByTripPriceRule(TripPriceRule tpr) throws Exception;
}

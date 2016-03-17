package com.hengyu.ticket.dao;

import java.util.List;

import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.TicketStore;
import com.hengyu.ticket.entity.TripPriceList;
import com.hengyu.ticket.entity.TripPriceRule;

/**
 * 
 * @author 李冠锋 2015-11-28
 *
 */
public interface TripPriceListDao {
	
	/**
	 * 保存一个对象
	 * @param tripPriceList
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(TripPriceList tripPriceList) throws Exception;
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public abstract int delete(Integer id) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param tripPriceList
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(TripPriceList tripPriceList) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回TripPriceList对象
	 * @throws Exception
	 */
	public abstract TripPriceList find(Integer id) throws Exception;
	
	//检查是否有日期冲突
	public abstract TripPriceList findExistsDate(TripPriceList tp) throws Exception;
	
	//查询列表
	public abstract List<TripPriceList> findlist(Page page) throws Exception;
	
	//按日期查询规则，并且已经设置完所有星期
	public abstract List<TripPriceList> findByTicketStore(TicketStore ts) throws Exception;
	
	//统计条数
	public abstract long count(Page page) throws Exception;
	
	//按行程统计星期
	public abstract TripPriceList findWeekdayByTripPrice(TripPriceRule tpr) throws Exception;
	
	//是否允许修改线路
	public abstract int findisAllowUpdateLineManage(Integer lmid) throws Exception;
	
	
}

package com.hengyu.ticket.dao;

import java.util.List;

import com.hengyu.ticket.entity.LinePrice;

/**
 * 
 * @author 李冠锋 2015-10-15
 *
 */
public interface LinePriceDao {
	
	/**
	 * 保存一个对象
	 * @param linePrice
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(LinePrice linePrice) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param linePrice
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(LinePrice linePrice) throws Exception;
	
	/**
	 * 更新价格
	 * @param linePrice
	 * @return
	 * @throws Exception
	 */
	public abstract int updatePrice(LinePrice linePrice) throws Exception;
	
	/**
	 * 删除 (删除不存在传入的ids)
	 * @param notIdInList
	 * @return
	 * @throws Exception
	 */
	public abstract int deleteByNotInIds(List<LinePrice> notIdInList) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回LinePrice对象
	 * @throws Exception
	 */
	public abstract LinePrice find(Integer id) throws Exception;
	
	/**
	 * 按线路查询模板
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public abstract List<LinePrice> findByLineId(String id) throws Exception;
}

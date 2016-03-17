package com.hengyu.ticket.dao;

import java.util.List;

import com.hengyu.ticket.entity.StaticShow;

/**
 * 
 * @author 李冠锋 2015-11-09
 *
 */
public interface StaticShowDao {
	
	/**
	 * 保存一个对象
	 * @param staticShow
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(StaticShow staticShow) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param staticShow
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(StaticShow staticShow) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回StaticShow对象
	 * @throws Exception
	 */
	public abstract StaticShow find(Object id) throws Exception;
	
	/**
	 * 查询所有
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public abstract List<StaticShow> findAll() throws Exception;
}

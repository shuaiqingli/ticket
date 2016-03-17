package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.StorePutModel;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
public interface StorePutModelDao {
	
	/**
	 * 保存一个对象
	 * @param storePutModel
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(StorePutModel storePutModel) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param storePutModel
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(StorePutModel storePutModel) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param tLID 主键
	 * @return 返回StorePutModel对象
	 * @throws Exception
	 */
	public abstract StorePutModel find(String tLID) throws Exception;
}

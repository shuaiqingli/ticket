package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.OperationLog;
import java.util.List;

/**
 * 
 * @author 李冠锋 2016-02-15
 *
 */
public interface OperationLogDao {
	
	/**
	 * 保存一个对象
	 * @param operationLog
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(OperationLog operationLog) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param operationLog
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(OperationLog operationLog) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回OperationLog对象
	 * @throws Exception
	 */
	public abstract OperationLog find(Integer id) throws Exception;
}

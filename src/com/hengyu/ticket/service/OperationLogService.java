package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.OperationLogDao;
import com.hengyu.ticket.entity.OperationLog;
import java.util.List;

/**
 * 
 * @author 李冠锋 2016-02-15
 *
 */
 @Service
public class OperationLogService{
	
	@Autowired
	private OperationLogDao operationLogDao;
	
	/**
	 * 保存一个对象
	 * @param operationLog
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(OperationLog operationLog) throws Exception{
		return operationLogDao.save(operationLog);
	}
	
	/**
	 * 更新一个对象
	 * @param operationLog
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(OperationLog operationLog) throws Exception{
		return operationLogDao.update(operationLog);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回OperationLog对象
	 * @throws Exception
	 */
	public OperationLog find(Integer id) throws Exception{
		return operationLogDao.find(id);
	}
	
	public void setOperationLogDao(OperationLogDao operationLogDao){
		this.operationLogDao = operationLogDao;
	}
}

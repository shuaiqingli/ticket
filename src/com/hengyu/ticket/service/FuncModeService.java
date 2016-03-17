package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.FuncModeDao;
import com.hengyu.ticket.entity.FuncMode;
import com.hengyu.ticket.entity.Role;

import java.util.List;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
 @Service
public class FuncModeService{
	
	@Autowired
	private FuncModeDao funcModeDao;
	
	/**
	 * 保存一个对象
	 * @param funcMode
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(FuncMode funcMode) throws Exception{
		return funcModeDao.save(funcMode);
	}
	
	/**
	 * 更新一个对象
	 * @param funcMode
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(FuncMode funcMode) throws Exception{
		return funcModeDao.update(funcMode);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param iD 主键
	 * @return 返回FuncMode对象
	 * @throws Exception
	 */
	public FuncMode find(Integer iD) throws Exception{
		return funcModeDao.find(iD);
	}
	
	public List<FuncMode> findMeunByRole(Integer roleId) throws Exception{
		return funcModeDao.findMenuByRole(roleId);
	}
	
	
	public void setFuncModeDao(FuncModeDao funcModeDao){
		this.funcModeDao = funcModeDao;
	}
}

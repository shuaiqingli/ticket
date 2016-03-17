package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.RolePowerDao;
import com.hengyu.ticket.entity.RolePower;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
 @Service
public class RolePowerService{
	
	@Autowired
	private RolePowerDao rolePowerDao;
	
	/**
	 * 保存一个对象
	 * @param rolePower
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(RolePower rolePower) throws Exception{
		return rolePowerDao.save(rolePower);
	}
	
	/**
	 * 更新一个对象
	 * @param rolePower
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(RolePower rolePower) throws Exception{
		return rolePowerDao.update(rolePower);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param iD 主键
	 * @return 返回RolePower对象
	 * @throws Exception
	 */
	public RolePower find(Integer iD) throws Exception{
		return rolePowerDao.find(iD);
	}
	
	public void setRolePowerDao(RolePowerDao rolePowerDao){
		this.rolePowerDao = rolePowerDao;
	}
}

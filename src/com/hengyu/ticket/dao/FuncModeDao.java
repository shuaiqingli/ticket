package com.hengyu.ticket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hengyu.ticket.entity.FuncMode;
import com.hengyu.ticket.entity.Role;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
public interface FuncModeDao {
	
	/**
	 * 保存一个对象
	 * @param funcMode
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(FuncMode funcMode) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param funcMode
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(FuncMode funcMode) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param iD 主键
	 * @return 返回FuncMode对象
	 * @throws Exception
	 */
	public abstract FuncMode find(Integer iD) throws Exception;
	
	/**
	 * 按角色编号获取菜单
	 * @param role
	 * @return
	 * @throws Exception
	 */
	List<FuncMode> findMenuByRole(@Param("roleid")Integer roleid) throws Exception;
}

package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
public interface RoleDao {
	
	/**
	 * 保存一个对象
	 * @param role
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(Role role) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param role
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(Role role) throws Exception;

	public abstract int delete(@Param("id") Integer id) throws Exception;

	
	/**
	 * 根据主键查询一个对象
	 * @param iD 主键
	 * @return 返回Role对象
	 * @throws Exception
	 */
	public abstract Role find(Integer iD) throws Exception;
	
	/**
	 * 查询所有角色
	 * @return
	 * @throws Exception
	 */
	List<Role> findAll() throws Exception;
	
	/**
	 * 查询所有角色,菜单
	 * @return
	 * @throws Exception
	 */
	List<Role> findList(Role role) throws Exception;
}

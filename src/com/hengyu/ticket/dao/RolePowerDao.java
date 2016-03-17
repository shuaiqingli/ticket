package com.hengyu.ticket.dao;


import com.hengyu.ticket.entity.RolePower;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
public interface RolePowerDao {
	
	/**
	 * 保存一个对象
	 * @param rolePower
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(RolePower rolePower) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param rolePower
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(RolePower rolePower) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param iD 主键
	 * @return 返回RolePower对象
	 * @throws Exception
	 */
	public abstract RolePower find(Integer iD) throws Exception;
	
	/**
	 * 按角色id和菜单id查找
	 * @param rp
	 * @return
	 * @throws Exception
	 */
	public abstract RolePower findByRoleFuncModel(RolePower rp) throws Exception;
	
	/**
	 * 按角色id和菜单id删除
	 * @param rp
	 * @return
	 * @throws Exception
	 */
	public abstract Integer deleteByRoleFuncModel(RolePower rp) throws Exception;
	
	/**
	 * 按角色删除权限
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public abstract Integer deleteByRoleId(Integer roleId) throws Exception;
}

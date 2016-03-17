package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.AdminLine;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-12-30
 *
 */
public interface AdminLineDao {

	/**
	 * 保存一个对象
	 * 
	 * @param adminLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(AdminLine adminLine) throws Exception;

	/**
	 * 更新一个对象
	 * 
	 * @param adminLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(AdminLine adminLine) throws Exception;

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回AdminLine对象
	 * @throws Exception
	 */
	public abstract AdminLine find(Integer id) throws Exception;

	// 按userid获取线路
	public abstract List<AdminLine> getAdminLine(String userid) throws Exception;
}

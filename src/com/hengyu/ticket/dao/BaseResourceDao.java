package com.hengyu.ticket.dao;

import java.util.List;

import com.hengyu.ticket.entity.BaseResource;
import com.hengyu.ticket.entity.Page;

/**
 * 
 * @author 李冠锋 2015-11-09
 *
 */
public interface BaseResourceDao {
	
	/**
	 * 保存一个对象
	 * @param baseResource
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(BaseResource baseResource) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param baseResource
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(BaseResource baseResource) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回BaseResource对象
	 * @throws Exception
	 */
	public abstract BaseResource find(Integer id) throws Exception;
	
	/**
	 * 查询资源列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public abstract List<BaseResource> findlist(Page page) throws Exception;
	/**
	 * 统计记录数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public abstract long findlistCount(Page page) throws Exception;
}

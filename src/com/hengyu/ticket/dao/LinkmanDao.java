package com.hengyu.ticket.dao;

import java.util.List;

import com.hengyu.ticket.entity.Linkman;
import com.hengyu.ticket.entity.Page;

/**
 * 
 * @author 李冠锋 2015-10-10
 *
 */
public interface LinkmanDao {
	
	/**
	 * 保存一个对象
	 * @param linkman
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(Linkman linkman) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param linkman
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(Linkman linkman) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回Linkman对象
	 * @throws Exception
	 */
	public abstract Linkman find(String id) throws Exception;
	
	/**
	 * 删除联系人
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public abstract int delete(String id) throws Exception;
	
	/**
	 * 查询联系人
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public abstract Linkman findByMobile(Linkman l) throws Exception;
	
	/**
	 * 查询联系人
	 * @return
	 * @throws Exception
	 */
	public abstract List<Linkman> findLinks(Page page) throws Exception;
	
	/**
	 * 统计条数
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public abstract long findLinksCount(Page page) throws Exception;
}

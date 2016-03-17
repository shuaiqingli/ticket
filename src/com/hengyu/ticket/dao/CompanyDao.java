package com.hengyu.ticket.dao;

import java.util.List;

import com.hengyu.ticket.entity.Company;
import com.hengyu.ticket.entity.Page;

/**
 * 
 * @author 李冠锋 2015-12-02
 *
 */
public interface CompanyDao {
	
	/**
	 * 保存一个对象
	 * @param company
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(Company company) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param company
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(Company company) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回Company对象
	 * @throws Exception
	 */
	public abstract Company find(Integer id) throws Exception;
	
	//统计条数
	public abstract long count(Page page) throws Exception;
	//查询公司列表
	public abstract List<Company> findlist(Page page) throws Exception;
	
}

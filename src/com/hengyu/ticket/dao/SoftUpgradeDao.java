package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.SoftUpgrade;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @author 李冠锋 2015-12-02
 *
 */
public interface SoftUpgradeDao {
	
	/**
	 * 保存一个对象
	 * @param softUpgrade
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(SoftUpgrade softUpgrade) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param softUpgrade
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(SoftUpgrade softUpgrade) throws Exception;
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回SoftUpgrade对象
	 * @throws Exception
	 */
	public abstract SoftUpgrade find(Integer id) throws Exception;
	
	//查找有没有新版本
	public abstract List getNewVersion(@Param("sort") Integer sort) throws Exception;

	public abstract List<SoftUpgrade> findlist(Page page) throws Exception;
	public abstract Long findlistCount(Page page) throws Exception;

}

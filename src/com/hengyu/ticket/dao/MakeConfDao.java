package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.MakeConf;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
public interface MakeConfDao {
	
	/**
	 * 保存一个对象
	 * @param makeConf
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(MakeConf makeConf) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param makeConf
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(MakeConf makeConf) throws Exception;
	
	/**
	 * 更新当前id值
	 * @param makeConf
	 * @return
	 * @throws Exception
	 */
	public abstract int updateVal(MakeConf makeConf) throws Exception;
	
	/**
	 * 根据主键查询一个对象
	 * @param tableName 主键
	 * @return 返回MakeConf对象
	 * @throws Exception
	 */
	public abstract MakeConf find(String tableName) throws Exception;
}

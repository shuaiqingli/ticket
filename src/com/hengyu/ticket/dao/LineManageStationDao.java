package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.LineManageStation;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
public interface LineManageStationDao {
	
	/**
	 * 保存一个对象
	 * @param lineManageStation
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int save(LineManageStation lineManageStation) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param lineManageStation
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int update(LineManageStation lineManageStation) throws Exception;
	
	/**
	 * 删除途经站点
	 * @return
	 * @throws Exception
	 */
	int delete(Object map) throws Exception;

	int deleteByID(@Param("id") Integer id) throws Exception;

	/**
	 * 按线路删除所有途经站点
	 * @param lMID
	 * @return
	 * @throws Exception
	 */
	int deleteByLMID(Object lMID) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回LineManageStation对象
	 * @throws Exception
	 */
	LineManageStation find(Integer id) throws Exception;
	
	/**
	 * 按线路查询途经站点
	 * @param lMID
	 * @return
	 * @throws Exception
	 */
	List<LineManageStation> findByLMID(Integer lMID) throws Exception;
	
	List<LineManageStation> findByStationTimeRuleID(@Param("strid") Integer strid) throws Exception;
}

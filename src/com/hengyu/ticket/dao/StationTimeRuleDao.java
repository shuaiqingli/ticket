package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.StationTimeRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * @author 李冠锋 2016-03-11
 *
 */
public interface StationTimeRuleDao {
	
	/**
	 * 保存一个对象
	 * @param stationTimeRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int save(StationTimeRule stationTimeRule) throws Exception;

	int delete(@Param("id") Integer id) throws Exception;

	int deleteStationByRuleID(@Param("id") Integer id) throws Exception;

	/**
	 * 更新一个对象
	 * @param stationTimeRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int update(StationTimeRule stationTimeRule) throws Exception;

	//重置默认果子
	int resetDefaultRule(@Param("lmid") Integer lmid) throws Exception;

	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回StationTimeRule对象
	 * @throws Exception
	 */
	StationTimeRule find(Integer id) throws Exception;

	List<StationTimeRule> findByLMID(@Param("lmid") Integer lmid) throws Exception;

	int delStationByTimeRule(StationTimeRule stationTimeRule) throws Exception;

	int delTimeRule(StationTimeRule stationTimeRule) throws Exception;

	List<StationTimeRule> findByGroupID(@Param("groupid") Integer groupid) throws Exception;

}

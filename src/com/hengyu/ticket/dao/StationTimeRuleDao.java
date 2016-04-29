package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.LineManageStation;
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

	Integer checkUpdate(Integer id) throws Exception;

    //按线路查询规则列表
	List<StationTimeRule> findByLMID(@Param("lmid") Integer lmid) throws Exception;

    //删除线路规则站点
	int delStationByTimeRule(StationTimeRule stationTimeRule) throws Exception;

    //删除线路规则
	int delTimeRule(StationTimeRule stationTimeRule) throws Exception;

    //按分组编号查询
	List<StationTimeRule> findByGroupID(@Param("groupid") Integer groupid) throws Exception;

    //获取时间线路规则站点（过滤重复站点）
	List<LineManageStation> getDistinctStationByGroupID(@Param("groupid") Integer groupid) throws Exception;

}

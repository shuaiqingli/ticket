package com.hengyu.ticket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hengyu.ticket.entity.StationLine;
import com.hengyu.ticket.entity.TripPriceRule;

/**
 * 
 * @author 李冠锋 2015-12-29
 *
 */
public interface StationLineDao {
	
	/**
	 * 保存一个对象
	 * @param stationLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(StationLine stationLine) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param stationLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(StationLine stationLine) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回StationLine对象
	 * @throws Exception
	 */
	public abstract StationLine find(Integer id) throws Exception;
	
	/**
	 * 按规则查询列表
	 * @param tprid
	 * @return
	 * @throws Exception
	 */
	public abstract List<StationLine> findByTripPriceRule(TripPriceRule tpr) throws Exception;
	/**
	 * 按线路查询列表
	 * @param lmid
	 * @return
	 * @throws Exception
	 */
	public abstract List<StationLine> findByLMID(@Param("lmid")Integer lmid) throws Exception;
	/**
	 * 按规则删除
	 * @param tprid
	 * @return
	 * @throws Exception
	 */
	public abstract Integer delByTripPriceRule(@Param("tprid")Integer tprid) throws Exception;
}

package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.StationLineDao;
import com.hengyu.ticket.entity.StationLine;
import com.hengyu.ticket.entity.TripPriceRule;

import java.util.List;

/**
 * 
 * @author 李冠锋 2015-12-29
 *
 */
 @Service
public class StationLineService{
	
	@Autowired
	private StationLineDao stationLineDao;
	
	/**
	 * 保存一个对象
	 * @param stationLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(StationLine stationLine) throws Exception{
		return stationLineDao.save(stationLine);
	}
	
	/**
	 * 更新一个对象
	 * @param stationLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(StationLine stationLine) throws Exception{
		return stationLineDao.update(stationLine);
	}
	
	//按规则删除
	public int delByTripPriceRule(Integer tprid) throws Exception{
		return stationLineDao.delByTripPriceRule(tprid);
	}
	
	//	 按规则查询列表
	public List<StationLine> findByTripPriceRule(TripPriceRule tpr) throws Exception{
		return stationLineDao.findByTripPriceRule(tpr);
	}
	
	//按线路查询列表
	public List<StationLine> findByLMID(Integer lmid) throws Exception{
		return stationLineDao.findByLMID(lmid);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回StationLine对象
	 * @throws Exception
	 */
	public StationLine find(Integer id) throws Exception{
		return stationLineDao.find(id);
	}
	
	public void setStationLineDao(StationLineDao stationLineDao){
		this.stationLineDao = stationLineDao;
	}
	
	
}

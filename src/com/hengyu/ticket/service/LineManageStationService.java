package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.LineManageStationDao;
import com.hengyu.ticket.entity.LineManageStation;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
 @Service
public class LineManageStationService{
	
	@Autowired
	private LineManageStationDao lineManageStationDao;
	
	/**
	 * 保存一个对象
	 * @param lineManageStation
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(LineManageStation lineManageStation) throws Exception{
		return lineManageStationDao.save(lineManageStation);
	}
	
	/**
	 * 更新一个对象
	 * @param lineManageStation
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(LineManageStation lineManageStation) throws Exception{
		return lineManageStationDao.update(lineManageStation);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param iD 主键
	 * @return 返回LineManageStation对象
	 * @throws Exception
	 */
	public LineManageStation find(Integer iD) throws Exception{
		return lineManageStationDao.find(iD);
	}
	
	/**
	 * 按线路id查询途经站点
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<LineManageStation> findBylMID(Integer id) throws Exception{
		return lineManageStationDao.findByLMID(id);
	}
	
	public void setLineManageStationDao(LineManageStationDao lineManageStationDao){
		this.lineManageStationDao = lineManageStationDao;
	}
}

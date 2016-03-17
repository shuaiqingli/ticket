package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.RelationStationDao;
import com.hengyu.ticket.entity.RelationStation;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-11-09
 *
 */
 @Service
public class RelationStationService{
	
	@Autowired
	private RelationStationDao relationStationDao;
	
	/**
	 * 保存一个对象
	 * @param relationStation
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(RelationStation relationStation) throws Exception{
		return relationStationDao.save(relationStation);
	}
	
	/**
	 * 更新一个对象
	 * @param relationStation
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(RelationStation relationStation) throws Exception{
		return relationStationDao.update(relationStation);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回RelationStation对象
	 * @throws Exception
	 */
	public RelationStation find(Integer id) throws Exception{
		return relationStationDao.find(id);
	}
	
	//按用户id查询
	public  List<RelationStation> findByUser(String userid) throws Exception{
		return relationStationDao.findByUser(userid);
	};
}

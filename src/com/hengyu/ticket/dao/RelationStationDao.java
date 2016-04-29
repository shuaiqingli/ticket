package com.hengyu.ticket.dao;

import java.util.List;
import java.util.Map;

import com.hengyu.ticket.entity.RelationStation;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author 李冠锋 2015-11-09
 *
 */
public interface RelationStationDao {
	
	/**
	 * 保存一个对象
	 * @param relationStation
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int save(RelationStation relationStation) throws Exception;
	
	//删除
	int deleteByIDS(Map map) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param relationStation
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int update(RelationStation relationStation) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回RelationStation对象
	 * @throws Exception
	 */
	RelationStation find(Integer id) throws Exception;
	
	//按用户id查询
	List<RelationStation> findByUser(@Param("userid") String userid) throws Exception;
	//按多个id查询
}

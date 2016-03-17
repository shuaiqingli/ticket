package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.StaticShowDao;
import com.hengyu.ticket.entity.StaticShow;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-11-09
 *
 */
 @Service
public class StaticShowService{
	
	@Autowired
	private StaticShowDao staticShowDao;
	
	/**
	 * 保存一个对象
	 * @param staticShow
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(StaticShow staticShow) throws Exception{
		return staticShowDao.save(staticShow);
	}
	
	/**
	 * 更新一个对象
	 * @param staticShow
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(StaticShow staticShow) throws Exception{
		return staticShowDao.update(staticShow);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回StaticShow对象
	 * @throws Exception
	 */
	public StaticShow find(Object id) throws Exception{
		return staticShowDao.find(id);
	}
	
	//查询所有
	public List<StaticShow> findAll() throws Exception{
		return staticShowDao.findAll();
	}
	
	public void setStaticShowDao(StaticShowDao staticShowDao){
		this.staticShowDao = staticShowDao;
	}
}

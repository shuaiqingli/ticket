package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.StorePutModelDao;
import com.hengyu.ticket.entity.StorePutModel;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
 @Service
public class StorePutModelService{
	
	@Autowired
	private StorePutModelDao storePutModelDao;
	
	/**
	 * 保存一个对象
	 * @param storePutModel
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(StorePutModel storePutModel) throws Exception{
		return storePutModelDao.save(storePutModel);
	}
	
	/**
	 * 更新一个对象
	 * @param storePutModel
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(StorePutModel storePutModel) throws Exception{
		return storePutModelDao.update(storePutModel);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param tLID 主键
	 * @return 返回StorePutModel对象
	 * @throws Exception
	 */
	public StorePutModel find(String tLID) throws Exception{
		return storePutModelDao.find(tLID);
	}
	
	public void setStorePutModelDao(StorePutModelDao storePutModelDao){
		this.storePutModelDao = storePutModelDao;
	}
}

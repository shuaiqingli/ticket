package com.hengyu.ticket.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.BaseResourceDao;
import com.hengyu.ticket.entity.BaseResource;
import com.hengyu.ticket.entity.Page;

/**
 * 
 * @author 李冠锋 2015-11-09
 *
 */
 @Service
public class BaseResourceService{
	
	@Autowired
	private BaseResourceDao baseResourceDao;
	
	/**
	 * 保存一个对象
	 * @param baseResource
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(BaseResource baseResource) throws Exception{
		return baseResourceDao.save(baseResource);
	}
	
	/**
	 * 更新一个对象
	 * @param baseResource
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(BaseResource baseResource) throws Exception{
		return baseResourceDao.update(baseResource);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回BaseResource对象
	 * @throws Exception
	 */
	public BaseResource find(Integer id) throws Exception{
		return baseResourceDao.find(id);
	}
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<BaseResource> findlist(Page page)throws Exception{
		long count = baseResourceDao.findlistCount(page);
		page.setTotalCount(count);
		List<BaseResource> data = baseResourceDao.findlist(page);
		page.setData(data );
		return data;
	}
	
	public void setBaseResourceDao(BaseResourceDao baseResourceDao){
		this.baseResourceDao = baseResourceDao;
	}
}

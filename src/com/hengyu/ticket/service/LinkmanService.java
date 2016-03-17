package com.hengyu.ticket.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.LinkmanDao;
import com.hengyu.ticket.entity.Linkman;
import com.hengyu.ticket.entity.Page;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
 @Service
public class LinkmanService{
	
	@Autowired
	private LinkmanDao linkmanDao;
	
	/**
	 * 保存一个对象
	 * @param linkman
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(Linkman linkman) throws Exception{
		
		return linkmanDao.save(linkman);
	}
	
	/**
	 * 更新一个对象
	 * @param linkman
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(Linkman linkman) throws Exception{
		return linkmanDao.update(linkman);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param iD 主键
	 * @return 返回Linkman对象
	 * @throws Exception
	 */
	public Linkman find(String id) throws Exception{
		return linkmanDao.find(id);
	}
	
	/**
	 * 删除联系人
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(String id) throws Exception{
		return linkmanDao.delete(id);
	}
	
	/**
	 * @see com.hengyu.ticket.service.LinkmanService
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Linkman findByMobile(Linkman l) throws Exception{
		return linkmanDao.findByMobile(l);
	}
	
	/**
	 * 查询联系人
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<Linkman> findLinks(Page page) throws Exception{
		long count = linkmanDao.findLinksCount(page);
		page.setTotalCount(count);
		return linkmanDao.findLinks(page);
	}
	
	public void setLinkmanDao(LinkmanDao linkmanDao){
		this.linkmanDao = linkmanDao;
	}
}

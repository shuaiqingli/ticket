package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.TicketLineStoreDao;
import com.hengyu.ticket.entity.TicketLineStore;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-12-31
 *
 */
 @Service
public class TicketLineStoreService{
	
	@Autowired
	private TicketLineStoreDao ticketLineStoreDao;
	
	/**
	 * 保存一个对象
	 * @param ticketLineStore
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(TicketLineStore ticketLineStore) throws Exception{
		return ticketLineStoreDao.save(ticketLineStore);
	}
	
	/**
	 * 更新一个对象
	 * @param ticketLineStore
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(TicketLineStore ticketLineStore) throws Exception{
		return ticketLineStoreDao.update(ticketLineStore);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回TicketLineStore对象
	 * @throws Exception
	 */
	public TicketLineStore find(Integer id) throws Exception{
		return ticketLineStoreDao.find(id);
	}
	
	public void setTicketLineStoreDao(TicketLineStoreDao ticketLineStoreDao){
		this.ticketLineStoreDao = ticketLineStoreDao;
	}
}

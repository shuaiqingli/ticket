package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.SmsHistoryDao;
import com.hengyu.ticket.entity.SmsHistory;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
 @Service
public class SmsHistoryService{
	
	@Autowired
	private SmsHistoryDao smsHistoryDao;
	
	/**
	 * 保存一个对象
	 * @param smsHistory
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(SmsHistory smsHistory) throws Exception{
		return smsHistoryDao.save(smsHistory);
	}
	
	/**
	 * 更新一个对象
	 * @param smsHistory
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(SmsHistory smsHistory) throws Exception{
		return smsHistoryDao.update(smsHistory);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param mobile 手机号码
	 * @return 返回SmsHistory对象
	 * @throws Exception
	 */
	public SmsHistory findlastByMobile(String mobile) throws Exception{
		return smsHistoryDao.findLastByMobile(mobile);
	}
	
	public void setSmsHistoryDao(SmsHistoryDao smsHistoryDao){
		this.smsHistoryDao = smsHistoryDao;
	}
}

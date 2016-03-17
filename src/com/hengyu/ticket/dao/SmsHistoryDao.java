package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.SmsHistory;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
public interface SmsHistoryDao {
	
	/**
	 * 保存一个对象
	 * @param smsHistory
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(SmsHistory smsHistory) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param smsHistory
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(SmsHistory smsHistory) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param iD 主键
	 * @return 返回SmsHistory对象
	 * @throws Exception
	 */
	public abstract SmsHistory findLastByMobile(String mobile) throws Exception;
}

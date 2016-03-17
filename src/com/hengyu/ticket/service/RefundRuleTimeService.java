package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.RefundRuleTimeDao;
import com.hengyu.ticket.entity.RefundRuleTime;
import java.util.List;

/**
 * 
 * @author 李冠锋 2016-01-14
 *
 */
 @Service
public class RefundRuleTimeService{
	
	@Autowired
	private RefundRuleTimeDao refundRuleTimeDao;
	
	/**
	 * 保存一个对象
	 * @param refundRuleTime
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(RefundRuleTime refundRuleTime) throws Exception{
		return refundRuleTimeDao.save(refundRuleTime);
	}
	
	/**
	 * 更新一个对象
	 * @param refundRuleTime
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(RefundRuleTime refundRuleTime) throws Exception{
		return refundRuleTimeDao.update(refundRuleTime);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回RefundRuleTime对象
	 * @throws Exception
	 */
	public RefundRuleTime find(Integer id) throws Exception{
		return refundRuleTimeDao.find(id);
	}
	
	public List<RefundRuleTime> findByRule(Integer rrid) throws Exception{
		return refundRuleTimeDao.findByRule(rrid);
	}
	
	public void setRefundRuleTimeDao(RefundRuleTimeDao refundRuleTimeDao){
		this.refundRuleTimeDao = refundRuleTimeDao;
	}
}

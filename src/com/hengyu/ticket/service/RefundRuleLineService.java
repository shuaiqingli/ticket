package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.RefundRuleLineDao;
import com.hengyu.ticket.entity.RefundRuleLine;
import java.util.List;

/**
 * 
 * @author 李冠锋 2016-01-14
 *
 */
 @Service
public class RefundRuleLineService{
	
	@Autowired
	private RefundRuleLineDao refundRuleLineDao;
	
	/**
	 * 保存一个对象
	 * @param refundRuleLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(RefundRuleLine refundRuleLine) throws Exception{
		return refundRuleLineDao.save(refundRuleLine);
	}
	
	/**
	 * 更新一个对象
	 * @param refundRuleLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(RefundRuleLine refundRuleLine) throws Exception{
		return refundRuleLineDao.update(refundRuleLine);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回RefundRuleLine对象
	 * @throws Exception
	 */
	public RefundRuleLine find(Integer id) throws Exception{
		return refundRuleLineDao.find(id);
	}
	
	public void setRefundRuleLineDao(RefundRuleLineDao refundRuleLineDao){
		this.refundRuleLineDao = refundRuleLineDao;
	}
}

package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.SeatRuleDao;
import com.hengyu.ticket.entity.SeatRule;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-11-14
 *
 */
 @Service
public class SeatRuleService{
	
	@Autowired
	private SeatRuleDao seatRuleDao;
	
	/**
	 * 保存一个对象
	 * @param seatRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(SeatRule seatRule) throws Exception{
		return seatRuleDao.save(seatRule);
	}
	
	/**
	 * 更新一个对象
	 * @param seatRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(SeatRule seatRule) throws Exception{
		return seatRuleDao.update(seatRule);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回SeatRule对象
	 * @throws Exception
	 */
	public SeatRule find(Integer id) throws Exception{
		return seatRuleDao.find(id);
	}
	
	public void setSeatRuleDao(SeatRuleDao seatRuleDao){
		this.seatRuleDao = seatRuleDao;
	}
}

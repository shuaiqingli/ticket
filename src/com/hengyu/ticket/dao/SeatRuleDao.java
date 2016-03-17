package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.SeatRule;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-11-14
 *
 */
public interface SeatRuleDao {
	
	/**
	 * 保存一个对象
	 * @param seatRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(SeatRule seatRule) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param seatRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(SeatRule seatRule) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回SeatRule对象
	 * @throws Exception
	 */
	public abstract SeatRule find(Integer id) throws Exception;
}

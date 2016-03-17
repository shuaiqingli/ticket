package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.RefundRuleTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author 李冠锋 2016-01-14
 *
 */
public interface RefundRuleTimeDao {
	
	/**
	 * 保存一个对象
	 * @param refundRuleTime
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(RefundRuleTime refundRuleTime) throws Exception;
	//删除
	public abstract int delete(Integer id) throws Exception;
	//按规则删除
	public abstract int deleteByRrid(@Param("rrid")Integer rrid) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param refundRuleTime
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(RefundRuleTime refundRuleTime) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回RefundRuleTime对象
	 * @throws Exception
	 */
	public abstract RefundRuleTime find(Integer id) throws Exception;
	
	//按规则查询
	public abstract List<RefundRuleTime> findByRule(@Param("rrid")Integer rrid) throws Exception;
}

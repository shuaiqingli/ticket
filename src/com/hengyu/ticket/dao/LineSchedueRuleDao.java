package com.hengyu.ticket.dao;

import java.util.List;
import java.util.Map;

import com.hengyu.ticket.entity.LineSchedueRule;

/**
 * 
 * @author 李冠锋 2015-11-14
 *
 */
public interface LineSchedueRuleDao {
	
	/**
	 * 保存一个对象
	 * @param lineSchedueRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(LineSchedueRule lineSchedueRule) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param lineSchedueRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(LineSchedueRule lineSchedueRule) throws Exception;
	
	/**
	 * 删除排班规则
	 * @param lineSchedueRule
	 * @return
	 * @throws Exception
	 */
	public abstract int delete(LineSchedueRule lineSchedueRule) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回LineSchedueRule对象
	 * @throws Exception
	 */
	public abstract LineSchedueRule find(Integer id) throws Exception;
	
	/**
	 * 按排班查询所有规则
	 * @param lsid
	 * @return
	 * @throws Exception
	 */
	public abstract List<LineSchedueRule> findByLineSchedule(Integer lsid) throws Exception;
	
	/**
	 * 查询没有排班的星期
	 * @param lineSchedueRule
	 * @return
	 * @throws Exception
	 */
	public abstract LineSchedueRule findNotShiftScheduleRule(LineSchedueRule lineSchedueRule) throws Exception;
	
	/**
	 * 查询已排版的星期
	 * @param lineSchedueRule
	 * @return
	 * @throws Exception
	 */
	public abstract Integer findShiftScheduleRuleWeeks(LineSchedueRule lineSchedueRule) throws Exception;
	
	/**
	 * 按排班查询规则，排班的星期
	 * @param lsid
	 * @return
	 * @throws Exception
	 */
	public abstract Map<String,Integer> findweekScheduleRule(LineSchedueRule lsr) throws Exception;
}

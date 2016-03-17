package com.hengyu.ticket.dao;

import java.util.List;

import com.hengyu.ticket.entity.LineScheduDetail;
import com.hengyu.ticket.entity.LineSchedueRule;

/**
 * 
 * @author 李冠锋 2015-11-14
 *
 */
public interface LineScheduDetailDao {
	
	/**
	 * 保存一个对象
	 * @param lineScheduDetail
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int save(LineScheduDetail lineScheduDetail) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param lineScheduDetail
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int update(LineScheduDetail lineScheduDetail) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回LineScheduDetail对象
	 * @throws Exception
	 */
	LineScheduDetail find(Integer id) throws Exception;
	
	/**
	 * 按排班规则查询，班次详情
	 * @param lsuid
	 * @return
	 * @throws Exception
	 */
	List<LineScheduDetail> findByLineScheduleRule(Integer lsuid) throws Exception;
	
	/**
	 * 按排班规则删除，班次详情
	 * @param lsuid
	 * @return
	 * @throws Exception
	 */
	int deleteByLineSchedule(Integer lsuid) throws Exception;
	
	/**
	 * 删除排班详情
	 * @param lsuid
	 * @return
	 * @throws Exception
	 */
	int delete(Integer lsuid) throws Exception;
	
	/**
	 * 查询排班规则，首发时间，和班车数量
	 * @param lsr
	 * @return
	 * @throws Exception
	 */
	LineSchedueRule findLineScheduleRuleInfo(LineSchedueRule lsr) throws Exception;
}

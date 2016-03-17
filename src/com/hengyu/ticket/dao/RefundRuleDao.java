package com.hengyu.ticket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.RefundRule;

/**
 * 
 * @author 李冠锋 2016-01-14
 *
 */
public interface RefundRuleDao {
	
	/**
	 * 保存一个对象
	 * @param refundRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(RefundRule refundRule) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param refundRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(RefundRule refundRule) throws Exception;
	
	//删除
	public abstract int delete(Integer id) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回RefundRule对象
	 * @throws Exception
	 */
	public abstract RefundRule find(Integer id) throws Exception;
	
	//按出发日期，线路编号查询规则和时间段
	public abstract List<RefundRule> findRuleByLmidTicketDateRank(@Param("lmid")Integer lmid,@Param("ticketdate")String ticketdate,@Param("rank")Integer rank) throws Exception;
	
	//查询列表
	public abstract List<RefundRule> findlist(Page page) throws Exception;
	
	//按规则查询线路
	public abstract List<LineManage> findLineManageByRule(Page page) throws Exception;
	//统计线路记录数
	public abstract long findLineManageByRuleCount(Page page) throws Exception;
	
	//统计记录数
	public abstract long count(Page page) throws Exception;
}

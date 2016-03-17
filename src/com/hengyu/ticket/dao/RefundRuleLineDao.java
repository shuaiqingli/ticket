package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.RefundRuleLine;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author 李冠锋 2016-01-14
 *
 */
public interface RefundRuleLineDao {
	
	/**
	 * 保存一个对象
	 * @param refundRuleLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(RefundRuleLine refundRuleLine) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param refundRuleLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(RefundRuleLine refundRuleLine) throws Exception;
	
	//删除
	public abstract int deleteByLmidRrid(@Param("lmid")Integer lmid,@Param("rrid")Integer rrid) throws Exception;
	//按线路编号删除，删除线路的时候适用
	public abstract int deleteByLmid(@Param("lmid")Integer lmid) throws Exception;
	//按规则删除
	public abstract int deleteByRrid(@Param("rrid")Integer rrid) throws Exception;
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回RefundRuleLine对象
	 * @throws Exception
	 */
	public abstract RefundRuleLine find(Integer id) throws Exception;
	
	//按线路编号和规则查询
	public abstract RefundRuleLine findByLmidRrid(@Param("lmid")Integer lmid,@Param("rrid")Integer rrid) throws Exception;
}

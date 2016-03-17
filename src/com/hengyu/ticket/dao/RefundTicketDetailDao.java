package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.RefundTicketDetail;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author 李冠锋 2016-01-19
 *
 */
public interface RefundTicketDetailDao {
	
	/**
	 * 保存一个对象
	 * @param refundTicketDetail
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(RefundTicketDetail refundTicketDetail) throws Exception;
	
	//删除
	public abstract int deleteByRefundno(@Param("refundno")String refundno) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param refundTicketDetail
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(RefundTicketDetail refundTicketDetail) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回RefundTicketDetail对象
	 * @throws Exception
	 */
	public abstract RefundTicketDetail find(Integer id) throws Exception;
	
	public abstract List<RefundTicketDetail> findByRefundNo(@Param("refundno")String refundno) throws Exception;
}

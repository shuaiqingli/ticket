package com.hengyu.ticket.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.RefundTicket;
import com.hengyu.ticket.entity.RefundTicketDetail;

/**
 * 
 * @author 李冠锋 2015-12-29
 *
 */
public interface RefundTicketDao {

	/**
	 * 保存一个对象
	 * 
	 * @param refundTicket
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(RefundTicket refundTicket) throws Exception;

	/**
	 * 更新一个对象
	 * 
	 * @param refundTicket
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(RefundTicket refundTicket) throws Exception;
	
	//删除
	public abstract int delete(String id) throws Exception;
	
	//更新退款状态
	public abstract int updateRefund(RefundTicket refundTicket) throws Exception;

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回RefundTicket对象
	 * @throws Exception
	 */
	public abstract RefundTicket find(String id) throws Exception;

	/**
	 * 查询退款订单列表
	 * 
	 * @param page
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public abstract List<RefundTicket> findlist(Page page) throws Exception;

	// 查询退款详情
	public abstract List<RefundTicketDetail> findDetails(@Param("rtid") String rtid) throws Exception;

	/**
	 * 统计记录数
	 * 
	 * @param page
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public abstract long count(Page page) throws Exception;

	// 获取待确认退票数量
	public abstract long waitAffirmRefundTicketQuantity(Map a) throws Exception;

	// 获取待确认退票列表
	public abstract List waitAffirmRefundTicketList(Map a) throws Exception;
	
	// 确认退票
	public abstract int refundTicketAffirm(Map a) throws Exception;
	
	/**
	 * 根据外部号进行退费
	 * @param batch_no
	 * @return
	 */
	public abstract RefundTicket findByOutCode(String batch_no);
}

package com.hengyu.ticket.dao;

import java.util.List;
import java.util.Map;

import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.TicketLine;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
public interface TicketLineDao {

	/**
	 * 保存一个对象
	 * 
	 * @param ticketLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(TicketLine ticketLine) throws Exception;
	
	/**
	 * 批量保存
	 * @param ticketLines
	 * @return
	 * @throws Exception
	 */
	public abstract int batchSave(List<TicketLine> ticketLines) throws Exception;

	/**
	 * 更新一个对象
	 * 
	 * @param ticketLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(TicketLine ticketLine) throws Exception;
	
	//更新车票线路价格
	public abstract int updateprice(TicketLine ticketLine) throws Exception;
	//取消班次后取消车票线路
	public abstract int cancelTicketLine(Map m) throws Exception;

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回TicketLine对象
	 * @throws Exception
	 */
	public abstract TicketLine find(Integer id) throws Exception;
	
	/**
	 * 按班次号，
	 * 出发日期，
	 * 线路编号，
	 * 班次编号，
	 * 出发城市编号，
	 * 到达城市编号
	 * <br/>
	 * 查询唯一的车票线路详情
	 * @param tl
	 * @return
	 * @throws Exception
	 */
	public abstract TicketLine findUniqueTicketLine(TicketLine tl) throws Exception;

	/**
	 * 查询车票列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public abstract List<Map> findTicketList(Page page) throws Exception;
	
	/**
	 * 查询车票总记录数量
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public abstract long findTicketListCount(Page page) throws Exception;

	/**
	 * 修改排班，检查日期是否在审核日期范围
	 * @return
	 */
	public abstract String getCheckApproveTicketDate(TicketLine tl) throws Exception;
	
	/**
	 * 当前审核的最大日期
	 * @param tl
	 * @return
	 * @throws Exception
	 */
	public abstract String getMaxApproveTicketDate(TicketLine tl) throws Exception;
	
	/**
	 * 修改排班日期后删除车票
	 * @param tl
	 * @return
	 * @throws Exception
	 */
	public abstract int delNotApproveTicketLine(TicketLine tl) throws Exception;
	
	//根据班次查询各站点的票价
	public abstract List getTicketLineByShiftCode(Map a)throws Exception;
}

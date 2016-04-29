package com.hengyu.ticket.dao;

import java.util.List;
import java.util.Map;

import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.SaleOrder;
import com.hengyu.ticket.entity.TicketLine;
import org.apache.ibatis.annotations.Param;

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
	int save(TicketLine ticketLine) throws Exception;
	
	/**
	 * 批量保存
	 * @param ticketLines
	 * @return
	 * @throws Exception
	 */
	int batchSave(List<TicketLine> ticketLines) throws Exception;

	/**
	 * 更新一个对象
	 * 
	 * @param ticketLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int update(TicketLine ticketLine) throws Exception;
	
	//更新车票线路价格
	int updateprice(TicketLine ticketLine) throws Exception;

    //更新匹配价格车票数量
	int updateMatchQuantityPrice(TicketLine ticketLine) throws Exception;

	//取消班次后取消车票线路
	int cancelTicketLine(Map m) throws Exception;

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回TicketLine对象
	 * @throws Exception
	 */
	TicketLine find(Integer id) throws Exception;
	
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
	TicketLine findUniqueTicketLine(TicketLine tl) throws Exception;

	/**
	 * 查询车票列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	List<Map> findTicketList(Page page) throws Exception;
	
	/**
	 * 查询车票总记录数量
	 * @param page
	 * @return
	 * @throws Exception
	 */
	long findTicketListCount(Page page) throws Exception;

	/**
	 * 修改排班，检查日期是否在审核日期范围
	 * @return
	 */
	String getCheckApproveTicketDate(TicketLine tl) throws Exception;
	
	/**
	 * 当前审核的最大日期
	 * @param tl
	 * @return
	 * @throws Exception
	 */
	String getMaxApproveTicketDate(TicketLine tl) throws Exception;
	
	/**
	 * 修改排班日期后删除车票
	 * @param tl
	 * @return
	 * @throws Exception
	 */
	int delNotApproveTicketLine(TicketLine tl) throws Exception;
	
	/**
	 * 根据班次查询各站点的票价(不要再用)
	 * @param a
	 * @return
	 * @throws Exception
	 */
	@Deprecated 
	List getTicketLineByShiftCode(Map a)throws Exception;

    Integer getTicketLineNotDataTypeTip(@Param("param") TicketLine ticketLine) throws Exception;

	/**
	 * 根据多个条件找Shift对象数据
	 * @param map
	 * @return
	 */
	public abstract List<TicketLine> findShiftByCityStartIDAndCityArriveIDAndRide(Map<String, Object> map);

	int updateQuantity(@Param("id")Integer id, @Param("quantity")Integer quantity, @Param("discountquantity")Integer discountquantity);

	int addQuantity(@Param("id")Integer id, @Param("quantity")Integer quantity, @Param("discountquantity")Integer discountquantity);
	
	List<Map> getTicketLineByShiftId(Long ShiftID);

	List<Map> getStationListForShift(@Param("shiftid") Long shiftid);
}

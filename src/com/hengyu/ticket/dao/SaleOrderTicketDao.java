package com.hengyu.ticket.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hengyu.ticket.entity.SaleOrderTicket;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author 李冠锋 2015-10-29
 *
 */
public interface SaleOrderTicketDao {

	/**
	 * 保存一个对象
	 * 
	 * @param saleOrderTicket
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(SaleOrderTicket saleOrderTicket) throws Exception;

	//过期更新状态
	public abstract int updateDateOutOrder() throws Exception;

	/**
	 * 更新一个对象
	 * 
	 * @param saleOrderTicket
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(SaleOrderTicket saleOrderTicket) throws Exception;
	
	//更新退款状态
	public abstract int updateRefundStatus(@Param("checkcode")String checkcode,@Param("newstatus")int newstatus,
			@Param("oldstatus")int oldstatus,@Param("paystatus")Integer paystatus,@Param("paystatusname")String paystatusname) throws Exception;

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param checkcode
	 *            主键
	 * @return 返回SaleOrderTicket对象
	 * @throws Exception
	 */
	public abstract SaleOrderTicket find(String checkcode) throws Exception;
	
	//按订单号查询
	public abstract List<SaleOrderTicket> findBySaleOrderId(String soid) throws Exception;

	// 根据SOID获sale_order_ticket列表
	public abstract List getSaleOrderTicketBySOIDTC(Map c) throws Exception;

	// 根据SOID查已取票数量
	public abstract long getAlreadyTakeBySOID(Map a) throws Exception;

	// 打印车票
	public abstract int printSaleOrderTicket(Map a) throws Exception;

	// 查找车票打印信息
	public abstract Map findTicketPrint(Map a) throws Exception;

	// 验票前对比资料
	public abstract SaleOrderTicket constrastTicket(Map a) throws Exception;

	// 验票
	public abstract int checkTicket(Map a) throws Exception;

	// 异常验票
	public abstract int abnormalUpdateShiftNum(Map a) throws Exception;

	// 查找班次未验票数量
	public abstract long getNoOnTrain(Map a) throws Exception;

	// 按取票码查找已验票人数
	public abstract long getAlreadyOnTrain(Map a) throws Exception;

	// 整单验票
	public abstract int saleorderCheck(Map a) throws Exception;

	// 整单异常验票
	public abstract int abnormalUpdateShiftNumByTicketCode(Map a) throws Exception;

	int cancelLockSaleOrderTicketByCheckCode(@Param("soid") String soid, @Param("checkcodeListForPay")List<String> checkcodeListForPay);

	int payLockSaleOrderTicketByCheckCode(@Param("soid") String soid, @Param("checkcodeListForPay")List<String> checkcodeListForPay);

	List<SaleOrderTicket> findSaleOrderTicketListWithoutCheckCode(@Param("soid") String soid, @Param("checkcodeList")List<String> checkcodeList);
	
	//站务整单退票，修改订单子表
	public abstract int ticketRefundBySOID(Map b);
	
	//站务整单退票，修改订单子表
	public abstract int ticketDealByCheckCode(Map b);
	
	List<Map> findSeatNOListForLock(@Param("lmid") Integer lmid, @Param("shiftnum") String shiftnum, @Param("ridedate") String ridedate);
	
	//获取订单中的有效票数量
	public abstract int getValidateTicketCountBySOID(Map a)throws Exception;
	
	//已售
	public abstract long getSoldQuantity(Map c) throws Exception;
}

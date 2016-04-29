package com.hengyu.ticket.dao;

import java.util.List;
import java.util.Map;

import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.SaleOrder;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
@SuppressWarnings("rawtypes")
public interface SaleOrderDao {

	/**
	 * 保存一个对象
	 * 
	 * @param saleOrder
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(SaleOrder saleOrder) throws Exception;

	/**
	 * 更新一个对象
	 * 
	 * @param saleOrder
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(SaleOrder saleOrder) throws Exception;

	//过期更新状态
	public abstract int updateDateOutOrder() throws Exception;

	/**
	 * 更新付款状态
	 * 
	 * @param saleOrder
	 * @return
	 * @throws Exception
	 */
	public abstract int updatePay(SaleOrder saleOrder) throws Exception;
	
	//更新退款状态
	public abstract int updateRefundStatus(@Param("id")String id,@Param("newstatus")Integer newstatus,
			@Param("ticketstatus")Integer ticketstatus,@Param("statusname") String statusname,
			@Param("paystatus")Integer paystatus,@Param("paystatusname")String paystatusname) throws Exception;
	
	//锁定车票
	public abstract int updateLockPay(SaleOrder saleOrder) throws Exception;

	/**
	 * 更新支付加密信息
	 * 
	 * @param saleOrder
	 * @return
	 * @throws Exception
	 */
	public abstract int updatePayFeedBack(SaleOrder saleOrder) throws Exception;

	public abstract int updateStatus(SaleOrder saleOrder) throws Exception;
	
	//更新积分状态
	public abstract int updateOrderIntegralStatus(SaleOrder saleOrder) throws Exception;
	
	
	/**
	 * 更新订单状态，取消或退票
	 * 
	 * @param saleOrder
	 * @return
	 * @throws Exception
	 */
	public abstract int updateOrderReturned(SaleOrder saleOrder) throws Exception;

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回SaleOrder对象
	 * @throws Exception
	 */
	public abstract SaleOrder find(String id) throws Exception;

	// 按取票码查询，ticketcode
	public abstract SaleOrder getSaleOrderByTicketCode(String ticketcode) throws Exception;

	// 整单取票按取票码查询，不判断status
	public abstract SaleOrder getAllSaleOrderByTicketCode(String ticketcode) throws Exception;

	// 根据ridedate(出行日期)，lineid(线路ID),shiftnum(班次)统计sale_order表的quantity
	public abstract Long getSoldQuantity(Map c) throws Exception;

	// 统计未取票数量,条件：lineid线路id,shiftnum班次，ridedate日期,paysttus=1已支付，status=0未取票
	public abstract Long getNoTakeQuantity(Map q) throws Exception;

	// 订单购票人数列表，条件：lineid线路id,shiftnum班次，ridedate日期,paysttus=1已支付
	public abstract List getAlreadyTake(Map c) throws Exception;

	// // 增加订单异常备注,id,memo
	// public abstract int addSaleOrderMemo(Map a) throws Exception;

	// 订单搜索，条件：ststartid,keywords(查订单号id，取票人lname,手机lmobile)
	public abstract List searchSaleOrder(Map a) throws Exception;

	// 获取有异常的订单记录
	public abstract List getAbnormalSaleOrderList(Map a) throws Exception;

	// 获取有异常的订单记录条数
	public abstract Long totalCountAbnormal(Map a) throws Exception;

	/**
	 * APP查询订单列表
	 * 
	 * @param page
	 * @return
	 */
	public abstract List getOrderlist(Page page);

	/**
	 * 后台查询订单列表
	 * 
	 * @param page
	 * @return
	 */
	public abstract List<SaleOrder> findOrderList(Page page);
	
	//获取未结算积分的订单
	public abstract List<SaleOrder> findOrderIntegral();

	/**
	 * 后台查询订单记录
	 * 
	 * @param page
	 * @return
	 */
	public abstract long findOrderListCount(Page page);

	/**
	 * APP统计订单记录数
	 * 
	 * @param page
	 * @return
	 */
	public abstract long getOrderlistCount(Page page);

	/**
	 * 获取所有没有支付的订单
	 * 
	 * @param page
	 * @return
	 */
	public abstract List<SaleOrder> getNotPayOrder(Map m);
	
	//订单异常验票
	public abstract int abnormalUpdateSaleOrderShiftNum(Map a);

	//站点查询统计班次买票情况
	List<Map> stationStatisticList(@Param("stationidList") List<String> stationidList, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("keyword") String keyword, @Param("page") Page page);

	long stationStatisticTotal(@Param("stationidList") List<String> stationidList, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("keyword") String keyword);

	List findSaleOrderListForAdmin(@Param("keyword") String keyword, @Param("type") Integer type, @Param("rideDate") String rideDate, @Param("makeDate") String makeDate, @Param("page") Page page);

	Long findSaleOrderTotalForAdmin(@Param("keyword") String keyword, @Param("type") Integer type, @Param("rideDate") String rideDate, @Param("makeDate") String makeDate);

	int cancelLockSaleOrder(@Param("id") String id, @Param("receiveid")String receiveid, @Param("receivename") String receivename, @Param("receivedate")String receivedate);

	int payLockSaleOrder(@Param("id") String id, @Param("receiveid")String receiveid, @Param("receivename") String receivename, @Param("receivedate")String receivedate);
	
	//站务整单退票
	public abstract int saleOrderRefund(Map a);

	//站务收款
	public abstract int saleOrderReceive(Map a);


	List findAdminSettleList(@Param("keyword") String keyword, @Param("page") Page page);

	Long findAdminSettleTotal(@Param("keyword") String keyword);

	List findAdminSettleDetailList(@Param("receiveid")String receiveid, @Param("page") Page page);

	Long findAdminSettleDetailTotal(@Param("receiveid")String receiveid);

	int updateForSettle(@Param("receiveid")String receiveid, @Param("idList") List<String> idList);

	List<Map> findMiles(@Param("cid") String cid);

	//对账统计
	List<Map> billStatisticList(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("keywordList") List<String> keywordList, @Param("page") Page page);

	long billStatisticTotal(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("keywordList") List<String> keywordList);

	/**
	 * 根据取票码获取订单(可能取票码不是唯一 慎用)
	 * @param ticketcode
	 * @return
	 */
	public abstract SaleOrder findByTicketCode(String ticketcode);
	
	/**
	 * 根据线路编号,出行日期,出发站ID,到达站ID,班次ID,以及发车时间
	 * @param map
	 * @return 已售了多少票
	 */
	public abstract Integer findSoldNumByMap(Map<String, Object> map);
}

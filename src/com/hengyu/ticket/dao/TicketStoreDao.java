package com.hengyu.ticket.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.SaleOrder;
import com.hengyu.ticket.entity.TicketLine;
import com.hengyu.ticket.entity.TicketStore;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
public interface TicketStoreDao {

	/**
	 * 保存一个对象
	 * 
	 * @param ticketStore
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int save(TicketStore ticketStore) throws Exception;
	
	/**
	 * 批量保存操作
	 * @param ticketStores
	 * @return
	 * @throws Exception
	 */
	int batchSave(List<TicketStore> ticketStores) throws Exception;

	/**
	 * 更新一个对象
	 * 
	 * @param ticketStore
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int update(TicketStore ticketStore) throws Exception;

    //更新发布状态
	int updateReleaseStatus(@Param("id") Integer id, @Param("isrelease") Integer isrelease, @Param("oldrelease") Integer oldrelease) throws Exception;

    int updateReleaseStatusByDate(@Param("shiftcode") String shiftcode, @Param("begindate") String begindate, @Param("enddate") String enddate, @Param("lmid") Integer lmid, @Param("isrelease") Integer isrelease, @Param("oldrelease") Integer oldrelease) throws Exception;

	//重置审核状态
	int updateResetApprove(@Param("ticketdate")String ticketdate,@Param("lmid")Integer lmid,
			@Param("isapprove")Integer isapprove) throws Exception;
	
	/**
	 * 更新审核票库
	 * @param ticketStore
	 * @return
	 * @throws Exception
	 */
	int updateMatchQuantity(TicketStore ticketStore) throws Exception;
	
	

	/**
	 * 更新余票（下单）
	 * @param id
	 * 编号
	 * @param quantity
	 * 减少数量
	 * @return
	 * @throws Exception
	 */
	int updateQuantity(@Param("id") Integer id,@Param("quantity") Integer quantity,@Param("balancecouponquantity") Integer balancecouponquantity) throws Exception;
	
	/**
	 * 添加余票
	 * @param id 
	 * 编号
	 * @param quantity
	 * 添加数量
	 * @return
	 * @throws Exception
	 */
	int addQuantity(@Param("id") Integer id,@Param("quantity") Integer quantity,@Param("balancecouponquantity") Integer balancecouponquantity) throws Exception;

	/**
	 * 根据主键查询一个对象
	 * 
	 *            主键
	 * @return 返回TicketStore对象
	 * @throws Exception
	 */
	TicketStore find(Integer id) throws Exception;
	
	TicketStore findByLmidDateShiftCode(@Param("lmid")Integer lmid,@Param("ticketdate")String ticketdate,@Param("shiftcode")String shiftcode) throws Exception;

    TicketStore findShiftID(@Param("shiftid")Long shiftid) throws Exception;

	/**
	 * 按订单查询唯一的票库
	 * @param s
	 * @return
	 * @throws Exception
	 */
	TicketStore findBySaleOrder(SaleOrder s) throws Exception;
	
	/**
	 * 按日期查询生成票记录
	 * @param dates
	 * @return
	 * @throws Exception
	 */
	List<TicketStore> findByDates(List<String> dates) throws Exception;
	
	/**
	 * 查询余票
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<Map> findQuantityBalance(Page page) throws Exception;
	
	
	/**
	 * 按日期查询票库，车票线路
	 * @param ts
	 * @return
	 * @throws Exception
	 */
	List<TicketStore> ticketmanage(TicketStore ts) throws Exception;
	
	
	/**
	 * 统计余票记录
	 * @param page
	 * @return
	 * @throws Exception
	 */
	long findQuantityBalanceCount(Page page) throws Exception;
	
	/**
	 * 修改排班日期后删除，未审核的车票关联的票库
	 * @param tl
	 * @return
	 * @throws Exception
	 */
	long delNotApproveTicketStore(TicketLine tl) throws Exception;
	
	/**
	 * 自动发布车票
	 * @param lm
	 * @return
	 * @throws Exception
	 */
	int updateTicketStoreReleaseByLineManage(LineManage lm) throws Exception;

	/**
	 * 按线路号，开始结束日期（按月查询）
	 * @param lmid
	 * @param begindate
	 * @param enddate
	 * @return
	 */
	List<Map<String,String>> findApproveReleaseDates(@Param("lmid")Integer lmid,@Param("begindate")String begindate,@Param("enddate")String enddate);
	
	/**
	 * 按线路编号统计已审核天数，已发布天数，总天数
	 * @param lmid
	 * @return
	 */
	Map<String,Integer> findAllApproveReleaseDates(@Param("lmid")Integer lmid);
	
	// 按lineid,shiftcode,ticketdate
	List getTicketStoreByLST(Map a)
			throws Exception;

	//按班次取消车票,
	int cancelTicketStore(Map a);

	//释放锁定票
	int releaseQuantity(Map e);
	
	//站务退票
	int releaseZWReleaseQuantity(Map e);
	
	//站务退票锁票
	int releaseZWLockQuantity(Map e);

	int updateQuantityForFreeze(@Param("id") Integer id,@Param("quantity") Integer quantity);

	int updateQuantityForUnfreeze(@Param("id") Integer id,@Param("quantity") Integer quantity);

	int updateQuantityByAdminWithoutSeat(@Param("id") Integer id,@Param("quantity") Integer quantity);

	int updateQuantityByAdminWithSeat(@Param("id") Integer id,@Param("balancequantity") Integer balancequantity, @Param("lockquantity") Integer lockquantity);

	int addQuantityToLock(@Param("id") Integer id,@Param("quantity") Integer quantity);

	TicketStore findForUpdate(Integer id);

	TicketStore findByShiftID(@Param("shiftid")Long shiftid);
	
	/**
	 * 根据班次ID和线路ID查询
	 * @param map
	 * @return
	 */
	public abstract TicketStore findByShiftIdAndLMID(Map<String, Object> map);
}

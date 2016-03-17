package com.hengyu.ticket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hengyu.ticket.entity.SaleOrder;
import com.hengyu.ticket.entity.TicketLine;
import com.hengyu.ticket.entity.TicketLineStore;

/**
 * 
 * @author 李冠锋 2015-12-31
 *
 */
public interface TicketLineStoreDao {
	
	/**
	 * 保存一个对象
	 * @param ticketLineStore
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(TicketLineStore ticketLineStore) throws Exception;
	
	//批量保存站点票库
	public abstract int batchSave(List<TicketLineStore> ticketLineStore) throws Exception;
	
	//删除
	public abstract int delNotApproveTicketLineStore(TicketLine tl) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param ticketLineStore
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(TicketLineStore ticketLineStore) throws Exception;
	
	/**
	 * 更新审核数量
	 * @param ticketLineStore
	 * @return
	 * @throws Exception
	 */
	public abstract int updateApproveQuantity(@Param("tl")TicketLine tl,@Param("id")Integer id,@Param("couponquantity")Integer couponquantity,@Param("allquantity")Integer allquantity) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回TicketLineStore对象
	 * @throws Exception
	 */
	public abstract TicketLineStore find(Integer id) throws Exception;
	
	//按订单查询
	public abstract TicketLineStore findBySaleOrder(SaleOrder so) throws Exception;
	
	//按订单减去余票
	public abstract int updateQuantityByOrder(SaleOrder so) throws Exception;
	
	//按订单加上余票
	public abstract int addQuantityByOrder(SaleOrder so) throws Exception;

	int addQuantity(@Param("id") Integer id, @Param("quantity") Integer quantity, @Param("discountquantity") Integer discountquantity);

}

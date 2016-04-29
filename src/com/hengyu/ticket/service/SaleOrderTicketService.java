package com.hengyu.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.SaleOrderTicketDao;
import com.hengyu.ticket.entity.SaleOrderTicket;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李冠锋 2015-10-29
 *
 */
@Service
public class SaleOrderTicketService {

	@Autowired
	private SaleOrderTicketDao saleOrderTicketDao;

	/**
	 * 保存一个对象
	 * 
	 * @param saleOrderTicket
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(SaleOrderTicket saleOrderTicket) throws Exception {
		return saleOrderTicketDao.save(saleOrderTicket);
	}

	/**
	 * 更新一个对象
	 * 
	 * @param saleOrderTicket
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(SaleOrderTicket saleOrderTicket) throws Exception {
		return saleOrderTicketDao.update(saleOrderTicket);
	}

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param checkcode
	 *            主键
	 * @return 返回SaleOrderTicket对象
	 * @throws Exception
	 */
	public SaleOrderTicket find(String checkcode) throws Exception {
		return saleOrderTicketDao.find(checkcode);
	}

	public List<SaleOrderTicket> findBySaleOrder(String soid) throws Exception {
		return saleOrderTicketDao.findBySaleOrderId(soid);
	}

	public void setSaleOrderTicketDao(SaleOrderTicketDao saleOrderTicketDao) {
		this.saleOrderTicketDao = saleOrderTicketDao;
	}

	// 根据SOID获sale_order_ticket列表
	public List getSaleOrderTicketBySOIDTC(Map c) throws Exception {
		return saleOrderTicketDao.getSaleOrderTicketBySOIDTC(c);
	}

	// 根据SOID查已取票数量
	public long getAlreadyTakeBySOID(Map a) throws Exception {
		return saleOrderTicketDao.getAlreadyTakeBySOID(a);
	}

	// 打印车票
	public int printSaleOrderTicket(Map a) throws Exception {
		return saleOrderTicketDao.printSaleOrderTicket(a);
	}

	// 查找车票打印信息
	public Map findTicketPrint(Map a) throws Exception {
		return saleOrderTicketDao.findTicketPrint(a);
	}

	// 验票前对比资料
	public SaleOrderTicket constrastTicket(Map a) throws Exception {
		return saleOrderTicketDao.constrastTicket(a);
	}

	// 验票
	public int checkTicket(Map a) throws Exception {
		return saleOrderTicketDao.checkTicket(a);
	}

	// 异常验票
	public int abnormalUpdateShiftNum(Map a) throws Exception {
		return saleOrderTicketDao.abnormalUpdateShiftNum(a);
	}

	// 查找班次未验票数量
	public long getNoOnTrain(Map a) throws Exception {
		return saleOrderTicketDao.getNoOnTrain(a);
	}

	// 按取票码查找已验票人数
	public long getAlreadyOnTrain(Map a) throws Exception {
		return saleOrderTicketDao.getAlreadyOnTrain(a);
	}

	// 整单验票
	public int saleorderCheck(Map a) throws Exception {
		return saleOrderTicketDao.saleorderCheck(a);
	}

	// 整单异常验票
	public int abnormalUpdateShiftNumByTicketCode(Map a) throws Exception {
		return saleOrderTicketDao.abnormalUpdateShiftNumByTicketCode(a);
	}

	// 站务整单退票，修改订单子表
	public  int ticketRefundBySOID(Map b){
		return saleOrderTicketDao.ticketRefundBySOID(b);
	}
	
	// 站务收款，修改订单子表
	public  int ticketDealByCheckCode(Map b){
		return saleOrderTicketDao.ticketDealByCheckCode(b);
	}

	public List<Map> findSeatNOListForLock(String shiftid){
		return saleOrderTicketDao.findSeatNOListForLock(shiftid);
	}
	
	//获取订单中的有效票数量
	public int getValidateTicketCountBySOID(Map a)throws Exception{
		return saleOrderTicketDao.getValidateTicketCountBySOID(a);
	}

	public void updateDateOutOrder() throws Exception {
		saleOrderTicketDao.updateDateOutOrder();
	}
	
	public long getSoldQuantity(Map c) throws Exception{
		return saleOrderTicketDao.getSoldQuantity(c);
	}
	
	/**
	 * 根据验票码查询
	 * @param checkCode
	 * @return
	 * @throws Exception
	 */
	public List<SaleOrderTicket> findByCheckCode(String checkCode) throws Exception {
		return saleOrderTicketDao.findByCheckCode(checkCode);
	}

	/**
	 * 根据checkcode获取座位号和座位ID
	 * @param checkcode
	 * @return key只有seat_no和seat_id
	 */
	public Map<String, Object> findSeatNOAndSeatId(String checkCode) {
		return saleOrderTicketDao.findSeatNOAndSeatId(checkCode);
	}

	/**
	 * 根据rtid获取座位号
	 * @param checkcode
	 * @return key只有seat_no和seat_id
	 */
	public List<Integer> findSeatNOByRTID(String rtid) {
		return saleOrderTicketDao.findSeatNOByRTID(rtid);
	}
}

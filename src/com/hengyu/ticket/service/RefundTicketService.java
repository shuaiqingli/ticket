package com.hengyu.ticket.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hengyu.ticket.dao.RefundTicketDao;
import com.hengyu.ticket.dao.RefundTicketDetailDao;
import com.hengyu.ticket.dao.SaleOrderDao;
import com.hengyu.ticket.dao.SaleOrderTicketDao;
import com.hengyu.ticket.dao.TicketLineStoreDao;
import com.hengyu.ticket.dao.TicketStoreDao;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.RefundRule;
import com.hengyu.ticket.entity.RefundTicket;
import com.hengyu.ticket.entity.RefundTicketDetail;
import com.hengyu.ticket.entity.SaleOrder;
import com.hengyu.ticket.entity.SaleOrderTicket;
import com.hengyu.ticket.entity.TicketStore;

/**
 * 
 * @author 李冠锋 2015-12-29
 *
 */
@Service
public class RefundTicketService {

	@Autowired
	private RefundTicketDao refundTicketDao;
	@Autowired
	private RefundTicketDetailDao rtddao;
	@Autowired
	private SaleOrderTicketDao sotdao;
	@Autowired
	private SaleOrderDao sodao;
	@Autowired
	private TicketStoreDao tsdao;
	@Autowired
	private TicketLineStoreDao tlsdao;

	/**
	 * 保存一个对象
	 * 
	 * @param refundTicket
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(RefundTicket refundTicket) throws Exception {
		return refundTicketDao.save(refundTicket);
	}

	// 删除退款订单
	public int delete(String id, RefundTicket r) throws Exception {
		Assert.isTrue(refundTicketDao.delete(id) == 1, "删除失败！");
		List<RefundTicketDetail> list = rtddao.findByRefundNo(id);
		int discountquantity = 0;
		int quantity = 0;
		for (RefundTicketDetail d : list) {
			SaleOrderTicket ck = sotdao.find(d.getCheckcode());
			ck.setStatus(0);
			Assert.isTrue(sotdao.updateRefundStatus(ck.getCheckcode(), 0, 1, 1, "已付款") == 1, "还原车票详情失败！");
			if (ck.getIsdiscount() != null && ck.getIsdiscount() == 1) {
				discountquantity++;
			}
			quantity++;
		}
		SaleOrder order = sodao.find(r.getSoid());
		TicketStore ts = tsdao.findBySaleOrder(order);

		Assert.isTrue(tsdao.updateQuantity(ts.getId(), quantity, discountquantity) == 1, "减少总票库失败！");

		order.setQuantity(quantity);
		order.setDiscountquantity(discountquantity);
		Assert.isTrue(tlsdao.updateQuantityByOrder(order) == 1, "减少站点票库失败！");

		sodao.updateRefundStatus(id, 0, 0, "未取票", 1, "已付款");
		rtddao.deleteByRefundno(id);
		return 1;
	}

	/**
	 * 更新一个对象
	 * 
	 * @param refundTicket
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(RefundTicket refundTicket) throws Exception {
		return refundTicketDao.update(refundTicket);
	}

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回RefundTicket对象
	 * @throws Exception
	 */
	public RefundTicket find(String id) throws Exception {
		return refundTicketDao.find(id);
	}

	// 退款订单列表
	public List<RefundTicket> findlist(Page page) throws Exception {
		long count = refundTicketDao.count(page);
		page.setTotalCount(count);
		List<RefundTicket> list = refundTicketDao.findlist(page);
		page.setData(list);
		return list;
	}
	
	public void updateRefundTicket(RefundTicket rf) throws Exception{
		Assert.isTrue(refundTicketDao.updateRefund(rf)==1);
	}

	public void setRefundTicketDao(RefundTicketDao refundTicketDao) {
		this.refundTicketDao = refundTicketDao;
	}

	public long waitAffirmRefundTicketQuantity(Map a) throws Exception {
		return refundTicketDao.waitAffirmRefundTicketQuantity(a);
	}

	// 获取待确认退票列表
	public List waitAffirmRefundTicketList(Map a) throws Exception {
		return refundTicketDao.waitAffirmRefundTicketList(a);
	}

	// 确认退票
	public int refundTicketAffirm(Map a) throws Exception {
		return refundTicketDao.refundTicketAffirm(a);
	}
}

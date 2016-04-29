package com.hengyu.ticket.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hengyu.ticket.dao.LineManageDao;
import com.hengyu.ticket.dao.PromotionDao;
import com.hengyu.ticket.dao.SeatDao;
import com.hengyu.ticket.dao.ShiftStartDao;
import com.hengyu.ticket.dao.StationLineDao;
import com.hengyu.ticket.dao.TicketLineDao;
import com.hengyu.ticket.dao.TicketLineStoreDao;
import com.hengyu.ticket.dao.TicketStoreDao;
import com.hengyu.ticket.dao.TripPriceListDao;
import com.hengyu.ticket.dao.TripPriceSubDao;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.Promotion;
import com.hengyu.ticket.entity.PromotionTime;
import com.hengyu.ticket.entity.SaleOrder;
import com.hengyu.ticket.entity.Seat;
import com.hengyu.ticket.entity.ShiftStart;
import com.hengyu.ticket.entity.StationLine;
import com.hengyu.ticket.entity.TicketLine;
import com.hengyu.ticket.entity.TicketStore;
import com.hengyu.ticket.entity.TripPriceList;
import com.hengyu.ticket.entity.TripPriceRule;
import com.hengyu.ticket.entity.TripPriceSub;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.Log;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
@Service
public class TicketStoreService {

	@Autowired
	private TicketStoreDao ticketStoreDao;
	@Autowired
	private TripPriceListDao tpldao;
	@Autowired
	private TripPriceSubDao tpsdao;
	@Autowired
	private PromotionDao ptdao;
	@Autowired
	private TicketLineDao tldao;
	@Autowired
	private LineManageDao lmdao;
	@Autowired
	private StationLineDao sldao;
	@Autowired
	private TicketLineStoreDao tlsdao;
	@Autowired
	private ShiftStartDao ssdao;
	@Autowired
	private SeatDao seatdao;

	/**
	 * 保存一个对象
	 * 
	 * @param ticketStore
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(TicketStore ticketStore) throws Exception {
		return ticketStoreDao.save(ticketStore);
	}
	

	/**
	 * 更新一个对象
	 * 
	 * @param ticketStore
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(TicketStore ticketStore) throws Exception {
		return ticketStoreDao.update(ticketStore);
	}

    //取消或发布车票
	public void updateReleaseStatusByDate(Integer lmid, String begindate, String enddate,String shiftcode,Integer status,Integer oldstatus) throws Exception {
	    Assert.isTrue(ticketStoreDao.updateReleaseStatusByDate(shiftcode,begindate,enddate,lmid,status,oldstatus)>=1,"更新票库发布状态失败!");
	}



	/**
	 * 根据主键查询一个对象
	 * 
	 * @param iD
	 *            主键
	 * @return 返回TicketStore对象
	 * @throws Exception
	 */
	public TicketStore find(Integer iD) throws Exception {
		return ticketStoreDao.find(iD);
	}

	/**
	 * 查询余票列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List findQuantityBalance(Page page) throws Exception {
		List list = ticketStoreDao.findQuantityBalance(page);
		page.setTotalCount(ticketStoreDao.findQuantityBalanceCount(page));
		return list;
	}


	public void setTicketStoreDao(TicketStoreDao ticketStoreDao) {
		this.ticketStoreDao = ticketStoreDao;
	}

	public List getTicketStoreByLST(Map a) throws Exception {
		return ticketStoreDao.getTicketStoreByLST(a);
	}

	/**
	 * 按线路号，开始结束日期（按月查询）
	 * 
	 * @param lmid
	 * @param begindate
	 * @param enddate
	 * @return
	 */
	public List<Map<String, String>> findApproveReleaseDates(Integer lmid, String begindate, String enddate) {
		return ticketStoreDao.findApproveReleaseDates(lmid, begindate, enddate);
	}

	/**
	 * 按线路编号统计已审核天数，已发布天数，总天数
	 * 
	 * @param lmid
	 * @return
	 */
	public Map<String, Integer> findAllApproveReleaseDates(Integer lmid) {
		return ticketStoreDao.findAllApproveReleaseDates(lmid);
	}

	// 按班次取消车票,
	public int cancelTicketStore(Map a) {
		return ticketStoreDao.cancelTicketStore(a);
	}

	// 释放锁定票
	public int releaseQuantity(Map e) {
		return ticketStoreDao.releaseQuantity(e);
	}
	
	// 站务退票
	public int releaseZWReleaseQuantity(Map e,SaleOrder order) throws Exception {
		Integer quantity = (Integer) e.get("quantity");
		order.setQuantity(quantity);
		order.setDiscountquantity(0);
		int count = tlsdao.addQuantityByOrder(order);
		Assert.isTrue(count == 1, "系统异常，增加站点余票失败！====== >> orderid : "+order.getId());
		return ticketStoreDao.releaseZWReleaseQuantity(e);
	}
	
	// 站务退票锁定
	public int releaseZWLockQuantity(Map e) {
		return ticketStoreDao.releaseZWLockQuantity(e);
	}
	
	/**
	 * 根据线路ID和班次ID
	 * @param map
	 */
	public TicketStore findByShiftIdAndLMID(Map<String, Object> map) {
		return ticketStoreDao.findByShiftIdAndLMID(map);
	}
}

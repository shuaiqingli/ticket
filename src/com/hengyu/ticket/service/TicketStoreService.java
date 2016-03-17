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
	
	//重置审核的车票管理
	public int updateResetApprove(String ticketdate,Integer lmid,Integer isapprove ) throws Exception{
		TicketLine tl = new TicketLine();
		tl.setTicketdate(ticketdate);
		tl.setLmid(lmid);
		seatdao.delNotApproveSeat(tl );
		return ticketStoreDao.updateResetApprove(ticketdate, lmid, isapprove);
	}

	/**
	 * 取消班次
	 * 
	 * @param id
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public int updateCancelShiftStart(Integer id, Integer status) throws Exception {
		int result = -1;
		TicketStore ts = ticketStoreDao.find(id);
		Assert.notNull(ts, "无效编号！");
		List<ShiftStart> shifts = ssdao.findShiftStartBylmidDateShiftCode(ts.getLmid(), ts.getTicketDate(),
				ts.getShiftCode());
		Assert.notNull(ts, "服务器异常，没有找到班次！");
		for (ShiftStart shift : shifts) {
			Assert.isTrue(!(shift.getIsstart() == 1), "班次已经发车不能取消或恢复班次！");
			if (status == 1) {
				shift.setIscancel(0);
				shift.setIscancelname("正常");
				shift.setIsstart(0);
				shift.setIsstartname("未发车");
				ts.setIsrelease(1);
			} else if (status == 2) {
				shift.setIscancel(1);
				shift.setIscancelname("已取消");
				shift.setIsstart(2);
				shift.setIsstartname("已取消");
			}
			result += ssdao.update(shift);
		}
		ts.setIsapprove(status);
		result += ticketStoreDao.update(ts);
		return result;
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

	/**
	 * 批量更新（审核） ticketStore
	 * 
	 * @param lmid
	 *            线路编号
	 * @param begindate
	 *            开始时间
	 * @param enddate
	 *            结束时间
	 * @return
	 * @throws Exception
	 */
	public int updateTicketStore(Integer lmid, String begindate, String enddate, Integer isrelease) throws Exception {
		Set<String> dates = DateHanlder.getDates(begindate, enddate);
		Log.info(this.getClass(), "============= >> 批量审核车票 ");
		Log.info(this.getClass(), "============= >> 审核日期： ", dates);
		for (String date : dates) {
			TicketStore ts = new TicketStore();
			ts.setLmid(lmid);
			ts.setTicketDate(date);
			if (isrelease == null || isrelease != 1) {
				ts.setIsrelease(0);
				ts.setIsapprove(0);
			}
			// 按天查询车票
			List<TicketStore> list = findticketmanage(ts);
			if (list == null || list.size() == 0) {
				Log.info(this.getClass(), "============= >> ", date, "：没有查询到相关记录... ");
				continue;
			}
			Log.info(this.getClass(), "============= >> 查询匹配车票记录： ", list.size());
			int size = 0;
			int tlsSize = 0;
			for (TicketStore ticketStore : list) {
				if (ticketStore.getIsapprove() == 2) {
					Log.info(this.getClass(), "============= >> 已取消、已发布的车票不需要再次更新： ", date);
					continue;
				}
				if (1 == ticketStore.getIsrelease()) {
					Log.info(this.getClass(), "============= >> 已审核、已发布的车票不需要再次更新： ", date);
					continue;
				} else if (1 == ticketStore.getIsapprove() && 1 == isrelease && 0 == ticketStore.getIsrelease()) {
					ticketStore.setIsrelease(1);
					ticketStoreDao.update(ticketStore);
					Log.info(this.getClass(), "============= >> " + ticketStore.getTicketDate(), " ",
							ticketStore.getShiftCode(), " 已审核的车票，不需要更新价格，立即生效。 isrelease=", isrelease);
					continue;
				}
				ticketStore.setIsapprove(1);
				if (isrelease != null && isrelease == 1) {
					ticketStore.setIsrelease(1);
				}
				// 更新线路总票库
				Assert.isTrue(ticketStoreDao.updateApproveQuantity(ticketStore) == 1);
				// 保存座位
				saveSeat(ticketStore);

				List<TicketLine> tls = ticketStore.getTicketlines();
				if (tls == null) {
					continue;
				}
				String stationid = "";
				for (TicketLine ticketLine : tls) {
					// 站点票库
					if (!stationid.equals(ticketLine.getStstartid())) {
						Integer tlsid = ticketLine.getTlsid();
						Integer salequantity = ticketLine.getSalequantity();
						Integer couponsalequantity = ticketLine.getCouponsalequantity();
						if (couponsalequantity == null) {
							couponsalequantity = 0;
						}
						if (salequantity == null) {
							salequantity = 0;
						}
						tlsSize += tlsdao.updateApproveQuantity(ticketLine, tlsid, couponsalequantity, salequantity);
						stationid = ticketLine.getStstartid();
					}
					// 车票线路
					size += tldao.update(ticketLine);
				}
			}
			Log.info(this.getClass(), "============= 更新车票记录： ", list.size());
			Log.info(this.getClass(), "============= 更新车票线路记录： ", size);
			Log.info(this.getClass(), "============= 更新站点车票线路记录： ", tlsSize);
		}
		// Assert.isTrue(false,"111111111");
		return 1;
	}

	// 批量保存座位
	public void saveSeat(TicketStore ts) throws Exception {
		TripPriceRule tpr = ts.getTpr();
		if (tpr == null) {
			return;
		}
		Integer start = tpr.getIsstart();
		if (start != null && start == 0) {
			Log.info(this.getClass(), "不排座位....");
			return;
		}
		Integer lmid = ts.getLmid();
		String ridedate = ts.getTicketDate();
		String shiftcode = ts.getShiftCode();
		Long count = seatdao.checkExistsByLmidDateShiftCode(lmid, ridedate, shiftcode);
		if (count != null && count >= 1) {
			Log.info(this.getClass(), "已经排座位，不需要再次排！");
			return;
		}
		Integer quantity = tpr.getTicketquantity();
//		Integer lockStart = ((start+quantity)-ts.getLockquantity());
		Integer startseat = tpr.getStartseat();
		List<Seat> list = new ArrayList<Seat>();
		
		int lockseatend = (tpr.getStartseat()+ts.getBalanceQuantity());
		boolean isalllock = ts.getBalanceQuantity()==0?true:false;
		
		for (int i = start; i < (start + quantity); i++) {
			Seat seat = seatdao.findUniqueNotSaleSeat(lmid, ridedate, shiftcode);
			if (seat != null) {
				Log.info(this.getClass(), "位置已经存在，不需要重新拍座位！", "lmid:", lmid, " date:", ridedate, " shiftcode:",
						shiftcode);
				continue;
			}
			Seat s = new Seat();
			s.setIssale(2);
			//释放卖票位置
			if(i>=startseat&&i<lockseatend&&isalllock==false){
				s.setIssale(0);
			}
			s.setLmid(lmid);
			s.setShiftcode(shiftcode);
			s.setRidedate(ridedate);
			s.setSeatno(i);
			list.add(s);
		}
		int r = seatdao.batchSave(list);
		Assert.isTrue(r == quantity, "批量保存座位异常！");
	}

	/**
	 * 按日期查询票库，车票线路
	 * 
	 * @param ts
	 * @return
	 * @throws Exception
	 */
	public List<TicketStore> findticketmanage(TicketStore ts) throws Exception {
		List<TicketStore> list = ticketStoreDao.ticketmanage(ts);
		boolean isrelease = false;
		if (list != null && list.size() != 0) {
			TicketStore s = list.get(0);
			if (s.getIsapprove() == 1 || s.getIsrelease() == 1 || s.getIsapprove() == 2) {
				Log.info(this.getClass(), "==== 已审核的车票，不需要匹配价格，优惠规则");
				// return list;
			}
			if(s.getIsrelease()==1){
				isrelease = true;
			}
		}
		// 已审核，或者已发布的车票不匹配价格
		String date = ts.getTicketDate();
		List<TripPriceList> tpls = tpldao.findByTicketStore(ts);
		if (tpls == null || tpls.size() == 0) {
			Log.info(this.getClass(), "==== 没有匹配到任何行程价格规则，终止查询");
			if(isrelease){
				return list;
			}
			return null;
		}
		List<Promotion> pts = ptdao.findByTicketStore(ts);
		Log.info(this.getClass(), "==== 匹配行程价格规则记录：" + tpls.size());
		Log.info(this.getClass(), "==== 匹配优惠规则记录：" + pts.size());
		if (list == null || list.size() == 0) {
			return list;
		}
		Promotion pt = null;
		// 匹配优惠规则
		if (pts != null && pts.size() != 0) {
			for (int i = 0; i < pts.size(); i++) {
				boolean ismatch = DateHanlder.checkExistsWeek(date, pts.get(i).getWeekdays());
				if (ismatch) {
					pt = pts.get(i);
					break;
				}
			}
		}
		TripPriceList tpl = tpls.get(0);
		TripPriceRule matchrule = null;
		// 规则列表
		List<TripPriceRule> rules = tpl.getRules();
		for (TripPriceRule rule : rules) {
			boolean ismatch = DateHanlder.checkExistsWeek(date, rule.getWeekday());
			if (ismatch) {
				matchrule = rule;
				// 价格规则
				matchrule.setTps(tpsdao.findByTripPriceRule(matchrule));
				// 出票规则
				matchrule.setStationlines(sldao.findByTripPriceRule(matchrule));
				break;
			}
		}

		Assert.notNull(matchrule, "没有找到对应的行程价格日期！");

		// if(pt!=null){
		// List<PromotionTime> ptts = pt.getTimes();
		// if(ptts!=null){
		// Log.info(this.getClass(),"========== 优惠规则列表:");
		// for (int i = 0; i < ptts.size(); i++) {
		// PromotionTime ptime = ptts.get(i);
		// Log.info(this.getClass(),"======== >> "+ptime.getBegintime()+" " +
		// ptime.getEndtime());
		// }
		// }
		// }

		// 匹配价格
		for (int i = 0; i < list.size(); i++) {
			TicketStore s = list.get(i);
			s.setTpr(matchrule);
			if (s.getIsapprove() == 1 || s.getIsrelease() == 1 || s.getIsapprove() == 2) {
				continue;
			}
			// 已审核，或者已发布的车票不匹配价格
			// if (s.getIsapprove() == 1 || s.getIsrelease() == 1) {
			// break;
			// }

			s.setAllquantity(matchrule.getTicketquantity() == null ? 0 : matchrule.getTicketquantity());
			s.setBalanceQuantity(matchrule.getTicketquantity() == null ? 0 : matchrule.getTicketquantity());

			s.setCouponquantity(matchrule.getCouponticketquantity() == null ? 0 : matchrule.getCouponticketquantity());
			s.setBalanceCouponquantity(
					matchrule.getCouponticketquantity() == null ? 0 : matchrule.getCouponticketquantity());
			s.setLockquantity(matchrule.getLockquantity()==null?0:matchrule.getLockquantity());
			//计算余票
			s.setBalanceQuantity(s.getAllquantity()-s.getLockquantity());
			// 规则

			List<TicketLine> ticketlines = s.getTicketlines();
			if (ticketlines != null) {
				int temp = 0;
				for (TicketLine tl : ticketlines) {

					List<TripPriceSub> tps = matchrule.getTps();
					List<StationLine> stationlines = matchrule.getStationlines();

					// 价格规则
					if (tps != null) {
						for (TripPriceSub sub : tps) {
							if (sub.getStarriveid().equals(tl.getStarriveid())
									&& sub.getStstartid().equals(tl.getStstartid())) {
								tl.setTicketprice(sub.getPrice());
								tl.setMileage(sub.getMileage());
								tl.setFavprice(sub.getPrice());
								if (pt != null) {
									List<PromotionTime> times = pt.getTimes();
									if (times != null) {
										// 匹配优惠价格
										for (PromotionTime time : times) {
											if (DateHanlder.checkeBetweenTime(tl.getOriginstarttime(),
													time.getBegintime(), time.getEndtime())) {
												BigDecimal reducesum = time.getReducesum() == null ? BigDecimal.ZERO
														: new BigDecimal(time.getReducesum());
												tl.setFavprice(sub.getPrice().add(reducesum));
												tl.setCouponticketpercent(time.getCouponpercent());
												tl.setPromotionprice(reducesum);
												break;
											}
										}
									}
								}
								break;
							}
						}
					}
					// 出票规则
					if (stationlines != null) {
						for (StationLine stationLine : stationlines) {
							if (stationLine.getStationid().equals(tl.getStstartid())) {
								if (tl.getCouponticketpercent() == null) {
									tl.setCouponsalequantity(stationLine.getCouponsalequantity());
								} else {
									tl.setCouponsalequantity(
											stationLine.getCouponsalequantity() * tl.getCouponticketpercent() / 100);
								}
								tl.setSalequantity(stationLine.getSalequantity());
								// 城市优惠价格百分比,第二次计算
								BigDecimal money = (tl.getPromotionprice() == null ? new BigDecimal(0)
										: tl.getPromotionprice())
												.multiply(new BigDecimal(stationLine.getCouponpercent())
														.divide(new BigDecimal(100)));
								tl.setFavprice(tl.getTicketprice().add(money));
							}
						}
					}

					if (tl.getCouponsalequantity() > temp) {
						temp = tl.getCouponsalequantity();
					}
				}

				s.setCouponquantity(temp);
				s.setBalanceCouponquantity(temp);
			}
		}
		return list;
	}

	/**
	 * 自动发布车票
	 * 
	 * @throws Exception
	 */
	public void updateTicketStoreReleaseByLineManage() throws Exception {
		SimpleDateFormat dateTimeFromat = DateHanlder.getDateTimeFromat();
		Log.error("========= 开始自动发布车票：" + dateTimeFromat.format(new Date()));
		List<LineManage> lines = lmdao.findReleaseLines();
		if (lines == null || lines.size() == 0) {
			return;
		}
		Log.error("========= 查询线路记录数：" + lines.size());
		SimpleDateFormat dateFromat = DateHanlder.getDateFromat();
		for (LineManage lm : lines) {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			if (lm.getReleaseday() == null || lm.getReleaseday() == 0) {
//				lm.setReleaseday(1);
				Log.error("========= 自动发布天数为0不发布 ：lineid：" + lm.getLineid());
				continue;
			}
			c.add(Calendar.DAY_OF_MONTH, lm.getReleaseday() - 1);
			lm.setBegindate(dateFromat.format(new Date(System.currentTimeMillis())));
			lm.setEnddate(dateFromat.format(c.getTime()));
			int update = ticketStoreDao.updateTicketStoreReleaseByLineManage(lm);
			Log.error("========= 自动发布天数：" + lm.getReleaseday());
			Log.error("========= 自动发布的车票日期：" + lm.getBegindate() + " 至 " + lm.getEnddate());
			Log.error("========= " + lm.getLineid() + " ( " + lm.getLinename() + " ) 自动发布车票记录数：" + update);
		}
		Log.error("========= 自动发布车票完成：" + DateHanlder.getDateTimeFromat().format(new Date()));
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
}

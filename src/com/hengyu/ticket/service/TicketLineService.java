package com.hengyu.ticket.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hengyu.ticket.entity.MakeConf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.config.TicketConfig;
import com.hengyu.ticket.dao.SeatDao;
import com.hengyu.ticket.dao.ShiftStartDao;
import com.hengyu.ticket.dao.TicketLineDao;
import com.hengyu.ticket.dao.TicketLineStoreDao;
import com.hengyu.ticket.dao.TicketStoreDao;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.TicketLine;
import com.hengyu.ticket.util.DateHanlder;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
@Service
public class TicketLineService {

	@Autowired
	private TicketLineDao ticketLineDao;
	@Autowired
	private TicketConfig tc;
	@Autowired
	private ShiftStartDao ssDao;
	@Autowired
	private TicketStoreDao tsDao;
	@Autowired
    private TicketLineStoreDao tlsdao;
	@Autowired
	private SeatDao seatdao;

	/**
	 * 保存一个对象
	 * 
	 * @param ticketLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(TicketLine ticketLine) throws Exception {
		return ticketLineDao.save(ticketLine);
	}

	/**
	 * 更新一个对象
	 * 
	 * @param ticketLine
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(TicketLine ticketLine) throws Exception {
		return ticketLineDao.update(ticketLine);
	}

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param iD
	 *            主键
	 * @return 返回TicketLine对象
	 * @throws Exception
	 */
	public TicketLine find(Integer iD) throws Exception {
		return ticketLineDao.find(iD);
	}

	/**
	 * 查询车票列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List findTicketList(Page page, boolean isCheck) throws Exception {
		TicketLine tl = (TicketLine) page.getParam();
		if (isCheck && DateHanlder.getCurrentDate().equals(tl.getTicketdate())) {
			SimpleDateFormat df = DateHanlder.getDateFromat();
			tl.setIstoday(1);
			Date date = new Date(System.currentTimeMillis() + 1000 * 60 * tc.getTicketTime());
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			
			Calendar c2 = Calendar.getInstance();
			c2.setTime(df.parse(DateHanlder.getCurrentDate()));
			c2.add(Calendar.HOUR_OF_DAY, 23);
			c2.add(Calendar.MINUTE, 59);
			
			if(c.getTimeInMillis()>c2.getTimeInMillis()){
				tl.setOrdertime(DateHanlder.getTimeFromat()
						.format(c2.getTime()));
			}else{
				tl.setOrdertime(DateHanlder.getTimeFromat()
						.format(c.getTime()));
			}
			
		}
		if (tl.getId() == null) {
			long count = ticketLineDao.findTicketListCount(page);
			page.setTotalCount(count);
			Integer istoday = tl.getIstoday();
			tl.setIstoday(0);
            Integer res_all = ticketLineDao.getTicketLineNotDataTypeTip(tl);
			page.setNotDataType(res_all == 0 ? 0 : 1);
			if(res_all > 0 && istoday == 1){
				tl.setIstoday(1);
				Integer res_available = ticketLineDao.getTicketLineNotDataTypeTip(tl);
				page.setNotDataType(res_available == 0 ? 1 : 2);
			}
			tl.setIstoday(istoday);
		}
		return ticketLineDao.findTicketList(page);
	}

	// 查询改线路审核最大的日期
	public String getCheckApproveTicketDate(TicketLine tl) throws Exception {
		return ticketLineDao.getCheckApproveTicketDate(tl);
	}

	// 更新车票线路价格
	public int updateprice(List ids, List prices) throws Exception {
		int result = 0;
		for (int i = 0; i < ids.size(); i++) {
			TicketLine tl = new TicketLine();
			tl.setId(Integer.parseInt(ids.get(i).toString()));
			tl.setTicketprice(new BigDecimal(prices.get(i).toString()));
			result += ticketLineDao.updateprice(tl);
		}
		return result;
	}

	/**
	 * 修改排班日期后删除车票，以及相关的 ticketStore ShirtStart
	 * 
	 * @return
	 * @throws Exception
	 */
	public int delNotApproveTicketLine(Integer lmid, String ticketdate) throws Exception {
		TicketLine tl = new TicketLine();
		tl.setLmid(lmid);
		tl.setTicketdate(ticketdate);
		int r = 0;
		r += ssDao.delNotApproveShiftStart(tl);
		r += tsDao.delNotApproveTicketStore(tl);
		r += ticketLineDao.delNotApproveTicketLine(tl);
		r += tlsdao.delNotApproveTicketLineStore(tl);
		r+= seatdao.delNotApproveSeat(tl);
		return r;
	}

	public void setTicketLineDao(TicketLineDao ticketLineDao) {
		this.ticketLineDao = ticketLineDao;
	}

	/**
	 * 根据班次查询各站点的票价(不要再用)
	 * @param a
	 * @return
	 * @throws Exception
	 */
	@Deprecated  
	public List getTicketLineByShiftCode(Map a) throws Exception{
		return ticketLineDao.getTicketLineByShiftCode(a);
	}

	/**
     * 根据出发站和到达站以及出发日期查询
     * @param map
     */
	public List<TicketLine> findShiftByCityStartIDAndCityArriveIDAndRide(Map<String, Object> map) {
		return ticketLineDao.findShiftByCityStartIDAndCityArriveIDAndRide(map);
	}

	public List<Map> getTicketLineByShiftId(Long shiftId) {
		return ticketLineDao.getTicketLineByShiftId(shiftId);
	}

	public List<Map> getStationListForShift(Long shiftid){
		return ticketLineDao.getStationListForShift(shiftid);
	}

}

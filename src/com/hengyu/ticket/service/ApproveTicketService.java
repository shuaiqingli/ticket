package com.hengyu.ticket.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.common.Const;
import com.hengyu.ticket.common.LineStation;
import com.hengyu.ticket.config.TicketConfig;
import com.hengyu.ticket.dao.LineManageDao;
import com.hengyu.ticket.dao.LineManageStationDao;
import com.hengyu.ticket.dao.ShiftStartDao;
import com.hengyu.ticket.dao.TicketLineDao;
import com.hengyu.ticket.dao.TicketLineStoreDao;
import com.hengyu.ticket.dao.TicketStoreDao;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.LineManageStation;
import com.hengyu.ticket.entity.LineScheduDetail;
import com.hengyu.ticket.entity.LineSchedue;
import com.hengyu.ticket.entity.LineSchedueRule;
import com.hengyu.ticket.entity.ShiftStart;
import com.hengyu.ticket.entity.TicketLine;
import com.hengyu.ticket.entity.TicketLineStore;
import com.hengyu.ticket.entity.TicketStore;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.Log;

/**
 * 生成车票，车票线路，发车.
 * @author LGF
 *
 */
@Service
public class ApproveTicketService {
	
	private Logger log = Logger.getLogger(ApproveTicketService.class);
	
	@Autowired
	private LineManageDao lmdao;
	@Autowired
	private LineManageStationDao lmsdao;
	@Autowired
	private TicketStoreDao ticketStoreDao;
	@Autowired
	private TicketLineDao ticketLineDao;
	@Autowired
	private ShiftStartDao shiftStartDao;
	@Autowired
	private LineSchedueService scheduleService;
	@Autowired
	private TicketLineStoreDao tlsdao;
	@Autowired
	private TicketConfig cnf;
	
	
	//自动审核审核排班
	public void saveapproveTicketByDay() throws Exception{
		try {
			long time = System.currentTimeMillis();
			List<LineManage> lines = lmdao.findReleaseLines();
			SimpleDateFormat df = DateHanlder.getDateFromat();
			
			log.error("^^^^^^^^^^^^^^^^^^^^^^ 开始批量审核排班 ^^^^^^^^^^^^^^^^^ ");
			if (lines != null && lines.isEmpty() == false) {
				for (LineManage lm : lines) {
					if(lm.getApprovescheduleday()==null||lm.getApprovescheduleday()==0){
						continue;
					}
					log.error("^^^^^^^^^^^^^^^^^^^^^^ 线路号："+lm.getLineid()+" 线路id："+lm.getId()+" ^^^^^^^^^^^^^^^^^ ");
					Calendar c = Calendar.getInstance();
					c.add(Calendar.DAY_OF_MONTH, lm.getApprovescheduleday()-1);
					String begindate = df.format(new Date());
					String enddate = df.format(c.getTime());
					saveapproveTickets(lm.getId(), begindate,enddate);
					log.error("^^^^^^^^^^^^^^^^^^^^^^ 开始日期："+begindate+" 结束日期："+enddate+"^^^^^^^^^^^^^^^^^ ");
				}
				time = System.currentTimeMillis() - time;
				log.error("^^^^^^^^^^^^^^^^^^^^^^ 结束批量审核排班 ^^^^^^^^^^^^^^^^^ 耗时" + time/1000 + " （秒）");
			}else{
				log.error("^^^^^^^^^^^^^^^^^^^^^^ 没有找到线路 ^^^^^^^^^^^^^^^^^ ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("^^^^^^^^^^^^^^^^^^^^^^ 批量审核排班异常 ^^^^^^^^^^^^^^^^^ "+e.getMessage());
		}
	}
	
	/**
	 * 批量审核（生成车票）
	 * @param lmid 线路编号
	 * @param begindate 开始日期
	 * @param enddate 结束日期
	 * @return
	 * @throws Exception
	 */
	public int saveapproveTickets(Integer lmid,String begindate,String enddate) throws Exception{
		Set<String> dates = DateHanlder.getDates(begindate, enddate);
		Log.info(this.getClass(),"============ ^^^^^批量生成车票线路，发车，票库！",dates);
		for (String date : dates) {
			LineSchedue ls = new LineSchedue();
			ls.setLmid(lmid);
//			ls.setDate(date);
			LineSchedueRule	lsr = null;// scheduleService.findRuleByLineSchedue(ls);
			if(lsr==null){
				Log.info(this.getClass(),"============ 没有找到任何匹配的排班规则！",date);
				continue;
			}else{
				if(lsr.getIsapprove()!=null&&lsr.getIsapprove()==1){
//					Log.info(this.getClass(),"===========^^^^^",ls.getDate(),"，所有班次已被审核！");
					continue;
				}
				saveapproveTicket(ls, lsr);
			}
		}
		return 1;
	}
	
	/**
	 * 审核班次
	 * @param ls 排班
	 * @param rule 排班规则
	 * @return
	 * 1正常 -1 失败  
	 * @throws Exception
	 */
	public int saveapproveTicket(LineSchedue ls,LineSchedueRule rule) throws Exception{
		Log.info(this.getClass(),"========== 查询已经审核过的日期 ");
		int result  = -1;
		try {
			//车票线路
			List<TicketLine> ticketLines = new ArrayList<TicketLine>();
			//班次发车
			List<ShiftStart> shiftStarts = new ArrayList<ShiftStart>();
			//票库
			List<TicketStore> ticketStores = new ArrayList<TicketStore>();
			//线路票库
			List<TicketLineStore> ticketlinestores = new ArrayList<TicketLineStore>();
			
			LineManage lm = new LineManage();
			lm.setId(ls.getLmid());
			lm = lmdao.find(lm);
			if(lm==null){
				Log.info(this.getClass(),"===========传入线路id错误，没有找到该线路 " , ls.getLmid());
			}else{
				List<LineManageStation> stations = lmsdao.findByLMID(lm.getId());
				if(stations!=null){
					Log.info(this.getClass(),"===========查收途经站记录：",stations.size());
				}
				lm.setLineManageStataions(stations);
//				result = createTicketData(lm, ls.getDate(), ticketStores, shiftStarts, ticketLines, rule, ticketlinestores);
			}
			
			
			Log.info(this.getClass(),"===========生成发车记录 :",shiftStarts.size());
			Log.info(this.getClass(),"===========生成车票线路记录 :",ticketLines.size());
			Log.info(this.getClass(),"===========生成票库记录 :",ticketStores.size());
			Log.info(this.getClass(),"===========生成站点票库记录 :",ticketlinestores.size());
			
			
			if(ticketLines.size()!=0){
				ticketLineDao.batchSave(ticketLines);
			}
			if(shiftStarts.size()!=0){
				shiftStartDao.batchSave(shiftStarts);
			}
			if(ticketStores.size()!=0){
				ticketStoreDao.batchSave(ticketStores);
			}
			if(ticketlinestores.size()!=0){
				tlsdao.batchSave(ticketlinestores);
			}
		} catch (Exception e) {
			Log.error(e);
			throw e;
		}
		return result;
	}
	
	/**
	 * 生成车票数据
	 * @param tlss 检查日期是否生成过车票
	 * @param lm 线路
	 * @param tickeDate 车票日期
	 * @param ticketStores 票库
	 * @param shiftStarts 发车
	 * @param ticketLines 车票线路
	 * @param rule 匹配的排班规则
	 * @return
	 * @throws Exception
	 */
	public int createTicketData(LineManage lm,String tickeDate,
		List<TicketStore> ticketStores,List<ShiftStart> shiftStarts,List<TicketLine> ticketLines,LineSchedueRule rule,List<TicketLineStore> tlss) throws Exception{
		
		Log.info(this.getClass(),"日期：",tickeDate,",线路编号：",lm.getLineid(),"-",lm.getLinename(),",开始生成车票....");
		
		SimpleDateFormat tf = DateHanlder.getTimeFromat();
		//班次详情
		List<LineScheduDetail> details = rule.getLinescheduledetail();
		if(details!=null){
			//发车表生成规则  A,B,C -- D,E,F
			// A-F,D-F,C-F
			List<LineStation> shiftLineStations = getShiftLineStations(lm);
			//生成线路站点
			//A,B,C -- D,E,F
			//A-D,A-E,A-F   B-D,B-E,B-F ....
			List<LineStation> tickectLineStations = getTickLineStations(lm);
			
			//遍历班次
			for (int i = 0; i < details.size(); i++) {
				LineScheduDetail lsd = details.get(i);
				Long startTime = tf.parse(lsd.getStarttime()).getTime();
				Date startDate = new Date(startTime);
				
				//车票线路生成
				for (int j = 0; j < tickectLineStations.size(); j++) {
					LineStation lineStation = tickectLineStations.get(j);
					TicketLine ticketLine = new TicketLine();
					createTicketLine(ticketLine, lm, lineStation, lsd.getShiftcode(), startDate, startTime, tickeDate);
					if(ticketLines!=null){
						ticketLines.add(ticketLine);
					}
				}
				
				//发车生成
				for (int j = 0; j < shiftLineStations.size(); j++) {
					LineStation lineStation = shiftLineStations.get(j);
					ShiftStart shiftStart = new ShiftStart();
					createShiftStart(shiftStart,lm, lineStation, lsd.getShiftcode(), startDate, startTime, tickeDate);
					if(shiftStarts!=null){
						shiftStarts.add(shiftStart);
					}
					
					TicketLineStore tls = new TicketLineStore();
					tls.setLmid(lm.getId());
					
					tls.setAllquantity(0);
					tls.setBalancequantity(0);
					
					tls.setCouponquantity(0);
					tls.setBalancecouponquantity(0);
					
					tls.setMakedate(DateHanlder.getCurrentDateTime());
					tls.setShiftcode(lsd.getShiftcode());
					tls.setTicketdate(tickeDate);
//					tls.setCurrstationid(lineStation.getStartStation().getsTID());
					tlss.add(tls);
					
				}
				
				Log.info(this.getClass(),"--------------------------------------");
				//生成车票
				TicketStore tickStore = new TicketStore();
				tickStore.setLmid(lm.getId());
				tickStore.setTicketDate(tickeDate);
				tickStore.setMakedate(DateHanlder.getCurrentDate());
				tickStore.setShiftCode(lsd.getShiftcode());
				tickStore.setIsapprove(0);
				tickStore.setIsrelease(0);
				tickStore.setAllquantity(0);
				tickStore.setBalanceCouponquantity(0);
				tickStore.setBalanceQuantity(0);
				tickStore.setCouponquantity(0);
				//检查站点
				TicketStore ts = ticketStoreDao.findByLmidDateShiftCode(lm.getId(),tickStore.getTicketDate() , tickStore.getShiftCode());
				if(ticketStores!=null){
					if(ts==null){
						ticketStores.add(tickStore);
					}
				}
				Log.info(this.getClass(),"===========余票：" ,tickStore.getBalanceQuantity());
				
			}
			return 1;
		}else{
			Log.info(this.getClass(),"=========== 班次详情为空，没有班次....");
		}
		return -1;
	}
	
	/**
	 * 获取站点列表,各个站点组合
	 * @param lm 线路管理
	 * @return
	 * @throws Exception 
	 */
	public List<LineStation> getTickLineStations(LineManage lm) throws Exception{
		
		List<LineStation> stations = new ArrayList<LineStation>();
		List<LineStation> tempStations = new ArrayList<LineStation>();
		//始发站
		LineManageStation begin = new LineManageStation();
		LineManageStation end = new LineManageStation();
		
		begin.setRequiretime(0);
//		begin.setlMID(lm.getId());
//		begin.setsTID(lm.getStstartid());
		begin.setStname(lm.getStstartname());
//		begin.setTime(0);

		//终点站
//		end.setlMID(lm.getId());
//		end.setsTID(lm.getStarriveid());
		end.setStname(lm.getStarrivename());
		end.setRequiretime(lm.getDefaulttime());
//		end.setTime(lm.getDefaulttime());
		end.setSubmileage(lm.getMileage());
		
		List<LineManageStation> lmstations = lm.getLineManageStataions();
		if(lmstations==null){
			lmstations = lmsdao.findByLMID(lm.getId());
			lm.setLineManageStataions(lmstations);
		}
		//组合站点，组合始发站到达城市途经站
		if(lmstations !=null){
			for (LineManageStation lms : lmstations) {
				LineStation lineStation = new LineStation();
				if(lms.getSort()==1){
					LineManageStation lineManageStation = new LineManageStation();
					try {
						BeanUtils.copyProperties(lineManageStation, lms);
					} catch (Exception e){
						e.printStackTrace();
					}
//					lineManageStation.setTime(lms.getRequireTime());
					lineStation.setStartStation(begin);
					lineStation.setEndStation(lineManageStation);
					stations.add(lineStation);
				}
				
			}
			//始发站-终点站
			LineStation beginEndLineStation = new LineStation();
			beginEndLineStation.setStartStation(begin);
			beginEndLineStation.setEndStation(end);
			stations.add(beginEndLineStation);
			
			for (int i = 0; i < lmstations.size(); i++) {
				LineManageStation s = lmstations.get(i);
				if(s.getSort()==1){
					break;
				}
				
//				s.setTime(s.getRequireTime());

				for (int j = (i+1); j < lmstations.size(); j++) {
					LineStation subLineStation = new LineStation();
					LineManageStation sub = new LineManageStation();
					try {
						BeanUtils.copyProperties(sub, lmstations.get(j));
						if(sub.getSort()==0){
							continue;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
//					sub.setTime(sub.getRequireTime());
					subLineStation.setStartStation(s);
					subLineStation.setEndStation(sub);
					tempStations.add(subLineStation);
				}
				
				LineStation LineStation = new LineStation();
				LineStation.setStartStation(s);
				LineStation.setEndStation(end);
				tempStations.add(LineStation);
				
				
			}
		}
		stations.addAll(tempStations);
		return stations;
	}
	
	/**
	 * 获取站点列表,各个站点组合
	 * @param lm 线路管理
	 * @return
	 */
	public List<LineStation> getShiftLineStations(LineManage lm){
		List<LineStation> stations = new ArrayList<LineStation>();
		//始发站
		LineManageStation begin = new LineManageStation();
		LineManageStation end = new LineManageStation();
		begin.setRequiretime(0);
//		begin.setlMID(lm.getId());
//		begin.setsTID(lm.getStstartid());
		begin.setStname(lm.getStstartname());
//		begin.setTime(0);
		
		//终点站
//		end.setlMID(lm.getId());
//		end.setsTID(lm.getStarriveid());
		end.setStname(lm.getStarrivename());
		end.setRequiretime(lm.getDefaulttime());
//		end.setTime(lm.getDefaulttime());
		
		LineStation ls = new LineStation();
		ls.setStartStation(begin);
		ls.setEndStation(end);
		
		stations.add(ls);
		List<LineManageStation> lmstations = lm.getLineManageStataions();
		//组合站点
		if(lmstations!=null){
			//始发站-终点站组合
			for (int i = 0; i < lmstations.size(); i++) {
				LineManageStation s = lmstations.get(i);
				if(s.getSort()==1){
					break;
				}
				LineStation sub = new LineStation();
				LineManageStation endStation = new LineManageStation();
//				s.setTime(0);
				Integer requireTime = 0;
				for (int j = (i+1); j < lmstations.size(); j++) {
					requireTime+=lmstations.get(j).getRequiretime();
					
				}
//				endStation.setlMID(lm.getId());
//				endStation.setsTID(lm.getStarriveid());
				endStation.setStname(lm.getStarrivename());
//				endStation.setTime(lm.getDefaulttime());
				endStation.setRequiretime(lm.getDefaulttime());
				
				sub.setStartStation(s);
				sub.setEndStation(endStation);
				stations.add(sub);
			}
		}
		return stations;
	}
	
	
	/**
	 * 班次发车生成
	 * @param lm 线路管理
	 * @param lineStation 途径站点
	 * @param code 班次号
	 * @param startDate 出发日期
	 * @param startTime 出发时间
	 * @param tickeDate 票面日期
	 * @return
	 */
	public void createShiftStart(ShiftStart shiftStart,LineManage lm,LineStation lineStation,String code,Date startDate ,Long startTime,String tickeDate){
		SimpleDateFormat tf = DateHanlder.getTimeFromat();
		//站点 - 站点 对象
		LineManageStation start = lineStation.getStartStation();
		LineManageStation end = lineStation.getEndStation();
		//车票线路
		//计算到达时间
//		String stationStartTime = tf.format(startTime+start.getTime()*1000*60);
//		String stationEndTime = tf.format(startTime+end.getTime()*1000*60);
//		Log.info(start.getStname(),"===========(",stationStartTime,") -- ",end.getsTName(),"(",stationEndTime,")");
		//班次发车
		shiftStart.setShiftcode(code);
		shiftStart.setRidedate(tickeDate);
		shiftStart.setLmid(lm.getId());
		shiftStart.setLinename(lm.getLinename());
		shiftStart.setOriginstid(lm.getStstartid());
		shiftStart.setOriginstname(lm.getStstartname());
		shiftStart.setOriginstarttime(tf.format(startDate));
		
//		shiftStart.setCurrstationid(start.getsTID());
		shiftStart.setCurrstationname(start.getStname());
		
		shiftStart.setStarriveid(lm.getStarriveid());
		shiftStart.setStarrivename(lm.getStarrivename());
		
		
//		shiftStart.setPunctualstart(stationStartTime);
		shiftStart.setIsstart(Const.SHIFSTATE_DEFAULT_CODE);
		shiftStart.setIsstartname(Const.SHIFSTATE_DEFAULT_Name);
		shiftStart.setCurrpeople(Const.MIN_INT);
		shiftStart.setAllticketnum(Const.MIN_INT);
		shiftStart.setHalfticketnum(Const.MIN_INT);
		shiftStart.setFreeticketnum(Const.MIN_INT);
		shiftStart.setIscancel(0);
		shiftStart.setIscancelname("");
	}
	
	/**
	 * 车票线路生成
	 * @param ticketLine
	 * @param lm
	 * @param lineStation
	 * @param code
	 * @param startDate
	 * @param startTime
	 * @param tickeDate
	 */
	public void createTicketLine(TicketLine ticketLine,LineManage lm,LineStation lineStation,String code,Date startDate ,Long startTime,String tickeDate){
		SimpleDateFormat tf = DateHanlder.getTimeFromat();
		//站点 - 站点 对象
		LineManageStation start = lineStation.getStartStation();
		LineManageStation end = lineStation.getEndStation();
		//车票线路
		ticketLine.setLmid(lm.getId());
		ticketLine.setLinename(lm.getLinename());
		ticketLine.setShiftcode(code);
		//首发时间
		ticketLine.setOriginstarttime(tf.format(startDate) );
		//开始城市
		ticketLine.setCitystartid(lm.getCitystartid());
//		ticketLine.setStstartid(start.getsTID());
		ticketLine.setStstartname(start.getStname());
		//计算到达时间
//		String stationStartTime = tf.format(startTime+start.getTime()*1000*60);
//		String stationEndTime = tf.format(startTime+end.getTime()*1000*60);
		
//		ticketLine.setStarttime(stationStartTime);
//		ticketLine.setArrivetime(stationEndTime);
		
		ticketLine.setTicketprice(BigDecimal.ZERO);
		
		ticketLine.setTranscompany(lm.getTranscompany());
		ticketLine.setTicketdate(tickeDate);
		ticketLine.setMakedate(DateHanlder.getCurrentDate());
		
		//到达城市
		ticketLine.setCityarriveid(lm.getCityarriveid());
//		ticketLine.setStarriveid(end.getsTID());
//		ticketLine.setStarrivename(end.getsTName());
		ticketLine.setShiftcode(code);
//		Log.info(this.getClass(),"===========",start.getsTName(),"(",stationStartTime,") -- ",end.getsTName(),"(",stationEndTime,")");
	}
}

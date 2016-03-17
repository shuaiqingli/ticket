//package com.hengyu.ticket.service;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.hengyu.ticket.common.Const;
//import com.hengyu.ticket.common.LineStation;
//import com.hengyu.ticket.dao.LineManageDao;
//import com.hengyu.ticket.dao.ShiftStartDao;
//import com.hengyu.ticket.dao.TicketLineDao;
//import com.hengyu.ticket.dao.TicketStoreDao;
//import com.hengyu.ticket.entity.LineManage;
//import com.hengyu.ticket.entity.LineManageStation;
//import com.hengyu.ticket.entity.LinePrice;
//import com.hengyu.ticket.entity.LineSchedue;
//import com.hengyu.ticket.entity.ShiftStart;
//import com.hengyu.ticket.entity.TicketLine;
//import com.hengyu.ticket.entity.TicketStore;
//import com.hengyu.ticket.util.DateHanlder;
//import com.hengyu.ticket.util.Log;
//
///**
// * 生成车票，车票线路，发车.
// * @author LGF
// *
// */
//@Service
//public class GenerateTicketService {
//
//	@Autowired
//	private LineManageDao lmdao;
//	@Autowired
//	private TicketStoreDao ticketStoreDao;
//	@Autowired
//	private TicketLineDao ticketLineDao;
//	@Autowired
//	private ShiftStartDao shiftStartDao;
//
//	/**
//	 * 保存生成车票
//	 * @param dates
//	 * @param isCheck 是否检查已经生成过车票
//	 * @return
//	 * @throws Exception
//	 */
//	public boolean saveGenerate(Set<String> dates,boolean isCheck) throws Exception{
//		if(dates==null){
//			return false;
//		}
//		List<TicketStore> list = null;
//		if(isCheck){
//			list = ticketStoreDao.findByDates(new ArrayList<String>(dates));
//		}
//		try {
//			List<LineManage> lines = lmdao.findApproveLines();
//			//车票线路
//			List<TicketLine> ticketLines = new ArrayList<TicketLine>();
//			//班次发车
//			List<ShiftStart> shiftStarts = new ArrayList<ShiftStart>();
//			//票库
//			List<TicketStore> stores = new ArrayList<TicketStore>();
//			for (String date : dates) {
//				addGenerate(isCheck,list,lines, date,stores,shiftStarts,ticketLines);
//			}
//
//			Log.info(this.getClass(),"shiftStarts:"+shiftStarts.size());
//			Log.info(this.getClass(),"ticketLines:"+ticketLines.size());
//			Log.info(this.getClass(),"lines:"+lines.size());
//			if(ticketLines.size()!=0){
//				ticketLineDao.batchSave(ticketLines);
//			}
//			if(shiftStarts.size()!=0){
//				shiftStartDao.batchSave(shiftStarts);
//			}
//			if(stores.size()!=0){
//				ticketStoreDao.batchSave(stores);
//			}
//		} catch (Exception e) {
//			Log.error(e);
//			throw e;
//		}
//		return true;
//	}
//
//	/**
//	 * 保存生成车票
//	 * @param dates
//	 * @return
//	 * @throws Exception
//	 */
//	public boolean saveGenerate(Set<String> dates) throws Exception{
//		return saveGenerate(dates, true);
//	}
//
//	/**
//	 *  生成车票
//	 * @param lines 线路
//	 * @param tickeDate 生成票面日期
//	 * @param ticketStores 票库集合
//	 * @param shiftStarts 班次发车集合
//	 * @param ticketLines 车票线路
//	 * @return
//	 * @throws Exception
//	 */
//	public boolean addGenerate(boolean isCheck,List<TicketStore> checkTicketStores,List<LineManage> lines,String tickeDate,
//			List<TicketStore> ticketStores,List<ShiftStart> shiftStarts,List<TicketLine> ticketLines)
//			throws Exception{
//		if(lines!=null){
//			Log.info(this.getClass(),"总共线路条数！" + lines.size());
//		}
//		for (LineManage lm : lines) {
//			if(isCheck&&checkTicketStores!=null){
//				boolean isexist = false;
//				for (TicketStore tt : checkTicketStores) {
//					if(tt.getLmid().equals(lm.getLineid())&&tickeDate.equals(tt.getTicketDate())){
//						isexist = true;
//						break;
//					}
//				}
//				if(isexist){
//					Log.info(this.getClass(),"日期："+tickeDate+",线路编号："+lm.getLineid()+"-"+lm.getLinename()+",该日期已经生成过....");
//					continue;
//				}
//			}
//			Log.info(this.getClass(),"-----------------  *("+lm.getLineid()+"-"+lm.getLinename()+")*  ---------------");
//			//排班列表
//			List<LineSchedue> lineSchedues = lm.getLineSchedues();
//			//途经站点
//			List<LineManageStation> lmstations = lm.getLineManageStataions();
//			//发车表生成规则  A,B,C -- D,E,F
//			// A-F,D-F,C-F
//			List<LineStation> shiftLineStations = getShiftLineStations(lm, lmstations);
//			//生成线路站点
//			//A,B,C -- D,E,F
//			//A-D,A-E,A-F   B-D,B-E,B-F ....
//			List<LineStation> tickectLineStations = getTickLineStations(lm, lmstations);
//			//需要添加班次发车列表
////			tickectLineStations.addAll(shiftLineStations);
//
////			System.out.println("*** shiftLineStations "+shiftLineStations);
////			System.out.println("*** tickectLineStations "+tickectLineStations);
//
//			Integer id = 1;
//			SimpleDateFormat tf = DateHanlder.getTimeFromat();
//			//遍历排班
//			for (LineSchedue lineSchedue : lineSchedues) {
//				SimpleDateFormat dateFromat = DateHanlder.getDateFromat();
//				//判断排班是否在有效期内
//				long begindate = dateFromat.parse(lineSchedue.getBegindate()).getTime();
//				long enddate = dateFromat.parse(lineSchedue.getEnddate()).getTime();
//				long ticketdate = dateFromat.parse(tickeDate).getTime();
//				long currentdate = System.currentTimeMillis();
//				if(currentdate<begindate||currentdate>enddate || ticketdate>enddate || ticketdate<begindate){
//					Log.info(this.getClass(),"生成车票日期："+tickeDate+"  不在有效时间以内 ... ");
//					Log.info(this.getClass(),"生效时间（"+lineSchedue.getBegindate()+"）  失效时间（"+lineSchedue.getEnddate()+"）\n");
//					continue;
//				}
//				/*
//				Integer shiftNum = lineSchedue.getShiftNum();
//				String beginTime = lineSchedue.getSchedueTime();
//				Integer intervalMinute = lineSchedue.getIntervalMinute();
//				List<String> codes = NumberCreate.createShiftCodes(lm.getLinePre(), shiftNum,id);
//				for (int i = 0; i < shiftNum; i++) {
//					//每次发车的开始时间
//					String code = codes.get(i);
//					Long startTime = tf.parse(beginTime).getTime()+(i*intervalMinute*1000*60);
//					Date startDate = new Date(startTime);
//					Log.info(this.getClass(),"发车时间：("+tickeDate+")"+tf.format(startDate));
//					//车票线路生成
//					for (int j = 0; j < tickectLineStations.size(); j++) {
//						LineStation lineStation = tickectLineStations.get(j);
//						TicketLine ticketLine = new TicketLine();
//						createTicketLine(ticketLine, lm, lineStation, code, startDate, startTime, tickeDate);
//						//价格模板处理
//						LinePriceHanlder(ticketLine, lm);
//						if(ticketLines!=null){
//							ticketLines.add(ticketLine);
//						}
//					}
//
//					Log.info(this.getClass(),"生成发车表：("+tickeDate+")"+tf.format(startDate));
//					//发车生成
//					for (int j = 0; j < shiftLineStations.size(); j++) {
//						LineStation lineStation = shiftLineStations.get(j);
//						ShiftStart shiftStart = new ShiftStart();
//						createShiftStart(shiftStart,lm, lineStation, code, startDate, startTime, tickeDate);
//						if(shiftStarts!=null){
//							shiftStarts.add(shiftStart);
//						}
//					}
//					Log.info(this.getClass(),"--------------------------------------\r\n");
//					//生成车票
//					TicketStore tickStore = new TicketStore();
//					tickStore.setLmid(lm.getId());
//					tickStore.setBalanceQuantity(lm.getTicketQuantity());
//					tickStore.setTicketDate(tickeDate);
//					tickStore.setMakedate(DateHanlder.getCurrentDate());
//					tickStore.setShiftCode(code);
//					if(ticketStores!=null){
//						ticketStores.add(tickStore);
//					}
//					Log.info(this.getClass(),"余票：" +tickStore.getBalanceQuantity());
//				}
//				id += codes.size();
//				 */
//			}
//
//		}
//		return true;
//	}
//
//	/**
//	 * 价格模板处理
//	 * @param tkl
//	 * @param lm
//	 */
//	public void LinePriceHanlder(TicketLine tkl,LineManage lm){
//		if(tkl==null){
//			return;
//		}
//		//价格模板
//		List<LinePrice> linePrices = lm.getLinePrices();
//		if(linePrices!=null){
//			//循环匹配
//			for (LinePrice linePrice : linePrices) {
//				if(tkl.getStstartid().equals(linePrice.getStstartid())&&tkl.getStarriveid().equals(linePrice.getStarriveid())){
//					if(linePrice.getPrice()!=null&&linePrice.getPrice().intValue()!=0){
//						tkl.setTicketprice(linePrice.getPrice());
//					}else{
//						//如果价格为 0 使用默认价格
//						tkl.setTicketprice(lm.getTicketprice());
//					}
//				}
//			}
//		}
//	}
//
//	/**
//	 * 班次发车生成
//	 * @param lm 线路管理
//	 * @param lineStation 途径站点
//	 * @param code 班次号
//	 * @param startDate 出发日期
//	 * @param startTime 出发时间
//	 * @param tickeDate 票面日期
//	 * @return
//	 */
//	public void createShiftStart(ShiftStart shiftStart,LineManage lm,LineStation lineStation,String code,Date startDate ,Long startTime,String tickeDate){
//		SimpleDateFormat tf = DateHanlder.getTimeFromat();
//		//站点 - 站点 对象
//		LineManageStation start = lineStation.getStartStation();
//		LineManageStation end = lineStation.getEndStation();
//		//车票线路
//		//计算到达时间
//		String stationStartTime = tf.format(startTime+start.getTime()*1000*60);
//		String stationEndTime = tf.format(startTime+end.getTime()*1000*60);
//		Log.info(start.getsTName()+"("+stationStartTime+") -- "+end.getsTName()+"("+stationEndTime+")");
//		//班次发车
//		shiftStart.setShiftcode(code);
//		shiftStart.setRidedate(tickeDate);
//		shiftStart.setLmid(lm.getId());
//		shiftStart.setLinename(lm.getLinename());
//		shiftStart.setOriginstid(lm.getStstartid());
//		shiftStart.setOriginstname(lm.getStstartname());
//		shiftStart.setOriginstarttime(tf.format(startDate));
//
//		shiftStart.setCurrstationid(start.getsTID());
//		shiftStart.setCurrstationname(start.getsTName());
//
//		shiftStart.setStarriveid(lm.getStarriveid());
//		shiftStart.setStarrivename(lm.getStarrivename());
//
//
//		shiftStart.setPunctualstart(stationStartTime);
//		shiftStart.setIsstart(Const.SHIFSTATE_DEFAULT_CODE);
//		shiftStart.setIsstartname(Const.SHIFSTATE_DEFAULT_Name);
//		shiftStart.setCurrpeople(Const.MIN_INT);
//		shiftStart.setAllticketnum(Const.MIN_INT);
//		shiftStart.setHalfticketnum(Const.MIN_INT);
//		shiftStart.setFreeticketnum(Const.MIN_INT);
//	}
//
//	/**
//	 * 车票线路生成
//	 * @param ticketLine
//	 * @param lm
//	 * @param lineStation
//	 * @param code
//	 * @param startDate
//	 * @param startTime
//	 * @param tickeDate
//	 */
//	public void createTicketLine(TicketLine ticketLine,LineManage lm,LineStation lineStation,String code,Date startDate ,Long startTime,String tickeDate){
//		SimpleDateFormat tf = DateHanlder.getTimeFromat();
//		//站点 - 站点 对象
//		LineManageStation start = lineStation.getStartStation();
//		LineManageStation end = lineStation.getEndStation();
//		//车票线路
//		ticketLine.setLmid(lm.getId());
//		ticketLine.setLinename(lm.getLinename());
//		ticketLine.setShiftcode(code);
//		//首发时间
//		ticketLine.setOriginstarttime(tf.format(startDate) );
//		//开始城市
//		ticketLine.setCitystartid(lm.getCitystartid());
//		ticketLine.setStstartid(start.getsTID());
//		ticketLine.setStstartname(start.getsTName());
//		//计算到达时间
//		String stationStartTime = tf.format(startTime+start.getTime()*1000*60);
//		String stationEndTime = tf.format(startTime+end.getTime()*1000*60);
//
//		ticketLine.setStarttime(stationStartTime);
//		ticketLine.setArrivetime(stationEndTime);
//
//		if(end.getUnitPrice()!=null&&end.getUnitPrice().intValue()!=0){
//			ticketLine.setTicketprice(end.getUnitPrice());
//		}else{
//			ticketLine.setTicketprice(lm.getTicketprice());
//		}
//		ticketLine.setTranscompany(lm.getTranscompany());
//		ticketLine.setTicketdate(tickeDate);
//		ticketLine.setMakedate(DateHanlder.getCurrentDate());
//
//		//到达城市
//		ticketLine.setCityarriveid(lm.getCityarriveid());K
//		ticketLine.setStarriveid(end.getsTID());
//		ticketLine.setStarrivename(end.getsTName());
//		ticketLine.setShiftcode(code);
//		Log.info(start.getsTName()+"("+stationStartTime+") -- "+end.getsTName()+"("+stationEndTime+")");
//	}
//
//
//	/**
//	 * 获取站点列表,各个站点组合
//	 * @param lm 线路管理
//	 * @param lmstations 途径站点
//	 * @return
//	 */
//	public List<LineStation> getShiftLineStations(LineManage lm,List<LineManageStation> lmstations){
//		List<LineStation> stations = new ArrayList<LineStation>();
////		List<LineStation> tempStations = new ArrayList<LineStation>();
//		//始发站
//		LineManageStation begin = new LineManageStation();
//		LineManageStation end = new LineManageStation();
//		begin.setRequireTime(0);
//		begin.setlMID(lm.getId());
//		begin.setsTID(lm.getStstartid());
//		begin.setSTName(lm.getStstartname());
//		begin.setTime(0);
//
//		//终点站
//		end.setlMID(lm.getId());
//		end.setsTID(lm.getStarriveid());
//		end.setSTName(lm.getStarrivename());
//		end.setRequireTime(lm.getDefaulttime());
//		end.setTime(lm.getDefaulttime());
//
//		LineStation ls = new LineStation();
//		ls.setStartStation(begin);
//		ls.setEndStation(end);
//
//		stations.add(ls);
//
//		//组合站点
//		if(lmstations!=null){
//			//始发站-终点站组合
////			Integer endRequireTime = 0;
////			for (LineManageStation lms : lmstations) {
////				endRequireTime+=lms.getRequireTime();
////			}
////			end.setRequireTime(endRequireTime);
////			end.setTime(endRequireTime);
//
////			Integer time = 0;
//			for (int i = 0; i < lmstations.size(); i++) {
//				LineManageStation s = lmstations.get(i);
////				time += s.getRequireTime();
//				if(s.getSort()==1){
//					break;
//				}
//				LineStation sub = new LineStation();
//				LineManageStation endStation = new LineManageStation();
//				s.setTime(0);
////				Integer time2 = time;
//				Integer requireTime = 0;
//				for (int j = (i+1); j < lmstations.size(); j++) {
////					time2+=lmstations.get(j).getRequireTime();
//					requireTime+=lmstations.get(j).getRequireTime();
//
//				}
//
//				endStation.setlMID(lm.getId());
//				endStation.setsTID(lm.getStarriveid());
//				endStation.setSTName(lm.getStarrivename());
//				endStation.setTime(lm.getDefaulttime());
//				endStation.setRequireTime(lm.getDefaulttime());
//
//				sub.setStartStation(s);
//				sub.setEndStation(endStation);
//				stations.add(sub);
//			}
//		}
//		return stations;
//	}
//
//	/**
//	 * 获取站点列表,各个站点组合
//	 * @param lm 线路管理
//	 * @param lmstations 途径站点
//	 * @return
//	 */
//	public List<LineStation> getTickLineStations(LineManage lm,List<LineManageStation> lmstations){
//		List<LineStation> stations = new ArrayList<LineStation>();
//		List<LineStation> tempStations = new ArrayList<LineStation>();
//		//始发站
//		LineManageStation begin = new LineManageStation();
//		LineManageStation end = new LineManageStation();
//
//		begin.setRequireTime(0);
//		begin.setlMID(lm.getId());
//		begin.setsTID(lm.getStstartid());
//		begin.setSTName(lm.getStstartname());
//		begin.setTime(0);
//
//		//终点站
//		end.setlMID(lm.getId());
//		end.setsTID(lm.getStarriveid());
//		end.setSTName(lm.getStarrivename());
//		end.setRequireTime(lm.getDefaulttime());
//		end.setTime(lm.getDefaulttime());
//
//
//		//组合站点，组合始发站到达城市途经站
//		if(lmstations!=null){
//			for (LineManageStation lms : lmstations) {
//				LineStation lineStation = new LineStation();
//				if(lms.getSort()==1){
//					LineManageStation lineManageStation = new LineManageStation();
//					try {
//						BeanUtils.copyProperties(lineManageStation, lms);
//					} catch (Exception e){
//						e.printStackTrace();
//					}
//					lineManageStation.setTime(lms.getRequireTime());
//					lineStation.setStartStation(begin);
//					lineStation.setEndStation(lineManageStation);
//					stations.add(lineStation);
//				}
//
//			}
//			//始发站-终点站
//			LineStation beginEndLineStation = new LineStation();
//			beginEndLineStation.setStartStation(begin);
//			beginEndLineStation.setEndStation(end);
//			stations.add(beginEndLineStation);
//
//			for (int i = 0; i < lmstations.size(); i++) {
//				LineManageStation s = lmstations.get(i);
//				if(s.getSort()==1){
//					break;
//				}
//
//				s.setTime(s.getRequireTime());
//
//				for (int j = (i+1); j < lmstations.size(); j++) {
//					LineStation subLineStation = new LineStation();
//					LineManageStation sub = new LineManageStation();
//					try {
//						BeanUtils.copyProperties(sub, lmstations.get(j));
//						if(sub.getSort()==0){
//							continue;
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					sub.setTime(sub.getRequireTime());
//					subLineStation.setStartStation(s);
//					subLineStation.setEndStation(sub);
//					tempStations.add(subLineStation);
//				}
//
//				LineStation LineStation = new LineStation();
//				LineStation.setStartStation(s);
//				LineStation.setEndStation(end);
//				tempStations.add(LineStation);
//
//
//			}
//		}
//		stations.addAll(tempStations);
//		return stations;
//	}
//
//	/**
//	 * 按线路获取模板
//	 * @param lm 线路管理
//	 * @return
//	 * 返回 List<TicketLine>
//	 */
//	public List<TicketLine> getTicketLineByLineManage(LineManage lm){
//		List<LineManageStation> lmstations = lm.getLineManageStataions();
//		//生成线路站点
//		//A,B,C -- D,E,F
//		//A-D,A-E,A-F   B-D,B-E,B-F ....
//		//该方法没有生成 A-F,B-F,C-F
//		List<LineStation> tickectLineStations = getTickLineStations(lm, lmstations);
//		List<TicketLine> ticketLines = new ArrayList<TicketLine>();
//		Long startTime = System.currentTimeMillis();
//		Date startDate = new Date();
//		for (LineStation lineStation : tickectLineStations) {
//			TicketLine ticketLine = new TicketLine();
//			createTicketLine(ticketLine , lm, lineStation, Const.MIN_INT.toString(), startDate, startTime, DateHanlder.getCurrentDate());
//			ticketLines.add(ticketLine);
//		}
//		return ticketLines;
//	}
//
//
//}
//
///**
// * 站点信息列表
// * @author LGF
// *
// */
////class LineStation{
////	//开始站点
////	private LineManageStation startStation;
////	//到达站点
////	private LineManageStation endStation;
////
////	public LineManageStation getStartStation() {
////		return startStation;
////	}
////	public void setStartStation(LineManageStation startStation) {
////		this.startStation = startStation;
////	}
////	public LineManageStation getEndStation() {
////		return endStation;
////	}
////	public void setEndStation(LineManageStation endStation) {
////		this.endStation = endStation;
////	}
////	@Override
////	public String toString() {
////		return "["+startStation.getsTName()+"-"+endStation.getSTName()+"]";
////	}
////}

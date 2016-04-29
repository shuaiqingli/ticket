package com.hengyu.ticket.service;
import java.math.BigDecimal;
import java.util.*;

import com.hengyu.ticket.common.LineStation;
import com.hengyu.ticket.dao.ShiftDao;
import com.hengyu.ticket.dao.StationTimeRuleDao;
import com.hengyu.ticket.dao.TripPriceSubDao;
import com.hengyu.ticket.entity.*;
import org.apache.poi.sl.usermodel.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.TripPriceListDao;
import com.hengyu.ticket.util.Log;
import org.springframework.util.Assert;

/**
 * 
 * @author 李冠锋 2015-11-28
 *
 */
 @Service
public class TripPriceListService{
	
	 
	@Autowired
	private TripPriceListDao tripPriceListDao;
    @Autowired
    private StationTimeRuleDao stationTimeRuleDao;
    @Autowired
    private ScheduleTaskService scheduleTaskService;
    @Autowired
    private TripPriceSubDao tripPriceSubDao;


    /**
     * 获取售票规则
     * @param id
     * @param lrid 线路规则编号
     * @return
     * @throws Exception
     */
    public TripPriceList getSaleRule(Integer id,Integer lrid) throws Exception {
        TripPriceList tpl = null;
        if(id!=null){
            tpl = tripPriceListDao.find(id);
            lrid = tpl.getStrid();
            List<LineManageStation> diffStations = tripPriceListDao.getDifferStation(id, tpl.getStrid());
            if(diffStations!=null&&!diffStations.isEmpty()){
//                List<LineManageStation> stations = stationTimeRuleDao.getDistinctStationByGroupID(tpl.getStrid());
                List<TripPriceSub> ticketStations = getTripPriceSubByStations(getFilterLineStation(lrid));
                List<TripPriceSub> subs = tpl.getTps();
                List<TripPriceSub> temps = new ArrayList<TripPriceSub>();

                for (TripPriceSub ts : ticketStations) {
                    boolean isnew = true;
                    for (TripPriceSub sub : subs) {
                        if(ts.getStarriveid().equals(sub.getStarriveid())&&ts.getStstartid().equals(sub.getStstartid())){
                            temps.add(sub);
                            isnew = false;
                            break;
                        }
                    }
                    if(isnew){
                        temps.add(ts);
                    }
                }

                tpl.setTps(temps);
            }
        }else if(lrid!=null){
            tpl = new TripPriceList();
            tpl.setStrid(lrid);
            List<LineStation> stations = getFilterLineStation(lrid);
            List<TripPriceSub> tps = getTripPriceSubByStations(stations);
            Collections.sort(tps,new Comparator<TripPriceSub>() {
                @Override
                public int compare(TripPriceSub o1, TripPriceSub o2) {
                    return o1.getStstartid().compareTo(o2.getStstartid());
                }
            });
            tpl.setTps(tps);
        }
        Assert.notNull(tpl,"没有找到售票规则！");
        return tpl;
    }

    //过滤重复站点
    public List<LineStation> getFilterLineStation(Integer lrid) throws Exception {
        StationTimeRule stationTimeRule = stationTimeRuleDao.find(lrid);
        List<StationTimeRule> rules = stationTimeRule.getRules();
        Set<LineStation> setStations = new HashSet<>();
        for (StationTimeRule rule: rules ) {
            List<LineManageStation> lms = rule.getStations();
            Assert.notEmpty(lms,"线路规则没有找到站点!");
            setStations.addAll(scheduleTaskService.getTicketLineStation(lms));
        }
        return new ArrayList<>(setStations);
    }

    //获取行程价格
    public List<TripPriceSub> getTripPriceSubByStations( List<LineStation> ticketStations ){
        List<TripPriceSub> subs = new ArrayList<>();
        int index = 0;
        for (LineStation station : ticketStations) {
            TripPriceSub sub = new TripPriceSub();
            sub.setMileage(0);
            sub.setPrice(BigDecimal.ZERO);
            sub.setStstartid(station.getStartStation().getStid());
            sub.setStstartname(station.getStartStation().getStname());
            sub.setStarriveid(station.getEndStation().getStid());
            sub.setStarrivename(station.getEndStation().getStname());
            sub.setPricesort(index);
            sub.setLimitcoupons(0);
            sub.setLimitsale(0);
            subs.add(sub);
            index++;
        }
        return subs;
    }


    //删除售票规则
    public int delete(Integer id,Integer lrid,boolean ischeck) throws Exception {
        int ret = -1;
        if(ischeck){
            Assert.isTrue(tripPriceListDao.deleteTripPriceSub(id,lrid)>=1,"删除行程详情失败！");
            ret = tripPriceListDao.delete(id,lrid);
            Assert.isTrue(ret>=1,"删除售票规则失败！");
        }else{
            tripPriceListDao.deleteTripPriceSub(id,lrid);
            ret = tripPriceListDao.delete(id,lrid);
        }
        return ret;
    }

	
	/**
	 * 保存一个对象
	 * @param tripPriceList
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(TripPriceList tripPriceList) throws Exception{
        if(tripPriceList.getIsdefault()!=null&&tripPriceList.getIsdefault()==1){
            tripPriceListDao.resetDefault(tripPriceList.getLmid(),tripPriceList.getStrid());
        }
		int ret = tripPriceListDao.save(tripPriceList);
        Assert.isTrue(ret==1,"保存售票规则失败！");
        saveOrUpdateSub(tripPriceList,false);
		return ret;
	}

	/**
	 * 更新一个对象
	 * @param tripPriceList
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(TripPriceList tripPriceList) throws Exception{
        if(tripPriceList.getIsdefault()!=null&&tripPriceList.getIsdefault()==1){
            tripPriceListDao.resetDefault(tripPriceList.getLmid(),tripPriceList.getStrid());
        }
        int ret = tripPriceListDao.update(tripPriceList);
        Assert.isTrue(ret==1,"更新售票规则失败！");
        saveOrUpdateSub(tripPriceList,true);
        return ret;
	}

    //更新或保存行程价格
    public void saveOrUpdateSub(TripPriceList tripPriceList,boolean isupdate) throws Exception {
        List<TripPriceSub> subs = tripPriceList.getTps();
        for (TripPriceSub sub : subs){
            sub.setTprid(tripPriceList.getId());
            if(isupdate){
                if(sub.getId()==null){
                    Assert.isTrue(tripPriceSubDao.save(sub)==1,"保存行程价格失败！");
                    continue;
                }
                Assert.isTrue(tripPriceSubDao.update(sub)==1,"更新行程价格失败！");
            }else{
                Assert.isTrue(tripPriceSubDao.save(sub)==1,"保存行程价格失败！");
            }

        }

    }



	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回TripPriceList对象
	 * @throws Exception
	 */
	public TripPriceList find(Integer id) throws Exception{
		return tripPriceListDao.find(id);
	}
	
	//检查是否有日期冲突
	public TripPriceList findExistsDate(TripPriceList tp) throws Exception{
		return tripPriceListDao.findExistsDate(tp);
	}
	
	//获取星期
	public TripPriceList findWeekdayByTripPrice(TripPriceRule tpr) throws Exception{
		return tripPriceListDao.findWeekdayByTripPrice(tpr);
	}
	
	//查询列表
	public List<TripPriceList> findlist(Page page) throws Exception{
		page.setTotalCount(tripPriceListDao.count(page));
		return tripPriceListDao.findlist(page);
	}

    //按线路编号查询
	public List<TripPriceList> findByLMID(Integer lmid) throws Exception{
		return tripPriceListDao.findByLMID(lmid);
	}

	//是否允许修改线路
	public int findisAllowUpdateLineManage(Integer lmid) throws Exception{
		return tripPriceListDao.findisAllowUpdateLineManage(lmid);
	}
	
	public void setTripPriceListDao(TripPriceListDao tripPriceListDao){
		this.tripPriceListDao = tripPriceListDao;
	}
	
}

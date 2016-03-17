package com.hengyu.ticket.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hengyu.ticket.dao.StationLineDao;
import com.hengyu.ticket.dao.TripPriceListDao;
import com.hengyu.ticket.dao.TripPriceRuleDao;
import com.hengyu.ticket.dao.TripPriceSubDao;
import com.hengyu.ticket.entity.StationLine;
import com.hengyu.ticket.entity.TripPriceList;
import com.hengyu.ticket.entity.TripPriceRule;
import com.hengyu.ticket.entity.TripPriceSub;
import com.hengyu.ticket.util.Log;

/**
 * 
 * @author 李冠锋 2015-11-14
 *
 */
 @Service
public class TripPriceRuleService{
	
	@Autowired
	private TripPriceRuleDao tripPriceRuleDao;
	@Autowired
	private TripPriceSubDao tpsdao;
	@Autowired
	private TripPriceListDao tpldao;
	@Autowired
	private StationLineDao sldao;
	
	/**
	 * 保存一个对象
	 * @param tripPriceRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(TripPriceRule tripPriceRule) throws Exception{
		int r = tripPriceRuleDao.save(tripPriceRule);
		Assert.isTrue(r==1);
		Log.info(this.getClass(),"=====保存行程价格规则 ",r);
		saveOrUpdateTripPriceSub(tripPriceRule);
		saveOrUpdateStationLine(tripPriceRule);
		return r;
	}
	
	/**
	 * 更新一个对象
	 * @param tripPriceRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(TripPriceRule tripPriceRule) throws Exception{
		Log.info(this.getClass(),"=====更新行程价格规则 ");
		int r = -1;
		if(tripPriceRule.getWeekday()==0){
			int delete = tripPriceRuleDao.delete(tripPriceRule.getId());
			int delete2 = tpsdao.deleteByTripPriceRule(tripPriceRule.getId());
			int delete3 = sldao.delByTripPriceRule(tripPriceRule.getId());
			tripPriceRule.setTps(null);
			tripPriceRule.setStationlines(null);
			Log.info(this.getClass(),"=====发现未设置星期的规则，删除规则、规则详情、限制票规则 [",delete,",",delete2,",",delete3,"]");
			r = 1;
		}else{
			r = tripPriceRuleDao.update(tripPriceRule);
		}
		saveOrUpdateTripPriceSub(tripPriceRule);
		saveOrUpdateStationLine(tripPriceRule);
		return r;
	}
	
	//保存站点卖票规则
	public void saveOrUpdateStationLine(TripPriceRule tripPriceRule) throws Exception{
		List<StationLine> stationlines = tripPriceRule.getStationlines();
		if(stationlines==null){
			return;
		}
		for (StationLine stationLine : stationlines) {
			StationLine sl = sldao.find(stationLine.getId());
			stationLine.setTprid(tripPriceRule.getId());
			stationLine.setLmid(tripPriceRule.getLmid());
			if(sl==null){
				Assert.isTrue(sldao.save(stationLine)==1);
			}else{
				Assert.isTrue(sldao.update(stationLine)==1);
			}
		}
		
	}
	
	//更新或添加行程价格
	public void saveOrUpdateTripPriceSub(TripPriceRule tripPriceRule) throws Exception{
		List<TripPriceSub> tps = tripPriceRule.getTps();
		if(tps!=null){
			for (TripPriceSub sub : tps) {
				sub.setTprid(tripPriceRule.getId());
				if(sub.getId()==null){
					tpsdao.save(sub);
				}else{
					tpsdao.update(sub);
				}
			}
			Log.info(this.getClass(),"=====更新或保存行程价格详情： ",tps.size());
		}
		TripPriceRule tpr = new TripPriceRule();
		tpr.setTplid(tripPriceRule.getTplid());
		TripPriceList tpl = tpldao.findWeekdayByTripPrice(tpr);
		if(tpl!=null&&127==tpl.getWeekday()){
			Log.info(this.getClass(),"=====已经设置的星期总和： ",tpl.getWeekday());
			TripPriceList utpl = tpldao.find(tpr.getTplid());
			utpl.setIseffect(1);
			tpldao.update(utpl);
			Log.info(this.getClass(),"=====自动激活行程价格规则 ");
		}else{
			if(tpl!=null){
				Log.info(this.getClass(),"===== ^^ 已经设置的星期总和： ",tpl.getWeekday());
				TripPriceList utpl = tpldao.find(tpr.getTplid());
				utpl.setIseffect(0);
				tpldao.update(utpl);
				Log.info(this.getClass(),"=====自动禁用行程价格规则 ");
			}
		}
		
		
	}
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回TripPriceRule对象
	 * @throws Exception
	 */
	public TripPriceRule find(Integer id) throws Exception{
		return tripPriceRuleDao.find(id);
	}
	
	public void setTripPriceRuleDao(TripPriceRuleDao tripPriceRuleDao){
		this.tripPriceRuleDao = tripPriceRuleDao;
	}
}

package com.hengyu.ticket.service;
import com.hengyu.ticket.dao.LineManageStationDao;
import com.hengyu.ticket.entity.LineManageStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.StationTimeRuleDao;
import com.hengyu.ticket.entity.StationTimeRule;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 
 * @author 李冠锋 2016-03-11
 *
 */
 @Service
public class StationTimeRuleService{
	
	@Autowired
	private StationTimeRuleDao stationTimeRuleDao;
	@Autowired
	private LineManageStationDao lineManageStationDao;
	
	/**
	 * 保存一个对象
	 * @param stationTimeRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(StationTimeRule stationTimeRule) throws Exception{
		if(stationTimeRule.getIsdefault()!=null&&stationTimeRule.getIsdefault()==1){
			stationTimeRuleDao.resetDefaultRule(stationTimeRule.getLmid());
		}
		Assert.isTrue(stationTimeRuleDao.save(stationTimeRule)==1,"保存规则失败！");
		saveOrUpdateRule(stationTimeRule,false);
		return 1;
	}

	/**
	 * 更新一个对象
	 * @param stationTimeRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(StationTimeRule stationTimeRule) throws Exception{
		if(stationTimeRule.getIsdefault()!=null&&stationTimeRule.getIsdefault()==1){
			stationTimeRuleDao.resetDefaultRule(stationTimeRule.getLmid());
		}
		Assert.isTrue(stationTimeRuleDao.update(stationTimeRule)==1,"更新规则失败！");
		saveOrUpdateRule(stationTimeRule,true);
		return 1;
	}


	//保存或更新规则
	public void saveOrUpdateRule(StationTimeRule str,boolean isupdate) throws Exception {
		List<StationTimeRule> rules = str.getRules();
		if(rules==null||rules.isEmpty()){
			stationTimeRuleDao.deleteStationByRuleID(str.getId());
			stationTimeRuleDao.delTimeRule(str);
			return;
		}
		if (isupdate){
			stationTimeRuleDao.delTimeRule(str);
		}
		for (StationTimeRule rule :rules ) {
			rule.setGroupid(str.getId());
			if(rule.getId()==null){
				Assert.isTrue(stationTimeRuleDao.save(rule)==1,"保存规则失败！");
			}else{
				Assert.isTrue(stationTimeRuleDao.update(rule)==1,"更新规则失败！");
			}

			saveOrUpdateStation(rule,isupdate);
		}
	}

	//更新或保存途径站点
	public void saveOrUpdateStation(StationTimeRule rule,boolean isupdate) throws Exception {
		if (isupdate){
			stationTimeRuleDao.delStationByTimeRule(rule);
		}
		List<LineManageStation> stations = rule.getStations();
		if(stations==null||stations.isEmpty()){
			return;
		}
		int i = 1;
		for (LineManageStation station  : stations ) {
			station.setStrid(rule.getId());
			station.setSortnum(i);
			if(station.getId()==null){
				Assert.isTrue(lineManageStationDao.save(station)==1,"保存途径站点失败！");
			}else{
				Assert.isTrue(lineManageStationDao.update(station)==1,"更新途径站点失败！");
			}
			i++;
		}
	}

	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回StationTimeRule对象
	 * @throws Exception
	 */
	public StationTimeRule find(Integer id) throws Exception{
		return stationTimeRuleDao.find(id);
	}

	public List<StationTimeRule> findByLMID(Integer lmid) throws Exception {
		return stationTimeRuleDao.findByLMID(lmid);
	}

	public void delete(Integer id) throws Exception {
		StationTimeRule rule = stationTimeRuleDao.find(id);
		if(rule.getStations()!=null&&!rule.getStations().isEmpty()){
			Assert.isTrue(stationTimeRuleDao.deleteStationByRuleID(id)>=1,"删除站点失败！");
		}
		Assert.isTrue(stationTimeRuleDao.delete(id)>=1,"删除规则失败！");
	}


	public void setStationTimeRuleDao(StationTimeRuleDao stationTimeRuleDao){
		this.stationTimeRuleDao = stationTimeRuleDao;
	}
}

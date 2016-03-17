package com.hengyu.ticket.service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.LineScheduDetailDao;
import com.hengyu.ticket.dao.LineSchedueDao;
import com.hengyu.ticket.dao.LineSchedueRuleDao;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.LineScheduDetail;
import com.hengyu.ticket.entity.LineSchedue;
import com.hengyu.ticket.entity.LineSchedueRule;
import com.hengyu.ticket.util.DateHanlder;
import com.hengyu.ticket.util.Log;
import com.hengyu.ticket.util.NumberCreate;

/**
 * 
 * @author 李冠锋 2015-11-14
 *
 */
 @Service
public class LineSchedueRuleService{
	
	@Autowired
	private LineSchedueRuleDao lineSchedueRuleDao;
	@Autowired
	private LineScheduDetailDao lsdDao;
	@Autowired
	private LineSchedueDao lsDao;
	
	/**
	 * 保存一个对象
	 * @param lineSchedueRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(LineSchedueRule lineSchedueRule,LineManage lm) throws Exception{
		Log.info(this.getClass(),"============= 保存排班规则 ");
		Integer shiftnum = lineSchedueRule.getShiftnum();
		LineScheduDetail lsd = null;
		int result = -1;
		if(lineSchedueRule.getWeekday()!=0&&lineSchedueRule.getIsshift()==1){
			result = lineSchedueRuleDao.save(lineSchedueRule);
			SimpleDateFormat tf = DateHanlder.getTimeFromat();
			//生成班次详情
			for (int i = 0; i < shiftnum; i++) {
				lsd = new LineScheduDetail();
				lsd.setLsuid(lineSchedueRule.getId());
				lsd.setShiftcode(lm.getLineid()+ NumberCreate.createNumber((i+1),2) );
				lsd.setStarttime(tf.format(
						new Date(tf.parse(lineSchedueRule.getFirststarttime()).getTime()
								+(i*60*1000*lineSchedueRule.getIntevalminute()))));
				lsdDao.save(lsd);
			}
			Log.info(this.getClass(),"============= 生成班次详情 ");
		}else if(lineSchedueRule.getIsshift()==0){
			//检查没有排班的星期是否存在
			LineSchedueRule notShiftScheduleRule = lineSchedueRuleDao.findNotShiftScheduleRule(lineSchedueRule);
			if(notShiftScheduleRule==null){
				notShiftScheduleRule = new LineSchedueRule();
				notShiftScheduleRule.setLsid(lineSchedueRule.getLsid());
				notShiftScheduleRule.setWeekday(lineSchedueRule.getWeekday());
				notShiftScheduleRule.setIsshift(0);
				result = lineSchedueRuleDao.save(notShiftScheduleRule);
			}else{
				notShiftScheduleRule.setWeekday(lineSchedueRule.getWeekday()+notShiftScheduleRule.getWeekday());
				result = lineSchedueRuleDao.update(notShiftScheduleRule);
				Log.info(this.getClass(),"============= 更新无排班 ：  "+notShiftScheduleRule.getWeekday());
			}
		}
		//自动判断排班生效
		updateEffectScuedule(lineSchedueRule);
		return result;
	}
	
	//自动激活排班
	public void updateEffectScuedule(LineSchedueRule lineSchedueRule) throws Exception{
		//查询星期是否排满
		Integer weeks = lineSchedueRuleDao.findShiftScheduleRuleWeeks(lineSchedueRule);
		if(weeks==null){
			weeks = 0;
		}
		//自动生效
		if(weeks==127){
			LineSchedue lineSchedue = lsDao.find(lineSchedueRule.getLsid());
//			lineSchedue.setIseffect(1);
			lsDao.update(lineSchedue);
			Log.info(this.getClass(),"============= 全部星期排班 ，排班生效  ");
		}else{
			LineSchedue lineSchedue = lsDao.find(lineSchedueRule.getLsid());
//			lineSchedue.setIseffect(0);
			lsDao.update(lineSchedue);
			Log.info(this.getClass(),"============= 未完成所有排班 ，排班失效  ");
		}
		Log.info(this.getClass(),"============= 排班星期总和 " + weeks);
	}
	
	/**
	 * 更新一个对象
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(LineSchedueRule lsr) throws Exception{
		Log.info(this.getClass(),"============= 更新排班规则  ");
		List<LineScheduDetail> details = lsr.getLinescheduledetail();
		if(details!=null&&lsr.getIsshift()==1){
			int d = 0;
			int u = 0;
			int s = 0;
			if(lsr.getWeekday()==0){
				Log.info(this.getClass(),"============= 没有选择排班星期，删除该排班规则  ");
				lineSchedueRuleDao.delete(lsr);
//				int del = lsdDao.deleteByLineScheduleRule(lsr.getId());
//				Log.info(this.getClass(),"============= 删除排班规则详情记录：  "+del);
			}else{
				for (LineScheduDetail detail : details) {
					if(detail.getId()!=null&&detail.getIsdel()==1){
						d+=lsdDao.delete(detail.getId());
					}else if(detail.getId()==null){
						s+=lsdDao.save(detail);
					}else if(detail.getId()!=null){
						u+=lsdDao.update(detail);
					}
				}
				Log.info(this.getClass(),"============= 排班详情 : 删除记录 ："+d+"  更新记录："+u +"  新增记录："+s);
				LineSchedueRule lsrinfo = lsdDao.findLineScheduleRuleInfo(lsr);
				lsr.setFirststarttime(lsrinfo.getFirststarttime());
				lsr.setShiftnum(lsrinfo.getShiftnum());
				lineSchedueRuleDao.update(lsr);
			}
		}else{
			lineSchedueRuleDao.update(lsr);
		}
		//自动判断排班生效
		updateEffectScuedule(lsr);
		return 1;
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回LineSchedueRule对象
	 * @throws Exception
	 */
	public LineSchedueRule find(Integer id) throws Exception{
		return lineSchedueRuleDao.find(id);
	}
	
	/**
	 * 按排班查询规则，排班的星期
	 * @return
	 * @throws Exception
	 */
	public Map<String,Integer> findweekScheduleRule(LineSchedueRule lsr) throws Exception{
		return lineSchedueRuleDao.findweekScheduleRule(lsr);
	}
	
	public void setLineSchedueRuleDao(LineSchedueRuleDao lineSchedueRuleDao){
		this.lineSchedueRuleDao = lineSchedueRuleDao;
	}
}

package com.hengyu.ticket.service;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hengyu.ticket.dao.RefundRuleDao;
import com.hengyu.ticket.dao.RefundRuleLineDao;
import com.hengyu.ticket.dao.RefundRuleTimeDao;
import com.hengyu.ticket.entity.LineManage;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.RefundRule;
import com.hengyu.ticket.entity.RefundRuleLine;
import com.hengyu.ticket.entity.RefundRuleTime;

/**
 * 
 * @author 李冠锋 2016-01-14
 *
 */
 @Service
public class RefundRuleService{
	
	@Autowired
	private RefundRuleDao refundRuleDao;
	@Autowired
	private RefundRuleTimeDao rrtdao;
	@Autowired
	private RefundRuleLineDao rrldao;
	
	/**
	 * 保存一个对象
	 * @param refundRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(RefundRule refundRule) throws Exception{
		int ret = -1;
		ret = refundRuleDao.save(refundRule);
		Assert.isTrue(ret==1);
		saveOrUpdateRetundRule(refundRule);
		return ret;
	}
	
	/**
	 * 更新一个对象
	 * @param refundRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(RefundRule refundRule) throws Exception{
		int r = -1;
		r=refundRuleDao.update(refundRule);
		Assert.isTrue(r==1);
		saveOrUpdateRetundRule(refundRule);
		return r;
	}
	
	//更新或删除时间段
	public void saveOrUpdateRetundRule(RefundRule refundRule) throws Exception{
		List<RefundRuleTime> times = refundRule.getTimes();
		if(times!=null){
			for (RefundRuleTime time : times) {
				time.setRrid(refundRule.getId());
				Assert.notNull(time.getAdvancetime(),"时间不能为空！");
				Assert.isTrue(time.getDeduction().floatValue()>=0&&time.getDeduction().floatValue()<=100,"百分比填写不正确");
				if(time.getIsdel()!=null&&time.getIsdel()==1&&time.getId()!=null){
					Assert.isTrue(rrtdao.delete(time.getId())==1);
					continue;
				}
				if(time.getId()==null){
					if(rrtdao.find(time.getId())==null){
						Assert.isTrue(rrtdao.save(time)==1);
					}
					
				}else{
					if(rrtdao.find(time.getId())!=null){
						Assert.isTrue(rrtdao.update(time)==1);
					}
				}
			}
		}
	}
	
	//绑定线路
	public int saveLines(RefundRule rule) throws Exception{
		if(rule!=null){
			List<RefundRuleLine> lines = rule.getLines();
			if(lines!=null){
				for (RefundRuleLine l : lines) {
					Assert.notNull(l.getLmid(),"线路编号不能为空");
					Assert.notNull(l.getRrid(),"规则编号不能为空");
					RefundRuleLine line = rrldao.findByLmidRrid(l.getLmid(),l.getRrid());
					if(line==null){
						Assert.isTrue(rrldao.save(l)==1,"绑定线路失败！");
					}
				}
			}
		}
		return 1;
	}
	
	//删除线路
	public int deleteline(Integer rrid,Integer lmid) throws Exception{
		Assert.isTrue(rrldao.deleteByLmidRrid(lmid,rrid)==1,"删除线路失败！");
		return 1;
	}
	
	//删除
	public int delete(Integer rrid) throws Exception{
		int r = -1;
		r = rrldao.deleteByRrid(rrid);
		r = rrtdao.deleteByRrid(rrid);
		r = refundRuleDao.delete(rrid);
		Assert.isTrue(r==1,"删除失败！");
		return r;
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回RefundRule对象
	 * @throws Exception
	 */
	public RefundRule find(Integer id) throws Exception{
		return refundRuleDao.find(id);
	}
	
	//列表
	public List<RefundRule> findlist(Page page) throws Exception{
		page.setTotalCount(refundRuleDao.count(page));
		return refundRuleDao.findlist(page);
	}
	
	@SuppressWarnings("all")
	public List<LineManage> findLineManageByRule(Page page,Integer rrid,Integer type) throws Exception{
		Map map = (Map) page.getParam();
		map.put("type", type);
		map.put("rrid", rrid);
		List<LineManage> lines = refundRuleDao.findLineManageByRule(page);
		if(type!=null&&type==1){
			page.setTotalCount(refundRuleDao.findLineManageByRuleCount(page));
		}
		return lines;
	}
	
	public void setRefundRuleDao(RefundRuleDao refundRuleDao){
		this.refundRuleDao = refundRuleDao;
	}
}

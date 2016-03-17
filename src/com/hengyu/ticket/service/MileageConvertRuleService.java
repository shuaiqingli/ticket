package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.MileageConvertRuleDao;
import com.hengyu.ticket.entity.CouponsRule;
import com.hengyu.ticket.entity.MileageConvertRule;
import com.hengyu.ticket.entity.Page;

import java.util.List;

/**
 * 
 * @author 李冠锋 2015-11-09
 *
 */
 @Service
public class MileageConvertRuleService{
	
	@Autowired
	private MileageConvertRuleDao mileageConvertRoleDao;
	
	/**
	 * 保存一个对象
	 * @param mileageConvertRole
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(MileageConvertRule mileageConvertRole) throws Exception{
		return mileageConvertRoleDao.save(mileageConvertRole);
	}
	
	/**
	 * 更新一个对象
	 * @param mileageConvertRole
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(MileageConvertRule mileageConvertRole) throws Exception{
		return mileageConvertRoleDao.update(mileageConvertRole);
	}
	
	public int delete(Integer id) throws Exception{
		return mileageConvertRoleDao.delete(id);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回MileageConvertRole对象
	 * @throws Exception
	 */
	public MileageConvertRule find(Integer id) throws Exception{
		return mileageConvertRoleDao.find(id);
	}
	
	public void setMileageConvertRoleDao(MileageConvertRuleDao mileageConvertRoleDao){
		this.mileageConvertRoleDao = mileageConvertRoleDao;
	}
	
	public List<MileageConvertRule> findList(Page page) throws Exception {
		List<MileageConvertRule> list = mileageConvertRoleDao.findList(page);
		page.setData(list);
		page.setTotalCount(mileageConvertRoleDao.totalCount(page));
		return list;
	}
}

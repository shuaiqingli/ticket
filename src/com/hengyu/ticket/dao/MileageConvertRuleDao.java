package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.CouponsRule;
import com.hengyu.ticket.entity.MileageConvertRule;
import com.hengyu.ticket.entity.Page;

import java.util.List;

/**
 * 
 * @author 李冠锋 2015-11-09
 *
 */
public interface MileageConvertRuleDao {

	/**
	 * 保存一个对象
	 * 
	 * @param mileageConvertRole
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(MileageConvertRule mileageConvertRole) throws Exception;

	/**
	 * 更新一个对象
	 * 
	 * @param mileageConvertRole
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(MileageConvertRule mileageConvertRole) throws Exception;
	
	public abstract int delete(Integer id) throws Exception;

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回MileageConvertRole对象
	 * @throws Exception
	 */
	public abstract MileageConvertRule find(Integer id) throws Exception;

	// 获取分页列表
	public abstract List<MileageConvertRule> findList(Page page) throws Exception;

	// 获取记录条数
	public abstract Long totalCount(Page page) throws Exception;

}

package com.hengyu.ticket.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hengyu.ticket.entity.CouponsRule;
import com.hengyu.ticket.entity.Page;

/**
 * 
 * @author 李冠锋 2015-11-12
 *
 */
public interface CouponsRuleDao {

	/**
	 * 保存一个对象
	 * 
	 * @param couponsRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(CouponsRule couponsRule) throws Exception;

	/**
	 * 更新一个对象
	 * 
	 * @param couponsRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(CouponsRule couponsRule) throws Exception;

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回CouponsRule对象
	 * @throws Exception
	 */
	public abstract CouponsRule find(Integer id) throws Exception;

	// 获取分页列表
	public abstract List<CouponsRule> findList(Page page) throws Exception;

	// 获取记录条数
	public abstract Long totalCount(Page page) throws Exception;

	// 获取当前送券规则--注册送
	public abstract List<CouponsRule> getCouponsRuleByRegist(Map a) throws Exception;
	
	//按类型查找优惠规则
	public abstract List<CouponsRule> findCouponRuleByType(@Param("type")Integer type) throws Exception;

	// 获取当前送券规则--满额送
	public abstract CouponsRule getCouponsRuleByBuy(Map a) throws Exception;

}

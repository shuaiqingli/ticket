package com.hengyu.ticket.dao;

import java.util.List;

import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.Promotion;
import com.hengyu.ticket.entity.TicketStore;

/**
 * 
 * @author 李冠锋 2015-11-09
 *
 */
public interface PromotionDao {

	/**
	 * 保存一个对象
	 * 
	 * @param promotion
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(Promotion promotion) throws Exception;

	/**
	 * 更新一个对象
	 * 
	 * @param promotion
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(Promotion promotion) throws Exception;

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回Promotion对象
	 * @throws Exception
	 */
	public abstract Promotion find(String id) throws Exception;

	// 获取分页列表
	public abstract List<Promotion> findList(Page page) throws Exception;

	// 获取记录条数
	public abstract Long totalCount(Page page) throws Exception;
	
	//检查优惠规则是否被占用
	public abstract Promotion checketPromotionExists(Promotion promotion) throws Exception;
	//按日期查询优惠规则
	public abstract List<Promotion> findByTicketStore(TicketStore ts) throws Exception;

}

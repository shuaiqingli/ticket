package com.hengyu.ticket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hengyu.ticket.entity.Coupons;

/**
 * 
 * @author 李冠锋 2015-11-12
 *
 */
public interface CouponsDao {
	
	/**
	 * 保存一个对象
	 * @param coupons
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(Coupons coupons) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param coupons
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(Coupons coupons) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param vouchercode 主键
	 * @return 返回Coupons对象
	 * @throws Exception
	 */
	public abstract Coupons find(String vouchercode) throws Exception;
	
	/**
	 * 优惠券列表
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	public abstract List<Coupons> findByCustomerId(String cid) throws Exception;
	
	//检查是否已送过
	public abstract Integer checkCountByVsortCID(@Param("cid")String cid,@Param("vsort")Integer vsort) throws Exception;
	
	/**
	 * 优惠券记录数
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	public abstract Integer findCountByCustomerId(String cid) throws Exception;
}

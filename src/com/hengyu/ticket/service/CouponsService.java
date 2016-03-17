package com.hengyu.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.CouponsDao;
import com.hengyu.ticket.entity.Coupons;
import com.hengyu.ticket.entity.CouponsRule;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李冠锋 2015-11-12
 *
 */
@Service
public class CouponsService {

	@Autowired
	private CouponsDao couponsDao;

	/**
	 * 保存一个对象
	 * 
	 * @param coupons
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(Coupons coupons) throws Exception {
		return couponsDao.save(coupons);
	}

	/**
	 * 更新一个对象
	 * 
	 * @param coupons
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(Coupons coupons) throws Exception {
		return couponsDao.update(coupons);
	}

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param vouchercode
	 *            主键
	 * @return 返回Coupons对象
	 * @throws Exception
	 */
	public Coupons find(String vouchercode) throws Exception {
		return couponsDao.find(vouchercode);
	}
	
	/**
	 * 优惠券列表
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	public List<Coupons> findByCustomerId(String cid) throws Exception {
		return couponsDao.findByCustomerId(cid);
	}
	/**
	 * 优惠券记录数
	 * @param cid
	 * @return
	 * @throws Exception
	 */
	public Integer findCountByCustomerId(String cid) throws Exception {
		return couponsDao.findCountByCustomerId(cid);
	}

	public void setCouponsDao(CouponsDao couponsDao) {
		this.couponsDao = couponsDao;
	}

	
}

package com.hengyu.ticket.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hengyu.ticket.dao.CouponLineDao;
import com.hengyu.ticket.dao.CouponsDao;
import com.hengyu.ticket.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.CouponsRuleDao;
import org.springframework.util.Assert;

/**
 * 
 * @author 李冠锋 2015-11-12
 *
 */
@Service
public class CouponsRuleService {

	@Autowired
	private CouponsRuleDao couponsRuleDao;
	@Autowired
	private CouponLineDao couponLineDao;
	@Autowired
	private CouponsDao couponsDao;

	/**
	 * 保存一个对象
	 * 
	 * @param couponsRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(CouponsRule couponsRule) throws Exception {
		return couponsRuleDao.save(couponsRule);
	}

	/**
	 * 更新一个对象
	 * 
	 * @param couponsRule
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(CouponsRule couponsRule) throws Exception {
		return couponsRuleDao.update(couponsRule);
	}

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回CouponsRule对象
	 * @throws Exception
	 */
	public CouponsRule find(Integer id) throws Exception {
		return couponsRuleDao.find(id);
	}

	public void setCouponsRuleDao(CouponsRuleDao couponsRuleDao) {
		this.couponsRuleDao = couponsRuleDao;
	}

	public List<CouponsRule> findList(Page page) throws Exception {
		List<CouponsRule> list = couponsRuleDao.findList(page);
		page.setData(list);
		page.setTotalCount(couponsRuleDao.totalCount(page));
		return list;
	}

	// 获取当前送券规则--注册送
	public List<CouponsRule> getCouponsRuleByRegist(Map a) throws Exception {
		return couponsRuleDao.getCouponsRuleByRegist(a);
	}

	// 获取当前送券规则--满额送
	public CouponsRule getCouponsRuleByBuy(Map a) throws Exception {
		return couponsRuleDao.getCouponsRuleByBuy(a);
	}

	public List<CouponLine> findCouponLineList(String keyword, Page page){
		page.setTotalCount(couponLineDao.findCouponLineTotal(keyword));
		return  couponLineDao.findCouponLineList(keyword, page);
	}

	public void updateForAddCoupon(Coupons coupons, Admin admin, String remark) throws Exception {
		Assert.isTrue(coupons.getVprice().compareTo(new BigDecimal(50)) <= 0, "单张代金券面值不能超过50");
		Assert.isTrue(couponLineDao.findCouponPriceTotalForCustomer(coupons.getCid()).compareTo(new BigDecimal(150).subtract(coupons.getVprice())) <= 0, "单个客户一天所送代金券面值总额不能超过150");
		Assert.isTrue(couponLineDao.findCouponPriceTotalForAdmin(admin.getUserID()).compareTo(new BigDecimal(1000).subtract(coupons.getVprice())) <=0, "单个客服一天所送代金券面值总额不能超过1000");
		CouponLine couponLine = new CouponLine();
		couponLine.setVouchercode(coupons.getVouchercode());
		couponLine.setVouchername(coupons.getVouchername());
		couponLine.setCid(coupons.getCid());
		couponLine.setCname(coupons.getCname());
		couponLine.setBegindate(coupons.getBegindate());
		couponLine.setEnddate(coupons.getEnddate());
		couponLine.setLowlimit(coupons.getLowlimit());
		couponLine.setVprice(coupons.getVprice());
		couponLine.setUserid(admin.getUserID());
		couponLine.setUsername(admin.getRealName());
		couponLine.setMakedate(coupons.getMakedate());
		couponLine.setRemark(remark);
		Assert.isTrue(couponLineDao.saveCouponLine(couponLine) == 1, "操作失败");
		Assert.isTrue(couponsDao.save(coupons) == 1, "操作失败");
	}
}

package com.hengyu.ticket.service;
import com.hengyu.ticket.dao.AdminDao;
import com.hengyu.ticket.entity.*;
import com.hengyu.ticket.util.DateUtil;
import com.hengyu.ticket.util.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hengyu.ticket.dao.CouponsDao;
import com.hengyu.ticket.dao.CouponsRuleDao;
import com.hengyu.ticket.dao.CustomerDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
 @Service
public class CustomerService{
	
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private CouponsRuleDao couponsRuleDao;
	@Autowired
	private CouponsDao coupondao;
	@Autowired
	private AdminDao adminDao;
	
	/**
	 * 保存一个对象
	 * @param customer
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(Customer customer) throws Exception{
		int result = customerDao.save(customer);
		
		try {
			// 送优惠券--注册送
			Map<String, Object> a = new HashMap<String, Object>();
			a.put("currdate", DateUtil.getCurrentDateString());
			List<CouponsRule> crs = couponsRuleDao.getCouponsRuleByRegist(a); //获取优惠券信息
			if(crs!=null){
				int i = 0;
				for (CouponsRule cr : crs) {
					if (cr != null) {
						//生成优惠券
						Coupons cp = new Coupons();
						Long tmpvc = Long.valueOf(System.currentTimeMillis()+i);
						String hexvc = Long.toHexString(tmpvc);
						cp.setVouchercode(hexvc.toUpperCase());
						cp.setVouchername(new StringBuilder().append("注册送").append(cr.getVprice()).append("优惠券").toString());
						cp.setMemo(cr.getVsortname());
						cp.setCid(customer.getCid());
						cp.setCname(customer.getCname());
						cp.setBegindate(DateUtil.getCurrentDateString());
						cp.setEnddate(DateUtil.formatDate(
								DateUtil.calculatedays(DateUtil.getCurrentDate(), cr.getValidday())));
						cp.setLowlimit(cr.getLowlimit());
						cp.setVprice(cr.getVprice());
						cp.setIsuse(1);
						cp.setIsusename("可用");
						cp.setMakedate(DateUtil.getCurrentDateTime());
						int save = coupondao.save(cp);
						Assert.isTrue(save==1,"注册失败！");
						Log.info(this.getClass(),"=========》注册送优惠劵成功！");
						i++;
					} 
				}
			}
		} catch (Exception e) {
			throw e;
		}
		
		Assert.isTrue(result == 1);
		return result;
	}
	
	/**
	 * 更新一个对象
	 * @param customer
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(Customer customer) throws Exception{
		int result = customerDao.update(customer);
		Assert.isTrue(result == 1);
		return result;
	}
	
	/**
	 * @see com.hengyu.ticket.dao.CustomerDao
	 * @param mobile 
	 * @return 返回Customer对象
	 * @throws Exception
	 */
	public Customer findByMobile(String mobile) throws Exception{
		return customerDao.findByMobile(mobile);
	}
	
	/**
	 * 返回Customer对象
	 * @param cid 
	 * @return
	 * @throws Exception
	 */
	public Customer find(String cid) throws Exception{
		return customerDao.find(cid);
	}
	
	//登录
	public Customer getCustomerLogin(String openid,String mobile,String password) throws Exception{
		 Customer login = customerDao.login(openid, mobile, password);
		 if(login!=null&&login.getOpenid()!=null){
			 if(login.getOpenid().trim().equals("null")||login.getOpenid().trim().length()<10){
				 login.setOpenid(null);
				 customerDao.update(login);
			 }
		 }
		 return login;
	}
	
	/**
	 * @see com.hengyu.ticket.dao.CustomerDao
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public Customer findByToken(String token) throws Exception{
		return customerDao.findByToken(token);
	}
	
	public void setCustomerDao(CustomerDao customerDao){
		this.customerDao = customerDao;
	}

	public List<Map> findCustomerList(Customer customer, Page page){
		page.setTotalCount(customerDao.findCustomerTotal(customer));
		return customerDao.findCustomerList(customer, page);
	}

	public void updateCustomerRank(Customer customer){
		Assert.isTrue(customerDao.updateCustomerRank(customer.getRank(), customer.getCid()) == 1, "操作失败");
	}

	public void updateCustomerForBindAdmin(String userid, String cid) throws Exception {
		Admin admin = adminDao.find(userid);
		Assert.notNull(admin, "无效管理员");
		Assert.isTrue(customerDao.bindAdminToCustomer(admin.getUserID(), admin.getRealName(), cid) == 1, "操作失败");
	}

	public List<Customer> findCustomerListForSelect(String keyword, Page page){
		page.setTotalCount(customerDao.findCustomerTotalForSelect(keyword));
		return customerDao.findCustomerListForSelect(keyword, page);
	}

	public Customer findCustomerByAdmin(String userid){
		return customerDao.findCustomerByAdmin(userid);
	}

	public Map<String, Object> findCustomerDetail(String cid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("customer", customerDao.find(cid));
		result.put("integralLinelist", customerDao.findIntegralLineList(cid));
		result.put("couponList", customerDao.findCouponList(cid));
		result.put("availableCouponTotal", coupondao.findCountByCustomerId(cid));
		return result;
	}
}

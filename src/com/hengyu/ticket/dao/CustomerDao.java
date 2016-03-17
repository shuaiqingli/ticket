package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.*;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
public interface CustomerDao {
	
	/**
	 * 保存一个对象
	 * @param customer
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(Customer customer) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param customer
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(Customer customer) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param mobile 主键
	 * @return 返回Customer对象
	 * @throws Exception
	 */
	public abstract Customer findByMobile(String mobile) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param cID 主键
	 * @return 返回Customer对象
	 * @throws Exception
	 */
	public abstract Customer find(String cid) throws Exception;
	//登录
	public abstract Customer login(@Param("openid")String openid,@Param("mobile")String mobile,@Param("password")String password) throws Exception;
	
	/**
	 * 查询秘钥查询用户
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public abstract Customer findByToken(String token) throws Exception;

	List<Map> findCustomerList(@Param("customer") Customer customer, @Param("page")Page page);

	long findCustomerTotal(@Param("customer") Customer customer);

	int updateCustomerRank(@Param("rank") Integer rank, @Param("cid") String cid);

	int bindAdminToCustomer(@Param("userid")String userid, @Param("username")String username, @Param("cid")String cid);

	Long findCustomerTotalForSelect(@Param("keyword") String keyword);

	List<Customer> findCustomerListForSelect(@Param("keyword") String keyword, @Param("page") Page page);

	Customer findCustomerByAdmin(@Param("userid") String userid);

	List<Map> findIntegralLineList(@Param("cid") String cid);

	List<Map> findCouponList(@Param("cid") String cid);
}

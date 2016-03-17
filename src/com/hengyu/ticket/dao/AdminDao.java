package com.hengyu.ticket.dao;

import java.util.List;
import java.util.Map;

import com.hengyu.ticket.entity.Driver;
import org.apache.ibatis.annotations.Param;

import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.AdminLine;
import com.hengyu.ticket.entity.Page;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
public interface AdminDao {

	/**
	 * 保存一个对象
	 * 
	 * @param admin
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(Admin admin) throws Exception;

	/**
	 * 更新一个对象
	 * 
	 * @param admin
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(Admin admin) throws Exception;

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param userID
	 *            主键
	 * @return 返回Admin对象
	 * @throws Exception
	 */
	public abstract Admin find(String userID) throws Exception;

	/**
	 * 查询
	 * 
	 * @param mobil
	 * @return
	 * @throws Exception
	 */
	public abstract Long findByMobilCheck(String mobile) throws Exception;

	/**
	 * 管理员登录
	 * 
	 * @param userID
	 * @return
	 * @throws Exception
	 */
	public abstract Admin login(Admin admin) throws Exception;

	/**
	 * 查询管理员列表
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public abstract List<Admin> findAdminList(Page page) throws Exception;

	/**
	 * 查询管理员列表，统计页数
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public abstract Long totalCount(Page page) throws Exception;

	/**
	 * 按手机号、密码查找管理员，
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public abstract Admin getAPIStationLogin(Map a) throws Exception;

	/**
	 * 设置token
	 * 
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public abstract void setToken(Map p) throws Exception;

	// 根据token查找admin
	public abstract Admin getAdminByToken(String token) throws Exception;

	// 修改密码
	public abstract int changePwd(Map a) throws Exception;

	List<Map> checkForBindStationToAdmin(@Param("userid") String userid, @Param("stationid") String stationid);

	int bindStationToAdmin(@Param("userid") String userid, @Param("stationid") String stationid,
			@Param("stationname") String stationname, @Param("cityid") String cityid);

	int unbindStationToAdmin(@Param("userid") String userid, @Param("stationid") String stationid);

	long checkForBindLineToAdmin(@Param("userid") String userid, @Param("lineid") String lineid);

	int bindLineToAdmin(@Param("userid") String userid, @Param("lineid") String lineid);

	int unbindLineToAdmin(@Param("userid") String userid, @Param("lineid") String lineid);

	int delInvalidLineList(@Param("userid") String userid);

	List<Integer> getLineidListOfStationForBindAdmin(@Param("userid") String userid,
			@Param("stationid") String stationid);

	List<Map> findAdminDataList(@Param("keyword") String keyword, @Param("page") Page page);

	long findAdminDataTotal(@Param("keyword") String keyword);

	Long findAdminTotalForBindCustomer(@Param("keyword") String keyword);

	List<Admin> findAdminListForBindCustomer(@Param("keyword") String keyword, @Param("page") Page page);

	List<Map> findShiftListForBalanceTicketWarn(@Param("lmidList") List<Integer> lmidList, @Param("shiftIDList") List<Integer> shiftIDList);
}

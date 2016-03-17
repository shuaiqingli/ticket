package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.Admin;
import com.hengyu.ticket.entity.Driver;
import com.hengyu.ticket.entity.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 李冠锋 2015-10-08
 */
public interface DriverDao {

	/**
	 * 保存一个对象
	 *
	 * @param driver
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(Driver driver) throws Exception;

	/**
	 * 更新一个对象
	 *
	 * @param driver
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(Driver driver) throws Exception;

	/**
	 * 删除
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public abstract int delete(String id) throws Exception;

	/**
	 * 根据主键查询一个对象
	 *
	 * @param id
	 *            主键
	 * @return 返回Driver对象
	 * @throws Exception
	 */
	Driver find(String id);

	/**
	 * 司机列表
	 *
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<Driver> findlist(Page page);

	/**
	 * 统计条数
	 *
	 * @param page
	 * @return
	 * @throws Exception
	 */
	Long totalCount(Page page);

	// 按关键字查询司机
	public abstract List getDriverByKeywords(Map a) throws Exception;

	int updatePassword(@Param("driverid") String driverid, @Param("password") String password);

	List<Map> findListByLineGroupID(String groupID);

	Long totalCountForBindLine(Page page);

	List<Driver> findListForBindLine(Page page);

	List<Map> findLineDataForDriverList(@Param("driveridList") List<String> driveridList);

	// 站务登陆
	public abstract Driver getAPIStationLogin(Map a) throws Exception;

	public abstract void setToken(Map p) throws Exception;

	public abstract Driver getDriverByToken(String token) throws Exception;

}

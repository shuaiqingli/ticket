package com.hengyu.ticket.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hengyu.ticket.entity.LineScheduDetail;
import com.hengyu.ticket.entity.LineSchedue;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.ShiftStart;
import com.hengyu.ticket.entity.TicketLine;

/**
 * 
 * @author 李冠锋 2015-10-10
 *
 */
public interface ShiftStartDao {

	/**
	 * 保存一个对象
	 * 
	 * @param shiftStart
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int save(ShiftStart shiftStart) throws Exception;

	/**
	 * 批量保存
	 * 
	 * @param shiftStarts
	 * @return
	 * @throws Exception
	 */
	int batchSave(List<ShiftStart> shiftStarts) throws Exception;

	/**
	 * 更新一个对象
	 * 
	 * @param shiftStart
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int update(ShiftStart shiftStart) throws Exception;

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回ShiftStart对象
	 * @throws Exception
	 */
	ShiftStart find(Integer id) throws Exception;

	List<ShiftStart> findByShiftID(@Param("shiftid") Long shiftid) throws Exception;

    //获取详情
	ShiftStart getDetail(Integer id) throws Exception;

	// 查找审核后的班次
	List<LineScheduDetail> findApprovedShiftStart(LineSchedue ls) throws Exception;

	// 查询审核过的日期
	List<String> findApprovedShiftDates(Integer lmid, String begindate, String enddate)
			throws Exception;

	// 按日期查询唯一的班次信息
	List<ShiftStart> findShiftStartBylmidDateShiftCode(@Param("lmid") Integer lmid,
			@Param("ridedate") String ridedate, @Param("shiftcode") String shiftcode) throws Exception;

	// 站务------根据日期查线路班次
	List getShiftStartByDate(Map map) throws Exception;

	Long totalCount(Map map) throws Exception;

	// 班次查询-----搜索
	List getShiftStartBySearch(Map map) throws Exception;

	// 班次查询-----正常班次搜索
	List getNormalShiftStartBySearch(Map map) throws Exception;

	Long totalCountBySearch(Map map) throws Exception;

	Long totalCountByNormalSearch(Map map) throws Exception;

	// 找全票，半票，免票，查找条件：currstationid,ridedate,shiftcode
	ShiftStart getShiftStartByCRS(Map map) throws Exception;

	// 找临时班次
	ShiftStart getShiftStartByTemp(Map map) throws Exception;

	// 发车
	int toStart(Map map) throws Exception;

	// 修改发车信息
	int modifyShiftStart(Map map) throws Exception;

	// 补齐该班次的其他发车站的信息，车牌，司机，核载,条件：班次号,线路ID,出行日期
	int completeShiftStart(Map map) throws Exception;

	// 取消、启用发班
	int cancelShiftStart(Map map) throws Exception;

    //修改发车状态
    int updateShiftStartIsCancel(@Param("shiftid") Long shiftid,@Param("id") Integer id, @Param("iscancel") Integer iscancel, @Param("iscancelname") String iscancelname, @Param("oldiscancel") Integer oldiscancel) throws Exception;
    int updateShiftStatus(@Param("shiftid") Long shiftid, @Param("status") Integer status, @Param("iscancel") Integer iscancel) throws Exception;

	// 修改取消状态名称
	int changeIsCancel(Map map) throws Exception;

	// 获取临时班次数量
	Long getTempShiftCodeCount(Map aa) throws Exception;

	// 补充备注
	int addShiftStartMemo(Map a) throws Exception;

	// 修改备注
	int updShiftStartMemo(Map a) throws Exception;

	/**
	 * 修改排班日期后，删除未审核的车票关联，发车信息
	 * 
	 * @param tl
	 * @return
	 * @throws Exception
	 */
	int delNotApproveShiftStart(TicketLine tl) throws Exception;

	// 按shiftcode,ridedate,lineid查找所有班次
	List getShiftStartBySRL(Map a) throws Exception;

	// 修改发车备注，根据ShiftCode,Ridedate修改
	int updStartMemo(Map a) throws Exception;

	// 按日期，线路编号，查询导出excel班次信息
	List<Map<String, String>> findExportExcelShiftByDate(@Param("lmid") Integer lmid,
			@Param("begindate") String begindate, @Param("enddate") String enddate) throws Exception;

	// 班次列表
	List<Map> findShiftlist(Page page) throws Exception;

	// 班次列表统计条数
	long findShiftlistCount(Page page) throws Exception;

	List<Map> getPlateListForDistributePlate(@Param("groupid") String groupid, @Param("date") String date,
			@Param("plateidList") List plateidList);

	List<Map> getPlatePosition(@Param("platenum") String platenum, @Param("date") String date);

	List<Map> getDriverListForDistributeDriver(@Param("groupid") String groupid, @Param("date") String date,
			@Param("type") String type, @Param("driveridList") List driveridList);

	List<Map> getDriverPosition(@Param("driverid") String driverid, @Param("date") String date);

	long isShiftStartExist(@Param("lmid") Integer lmid, @Param("date") String date);

	long isDriverExistForShiftStart(@Param("lmid") Integer lmid, @Param("date") String date);

	List<Map> getShiftStartListBylmidList(@Param("lmidList") Integer[] lmidList, @Param("date") String date);

	int bindDriverAndPlateToShiftStart(Map params);

	// 按司机列表发车信息,按shiftcode 分组,条件，driverid,ridedate
	List<Map> getShiftStartByDriver(Map a);

	// 按线路统计，时间范围统计人数，行李
	public List<Map<String, Object>> findStatisticShiftStartByLine(Page page) throws Exception;

	public long findStatisticCountShiftStartByLine(Page page) throws Exception;

	// 按线路、日期、当前站点查询
	public List<Object> getShiftStartDataReport(Map a) throws Exception;

	// 按线路、日期查询,按站点分组
	public List<Object> getShiftStartDataReportByDriver(Map a) throws Exception;

	//统计司机班次人数情况
	List<Map> driverStatisticList(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("keyword") String keyword, @Param("page") Page page);

	long driverStatisticTotal(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("keyword") String keyword);

	/**
	 * 找临时班次(从写了上面的getShiftStartByTemp 按照map2结构返回)
	 * @param a
	 * @return
	 */
	public abstract Map<String, Object> getMap_ShiftStartByTemp(Map<String, Object> a);

	/**
	 * 找全票，半票，免票(从写了上面的getShiftStartByCRS 按照map2结构返回)
	 * @param a
	 * @return
	 */
	public abstract Map<String, Object> getMap_ShiftStartByCRS(Map<String, Object> a);

}

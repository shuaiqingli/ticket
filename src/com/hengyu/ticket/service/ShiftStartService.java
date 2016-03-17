package com.hengyu.ticket.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.ShiftStartDao;
import com.hengyu.ticket.dao.TicketLineDao;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.ShiftStart;

/**
 * 
 * @author 李冠锋 2015-10-10
 *
 */
@Service
public class ShiftStartService {

	@Autowired
	private ShiftStartDao shiftStartDao;
	@Autowired
	private TicketLineDao tldao;

	/**
	 * 保存一个对象
	 * 
	 * @param shiftStart
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(ShiftStart shiftStart) throws Exception {
		return shiftStartDao.save(shiftStart);
	}

	/**
	 * 更新一个对象
	 * 
	 * @param shiftStart
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(ShiftStart shiftStart) throws Exception {
		return shiftStartDao.update(shiftStart);
	}

	/**
	 * 根据主键查询一个对象
	 * 
	 * @param id
	 *            主键
	 * @return 返回ShiftStart对象
	 * @throws Exception
	 */
	public ShiftStart find(Object id) throws Exception {
		return shiftStartDao.find(Integer.parseInt(id.toString()));
	}

	public void setShiftStartDao(ShiftStartDao shiftStartDao) {
		this.shiftStartDao = shiftStartDao;
	}

	// 根据日期获取线路班次
	public List getShiftStartByDate(Map map) throws Exception {
		return shiftStartDao.getShiftStartByDate(map);
	}

	public Long totalCount(Map map) throws Exception {
		return shiftStartDao.totalCount(map);
	}

	// 班次查询-----临时班次搜索
	public List getShiftStartBySearch(Map map) throws Exception {
		return shiftStartDao.getShiftStartBySearch(map);
	}

	public Long totalCountBySearch(Map map) throws Exception {
		return shiftStartDao.totalCountBySearch(map);
	}

	// 班次查询-----正常班次搜索
	public List getNormalShiftStartBySearch(Map map) throws Exception {
		return shiftStartDao.getNormalShiftStartBySearch(map);
	}

	public Long totalCountByNormalSearch(Map map) throws Exception {
		return shiftStartDao.totalCountByNormalSearch(map);
	}

	// 找全票，半票，免票，查找条件：currstationid,ridedate,shiftcode
	public ShiftStart getShiftStartByCRS(Map map) throws Exception {
		return shiftStartDao.getShiftStartByCRS(map);
	}

	// 找临时班次
	public ShiftStart getShiftStartByTemp(Map map) throws Exception {
		return shiftStartDao.getShiftStartByTemp(map);
	}

	// 发车
	public int toStart(Map map) throws Exception {
		return shiftStartDao.toStart(map);
	}

	// 修改发车信息
	public int modifyShiftStart(Map map) throws Exception {
		return shiftStartDao.modifyShiftStart(map);
	}

	// 补齐该班次的其他发车站的信息，车牌，司机，核载,条件：班次号,线路ID,出行日期
	public int completeShiftStart(Map map) throws Exception {
		return shiftStartDao.completeShiftStart(map);
	}

	// 取消、启用发班
	public int cancelShiftStart(Map map) throws Exception {
		return shiftStartDao.cancelShiftStart(map);
	}

	// 修改取消状态名称
	public int changeIsCancel(Map map) throws Exception {
		return shiftStartDao.changeIsCancel(map);
	}

	// 取消班次，并且取消车票线路
	public int cancelShiftStartTicketLine(Map map) throws Exception {
		tldao.cancelTicketLine(map);
		return shiftStartDao.cancelShiftStart(map);
	}

	// 获取临时班次数量
	public Long getTempShiftCodeCount(Map aa) throws Exception {
		return shiftStartDao.getTempShiftCodeCount(aa);
	}

	// 补充备注
	public int addShiftStartMemo(Map a) throws Exception {
		return shiftStartDao.addShiftStartMemo(a);
	}

	// 修改备注
	public int updShiftStartMemo(Map a) throws Exception{
		return shiftStartDao.updShiftStartMemo(a);
	}

	// 按shiftcode,ridedate,lineid查找所有班次
	public List getShiftStartBySRL(Map a) throws Exception {
		return shiftStartDao.getShiftStartBySRL(a);
	}

	// 修改发车备注，根据ShiftCode,Ridedate修改
	public int updStartMemo(Map a) throws Exception {
		return shiftStartDao.updStartMemo(a);
	}

	// 查询审核过的日期
	public List<String> findApproveShiftDates(Integer lmid, String begindate, String enddate) throws Exception {
		return shiftStartDao.findApprovedShiftDates(lmid, begindate, enddate);
	}

	// 按日期，线路编号，查询导出excel班次信息
	public List<Map<String, String>> findExportExcelShiftByDate(Integer lmid, String begindate, String enddate)
			throws Exception {
		return shiftStartDao.findExportExcelShiftByDate(lmid, begindate, enddate);
	}

	// 班次列表
	public void findShiftList(Page page) throws Exception {
		long count = shiftStartDao.findShiftlistCount(page);
		page.setTotalCount(count);
		page.setData(shiftStartDao.findShiftlist(page));
	}

	// 按司机列表发车信息,按shiftcode 分组,条件，driverid,ridedate
	public List<Map> getShiftStartByDriver(Map a) {
		return shiftStartDao.getShiftStartByDriver(a);
	}

	// 按线路统计，时间范围统计人数，行李
	public List<Map<String, Object>> findStatisticShiftStartByLine(Page page) throws Exception {
		page.setTotalCount(shiftStartDao.findStatisticCountShiftStartByLine(page));
		List<Map<String, Object>> list = shiftStartDao.findStatisticShiftStartByLine(page);
		page.setData(list);
		return list;
	}

	// 按线路、日期、当前站点查询
	public List<Object> getShiftStartDataReport(Map a) throws Exception {
		return shiftStartDao.getShiftStartDataReport(a);
	}

	// 按线路、日期查询,按站点分组
	public List<Object> getShiftStartDataReportByDriver(Map a) throws Exception {
		return shiftStartDao.getShiftStartDataReportByDriver(a);
	}

}

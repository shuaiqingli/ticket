package com.hengyu.ticket.dao;

import java.util.List;

import com.hengyu.ticket.entity.*;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author 李冠锋 2015-11-28
 *
 */
public interface TripPriceListDao {
	
	/**
	 * 保存一个对象
	 * @param tripPriceList
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int save(TripPriceList tripPriceList) throws Exception;
	
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	int delete(@Param("id") Integer id, @Param("lrid") Integer lrid) throws Exception;

	int deleteTripPriceSub(@Param("tplid") Integer id, @Param("lrid") Integer lrid) throws Exception;

	/**
	 * 更新一个对象
	 * @param tripPriceList
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int update(TripPriceList tripPriceList) throws Exception;

    //重置默认
	int resetDefault(@Param("lmid") Integer lmid, @Param("strid") Integer strid) throws Exception;

	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回TripPriceList对象
	 * @throws Exception
	 */
	TripPriceList find(Integer id) throws Exception;

	List<TripPriceList> findByLMID(@Param("lmid") Integer lmid) throws Exception;

    //按线路规则id查询
	List<TripPriceList> findByStrID(@Param("strid") Integer strid) throws Exception;

    //获取缺少的站点
	List<LineManageStation> getDifferStation(@Param("tplid") Integer tplid, @Param("lrid") Integer lrid) throws Exception;

	//检查是否有日期冲突
	TripPriceList findExistsDate(TripPriceList tp) throws Exception;
	
	//查询列表
	List<TripPriceList> findlist(Page page) throws Exception;
	
	//按日期查询规则，并且已经设置完所有星期
	List<TripPriceList> findByTicketStore(TicketStore ts) throws Exception;
	
	//统计条数
	long count(Page page) throws Exception;
	
	//按行程统计星期
	TripPriceList findWeekdayByTripPrice(TripPriceRule tpr) throws Exception;
	
	//是否允许修改线路
	int findisAllowUpdateLineManage(Integer lmid) throws Exception;
	
	
}

package com.hengyu.ticket.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.TripPriceListDao;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.TripPriceList;
import com.hengyu.ticket.entity.TripPriceRule;
import com.hengyu.ticket.util.Log;

/**
 * 
 * @author 李冠锋 2015-11-28
 *
 */
 @Service
public class TripPriceListService{
	
	 
	@Autowired
	private TripPriceListDao tripPriceListDao;
	
	/**
	 * 保存一个对象
	 * @param tripPriceList
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(TripPriceList tripPriceList) throws Exception{
		int ret = tripPriceListDao.save(tripPriceList);
		Log.info(this.getClass(),"======== 保存行程价格  " , ret);
		return ret;
	}
	
	/**
	 * 更新一个对象
	 * @param tripPriceList
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(TripPriceList tripPriceList) throws Exception{
		return tripPriceListDao.update(tripPriceList);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回TripPriceList对象
	 * @throws Exception
	 */
	public TripPriceList find(Integer id) throws Exception{
		return tripPriceListDao.find(id);
	}
	
	//检查是否有日期冲突
	public TripPriceList findExistsDate(TripPriceList tp) throws Exception{
		return tripPriceListDao.findExistsDate(tp);
	}
	
	//获取星期
	public TripPriceList findWeekdayByTripPrice(TripPriceRule tpr) throws Exception{
		return tripPriceListDao.findWeekdayByTripPrice(tpr);
	}
	
	//查询列表
	public List<TripPriceList> findlist(Page page) throws Exception{
		page.setTotalCount(tripPriceListDao.count(page));
		return tripPriceListDao.findlist(page);
	}
	
	//是否允许修改线路
	public int findisAllowUpdateLineManage(Integer lmid) throws Exception{
		return tripPriceListDao.findisAllowUpdateLineManage(lmid);
	}
	
	public void setTripPriceListDao(TripPriceListDao tripPriceListDao){
		this.tripPriceListDao = tripPriceListDao;
	}
	
}

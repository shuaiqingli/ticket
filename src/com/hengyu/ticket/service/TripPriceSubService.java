package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.TripPriceSubDao;
import com.hengyu.ticket.entity.TripPriceRule;
import com.hengyu.ticket.entity.TripPriceSub;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-11-14
 *
 */
 @Service
public class TripPriceSubService{
	
	@Autowired
	private TripPriceSubDao tripPriceSubDao;
	
	/**
	 * 保存一个对象
	 * @param tripPriceSub
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(TripPriceSub tripPriceSub) throws Exception{
		return tripPriceSubDao.save(tripPriceSub);
	}
	
	/**
	 * 更新一个对象
	 * @param tripPriceSub
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(TripPriceSub tripPriceSub) throws Exception{
		return tripPriceSubDao.update(tripPriceSub);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回TripPriceSub对象
	 * @throws Exception
	 */
	public TripPriceSub find(Integer id) throws Exception{
		return tripPriceSubDao.find(id);
	}
	
	/**
	 * 按规则查询详情
	 * @param tprid
	 * @return
	 * @throws Exception
	 */
	public List<TripPriceSub> findByTripPriceRule(TripPriceRule tpr) throws Exception{
		return tripPriceSubDao.findByTripPriceRule(tpr);
	}	
	
	public void setTripPriceSubDao(TripPriceSubDao tripPriceSubDao){
		this.tripPriceSubDao = tripPriceSubDao;
	}
}

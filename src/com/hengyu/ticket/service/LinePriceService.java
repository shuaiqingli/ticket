package com.hengyu.ticket.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.LinePriceDao;
import com.hengyu.ticket.entity.LinePrice;
import java.util.List;

/**
 * 
 * @author 李冠锋 2015-10-15
 *
 */
 @Service
public class LinePriceService{
	
	@Autowired
	private LinePriceDao linePriceDao;
	
	/**
	 * 保存一个对象
	 * @param linePrice
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(LinePrice linePrice) throws Exception{
		return linePriceDao.save(linePrice);
	}
	
	/**
	 * 更新一个对象
	 * @param linePrice
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(LinePrice linePrice) throws Exception{
		return linePriceDao.update(linePrice);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回LinePrice对象
	 * @throws Exception
	 */
	public LinePrice find(Integer id) throws Exception{
		return linePriceDao.find(id);
	}
	
	public void setLinePriceDao(LinePriceDao linePriceDao){
		this.linePriceDao = linePriceDao;
	}
}

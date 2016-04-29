package com.hengyu.ticket.service;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.SeatDao;
import com.hengyu.ticket.entity.Seat;

/**
 * 
 * @author 李冠锋 2015-11-14
 *
 */
 @Service
public class SeatService{
	
	@Autowired
	private SeatDao seatDao;
	
	/**
	 * 保存一个对象
	 * @param seat
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(Seat seat) throws Exception{
		return seatDao.save(seat);
	}
	
	/**
	 * 更新一个对象
	 * @param seat
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(Seat seat) throws Exception{
		return seatDao.update(seat);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回Seat对象
	 * @throws Exception
	 */
	public Seat find(Integer id) throws Exception{
		return seatDao.find(id);
	}
	
	public void setSeatDao(SeatDao seatDao){
		this.seatDao = seatDao;
	}

	public List<Seat> findSeatList(String shiftid) throws Exception {
		return seatDao.findByShiftID(shiftid);
	}
	
	public int updateUseSeat(Seat seat) throws Exception{
		return seatDao.updateUseSeat(seat);
	}
	
	//释放位置
	public int ReleaseSeat(Map d) throws Exception{
		return seatDao.ReleaseSeat(d);
	}
}

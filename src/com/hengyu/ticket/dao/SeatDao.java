package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.Seat;
import com.hengyu.ticket.entity.TicketLine;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * @author 李冠锋 2015-11-14
 *
 */
public interface SeatDao {
	
	/**
	 * 保存一个对象
	 * @param seat
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int save(Seat seat) throws Exception;
	
	//批量保存
	public abstract int batchSave(List<Seat> seat) throws Exception;
	
	/**
	 * 更新一个对象
	 * @param seat
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public abstract int update(Seat seat) throws Exception;
	
	//删除未审核的座位
	public abstract int delNotApproveSeat(TicketLine tl) throws Exception;
	
	//使用会解锁位置
	public abstract int updateUseSeat(Seat seat) throws Exception;
	
	
	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回Seat对象
	 * @throws Exception
	 */
	public abstract Seat find(Integer id) throws Exception;
	
	//查询座位
	public abstract List<Seat> findByLmidDateShiftCode(@Param("lmid")Integer lmid,@Param("ridedate")String ridedate,@Param("shiftcode")String shiftcode) throws Exception;
	
	public abstract Seat findUniqueNotSaleSeat(@Param("lmid")Integer lmid,@Param("ridedate")String ridedate,@Param("shiftcode")String shiftcode) throws Exception;
	//查询两个在一起的座位
	public abstract Seat findTowNotSaleSeat(@Param("lmid")Integer lmid,@Param("ridedate")String ridedate,@Param("shiftcode")String shiftcode) throws Exception;
	//检查是否已经排座位，统计条数 
	public abstract Long checkExistsByLmidDateShiftCode(@Param("lmid")Integer lmid,@Param("ridedate")String ridedate,@Param("shiftcode")String shiftcode) throws Exception;

	int lockSeat(@Param("seatno") Integer seatno, @Param("lmid") String lmid, @Param("shiftcode") String shiftcode, @Param("ridedate") String ridedate);

	Seat findSeat(@Param("seatno") Integer seatno, @Param("lmid") String lmid, @Param("shiftcode") String shiftcode, @Param("ridedate") String ridedate);

	public abstract int ReleaseSeat(Map d) throws Exception;

	int freezeSeat(@Param("seatno") Integer seatno, @Param("lmid") String lmid, @Param("shiftcode") String shiftcode, @Param("ridedate") String ridedate);

	int unfreezeSeat(@Param("seatno") Integer seatno, @Param("lmid") String lmid, @Param("shiftcode") String shiftcode, @Param("ridedate") String ridedate);

	int lockSeatFromFreeze(@Param("seatno") Integer seatno, @Param("lmid") String lmid, @Param("shiftcode") String shiftcode, @Param("ridedate") String ridedate);
}

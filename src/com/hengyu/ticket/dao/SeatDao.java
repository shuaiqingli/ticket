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
	int save(Seat seat) throws Exception;

	//批量保存
	int batchSave(List<Seat> seat) throws Exception;

	/**
	 * 更新一个对象
	 * @param seat
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	int update(Seat seat) throws Exception;

	//删除未审核的座位
	int delNotApproveSeat(TicketLine tl) throws Exception;

	//使用或解锁位置
	int updateUseSeat(Seat seat) throws Exception;


	/**
	 * 根据主键查询一个对象
	 * @param id 主键
	 * @return 返回Seat对象
	 * @throws Exception
	 */
	Seat find(Integer id) throws Exception;

	//查询座位
	List<Seat> findByLmidDateShiftCode(@Param("lmid")Integer lmid,@Param("ridedate")String ridedate,@Param("shiftcode")String shiftcode) throws Exception;

	List<Seat> findByShiftID(@Param("shiftid")String shiftid) throws Exception;

	Seat findUniqueNotSaleSeat(@Param("shiftid")Long shiftid) throws Exception;
	//查询两个在一起的座位
	Seat findTowNotSaleSeat(@Param("shiftid")Long shiftid) throws Exception;

    //检查是否已经排座位，统计条数
    Long checkExistsByShiftID(@Param("shiftid")Long shiftid) throws Exception;

	int lockSeat(@Param("seatno") Integer seatno, @Param("lmid") String lmid, @Param("shiftcode") String shiftcode, @Param("ridedate") String ridedate);

	Seat findSeat(@Param("seatno") Integer seatno, @Param("lmid") String lmid, @Param("shiftcode") String shiftcode, @Param("ridedate") String ridedate);

	int ReleaseSeat(Map d) throws Exception;

    int freezeSeat(@Param("seatno") Integer seatno, @Param("shiftid") String shiftid);

    int unfreezeSeat(@Param("seatno") Integer seatno, @Param("shiftid") String shiftid);

	int lockSeatFromFreeze(@Param("seatno") Integer seatno, @Param("lmid") String lmid, @Param("shiftcode") String shiftcode, @Param("ridedate") String ridedate);
}

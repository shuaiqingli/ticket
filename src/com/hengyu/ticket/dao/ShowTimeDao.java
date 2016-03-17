package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.ShowTime;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 线路显示信息DAO
 */
public interface ShowTimeDao {
    ShowTime findShowTime(@Param("id") Integer id);

    List<ShowTime> findShowTimeList(@Param("keyword") String keyword, @Param("page") Page page);

    long findShowTimeTotal(@Param("keyword") String keyword);

    int saveShowTime(ShowTime showTime);

    int updateShowTime(ShowTime showTime);

    int delShowTime(@Param("id") Integer id);

    int bindLineToShowTime(@Param("lmid") Integer lmid, @Param("stid") Integer stid);

    int unbindLineToShowTime(@Param("lmid") Integer lmid, @Param("stid") Integer stid);

    int unbindAllLineToShowTime(@Param("stid") Integer stid);

    String findShowContent(@Param("lmid") Integer lmid, @Param("ridedate") String ridedate);
}

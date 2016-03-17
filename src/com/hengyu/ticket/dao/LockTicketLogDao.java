package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.LockTicketLog;
import com.hengyu.ticket.entity.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Wang Wu on 2015-12-29.
 */
public interface LockTicketLogDao {

    int saveLockTicketLog(LockTicketLog lockTicketLog);

    List<LockTicketLog> findLockTicketLogList(@Param("keyword") String keyword, @Param("type") Integer type, @Param("ridedate") String ridedate, @Param("makedate") String makedate, @Param("page") Page page);

    long findLockTicketLogTotal(@Param("keyword") String keyword, @Param("type") Integer type, @Param("ridedate") String ridedate, @Param("makedate") String makedate);
}

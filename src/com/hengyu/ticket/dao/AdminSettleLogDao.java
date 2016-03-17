package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.AdminSettleLog;
import com.hengyu.ticket.entity.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Wang Wu on 2015-12-29.
 */
public interface AdminSettleLogDao {

    int saveAdminSettleLog(AdminSettleLog adminSettleLog);

    List<AdminSettleLog> findAdminSettleLogList(@Param("keyword") String keyword, @Param("page") Page page);

    long findAdminSettleLogTotal(@Param("keyword") String keyword);
}

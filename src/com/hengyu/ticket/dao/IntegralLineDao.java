package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.IntegralLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Wang Wu on 2015-12-29.
 */
public interface IntegralLineDao {

    int saveIntegralLine(IntegralLine integralLine);

    List<Map> integralProductStatisticForValue(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<Map> integralProductStatisticForCustomer(@Param("startDate") String startDate, @Param("endDate") String endDate);

    List<Map> integralProductStatisticForExport(@Param("startDate") String startDate, @Param("endDate") String endDate);
}

package com.hengyu.ticket.dao;

import com.hengyu.ticket.entity.DriverHoliday;
import com.hengyu.ticket.entity.Page;

import java.util.List;

/**
 * Created by Wang Wu on 2015-12-15.
 */
public interface DriverHolidayDao {

    Long totalCount(Page page);

    List<DriverHoliday> findList(Page page);

    DriverHoliday find(Integer id);

    int save(DriverHoliday driverHoliday);

    int update(DriverHoliday driverHoliday);

    int delete(Integer id);

}

package com.hengyu.ticket.service;

import com.hengyu.ticket.dao.DriverHolidayDao;
import com.hengyu.ticket.entity.DriverHoliday;
import com.hengyu.ticket.entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by Wang Wu on 2015-12-15.
 */
@Service
public class DriverHolidayService {

    @Autowired
    private DriverHolidayDao driverHolidayDao;

    public List<DriverHoliday> findList(Page page) {
        Long totalCount = driverHolidayDao.totalCount(page);
        page.setTotalCount(totalCount);
        return driverHolidayDao.findList(page);
    }

    public DriverHoliday find(Integer id) {
        return driverHolidayDao.find(id);
    }

    public void save(DriverHoliday driverHoliday){
        Assert.isTrue(driverHolidayDao.save(driverHoliday) == 1, "操作失败");
    }

    public void update(DriverHoliday driverHoliday){
        Assert.isTrue(driverHolidayDao.update(driverHoliday) == 1, "操作失败");
    }

    public void delete(Integer id){
        Assert.isTrue(driverHolidayDao.delete(id) == 1, "操作失败");
    }
}

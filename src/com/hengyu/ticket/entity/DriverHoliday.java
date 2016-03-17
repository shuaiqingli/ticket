package com.hengyu.ticket.entity;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created by Wang Wu on 2015-12-15.
 */
public class DriverHoliday implements Serializable{

    private Integer id;
    private String driverid;
    private String type;//1.按周 2.按固定时间
    private Integer weekday;//可选星期1,2,4,8,16,32,64
    private String startdate;
    private String enddate;
    private String makedate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public String getWeekdayStr(){
        if(null == weekday) return null;
        StringBuilder result = new StringBuilder();
        result.append((weekday&1) > 0 ? "1" : "0");
        result.append((weekday&2) > 0 ? "1" : "0");
        result.append((weekday&4) > 0 ? "1" : "0");
        result.append((weekday&8) > 0 ? "1" : "0");
        result.append((weekday&16) > 0 ? "1" : "0");
        result.append((weekday&32) > 0 ? "1" : "0");
        result.append((weekday&64) > 0 ? "1" : "0");
        return result.toString();
    }
}

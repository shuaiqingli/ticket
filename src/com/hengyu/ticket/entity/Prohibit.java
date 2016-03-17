package com.hengyu.ticket.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Wang Wu on 2016-01-11.
 */
public class Prohibit implements Serializable {

    private List<Map> prohibitTimeList;
    private List<Map> prohibitLineList;

    private Integer id;
    private String stid;
    private String begindate;
    private String enddate;
    private Integer weekdays;
    private String makeid;
    private String makename;
    private String makedate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStid() {
        return stid;
    }

    public void setStid(String stid) {
        this.stid = stid;
    }

    public String getBegindate() {
        return begindate;
    }

    public void setBegindate(String begindate) {
        this.begindate = begindate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public Integer getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(Integer weekdays) {
        this.weekdays = weekdays;
    }

    public String getMakeid() {
        return makeid;
    }

    public void setMakeid(String makeid) {
        this.makeid = makeid;
    }

    public String getMakename() {
        return makename;
    }

    public void setMakename(String makename) {
        this.makename = makename;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public List<Map> getProhibitTimeList() {
        return prohibitTimeList;
    }

    public void setProhibitTimeList(List<Map> prohibitTimeList) {
        this.prohibitTimeList = prohibitTimeList;
    }

    public List<Map> getProhibitLineList() {
        return prohibitLineList;
    }

    public void setProhibitLineList(List<Map> prohibitLineList) {
        this.prohibitLineList = prohibitLineList;
    }

    public String getWeekdayStr() {
        if (null == weekdays) return null;
        return ((weekdays & 1) > 0 ? "1" : "0") +
                ((weekdays & 2) > 0 ? "1" : "0") +
                ((weekdays & 4) > 0 ? "1" : "0") +
                ((weekdays & 8) > 0 ? "1" : "0") +
                ((weekdays & 16) > 0 ? "1" : "0") +
                ((weekdays & 32) > 0 ? "1" : "0") +
                ((weekdays & 64) > 0 ? "1" : "0");
    }
}

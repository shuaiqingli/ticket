package com.hengyu.ticket.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Wang Wu on 2016-03-01.
 */
public class IntegralRule implements Serializable {

    private List<Map> lineMapList;
    private String lineidlist;

    private Integer id;
    private String begindate;
    private String enddate;
    private Integer weekdays;
    private Float multiple;
    private String makeid;
    private String makename;
    private String makedate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Float getMultiple() {
        return multiple;
    }

    public void setMultiple(Float multiple) {
        this.multiple = multiple;
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

    public List<Map> getLineMapList() {
        return lineMapList;
    }

    public void setLineMapList(List<Map> lineMapList) {
        this.lineMapList = lineMapList;
    }

    public String getWeekdayStr(){
        if(null == weekdays) return null;
        return ((weekdays & 1) > 0 ? "1" : "0") +
                ((weekdays & 2) > 0 ? "1" : "0") +
                ((weekdays & 4) > 0 ? "1" : "0") +
                ((weekdays & 8) > 0 ? "1" : "0") +
                ((weekdays & 16) > 0 ? "1" : "0") +
                ((weekdays & 32) > 0 ? "1" : "0") +
                ((weekdays & 64) > 0 ? "1" : "0");
    }

    public String getLineidlist() {
        return lineidlist;
    }

    public void setLineidlist(String lineidlist) {
        this.lineidlist = lineidlist;
    }
}

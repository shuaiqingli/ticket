package com.hengyu.ticket.entity;

import java.util.List;

/**
 * @author 李冠锋 2016-03-11
 */
public class StationTimeRule {

    //------------------字段-----------------

    private Integer id;
    private Integer lmid;
    private String rulename;
    private Integer groupid;
    private String begintime;
    private String endtime;
    private Integer isdefault;

    private List<StationTimeRule> rules;

    private List<LineManageStation> stations;

    //状态
    private Integer status;


    //----------------构造方法----------------

    public StationTimeRule() {

    }

    //-------------- get/set方法 --------------


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<StationTimeRule> getRules() {
        return rules;
    }

    public void setRules(List<StationTimeRule> rules) {
        this.rules = rules;
    }

    public List<LineManageStation> getStations() {
        return stations;
    }

    public void setStations(List<LineManageStation> stations) {
        this.stations = stations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLmid() {
        return lmid;
    }

    public void setLmid(Integer lmid) {
        this.lmid = lmid;
    }

    public String getRulename() {
        return rulename;
    }

    public void setRulename(String rulename) {
        this.rulename = rulename;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Integer isdefault) {
        this.isdefault = isdefault;
    }
}
package com.hengyu.ticket.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.hengyu.ticket.util.DateHanlder;


/**
 * @author 李冠锋 2015-11-14
 */
public class LineSchedue {

    //------------------字段-----------------

    //编号
    private Integer id;
    //线路编号
    private Integer lmid;
    //排班名称
    private String scheduname;

    private Integer isdel;

    private Integer isdefault;

    //规则详情
    private List<LineScheduDetail> schedules;


    //----------------构造方法----------------

    public LineSchedue() {

    }

    //-------------- get/set方法 --------------


    public Integer getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Integer isdefault) {
        this.isdefault = isdefault;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
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

    public String getScheduname() {
        return scheduname;
    }

    public void setScheduname(String scheduname) {
        this.scheduname = scheduname;
    }

    public List<LineScheduDetail> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<LineScheduDetail> schedules) {
        this.schedules = schedules;
    }
}
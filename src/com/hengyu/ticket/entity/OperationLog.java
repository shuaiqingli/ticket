package com.hengyu.ticket.entity;

import java.util.Date;

/**
 * @author 李冠锋 2016-02-15
 */
public class OperationLog {

    //------------------字段-----------------

    private Integer id;
    private String userid;
    private String username;
    private String module;
    private String ip;
    private String operation;
    private String remark;
    private Date makedate;

    //----------------构造方法----------------

    public OperationLog() {

    }

    //-------------- get/set方法 --------------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getMakedate() {
        return makedate;
    }

    public void setMakedate(Date makedate) {
        this.makedate = makedate;
    }
}
package com.hengyu.ticket.entity;

/**
 * @author 李冠锋 2015-11-14
 */
public class LineScheduDetail {

    //------------------字段-----------------

    private Integer id;
    private Integer lsuid;
    private String shiftcode;
    private String starttime;

    private Integer isdel;

    //----------------构造方法----------------

    public LineScheduDetail() {

    }

    //-------------- get/set方法 --------------

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLsuid() {
        return lsuid;
    }

    public void setLsuid(Integer lsuid) {
        this.lsuid = lsuid;
    }

    public String getShiftcode() {
        return shiftcode;
    }

    public void setShiftcode(String shiftcode) {
        this.shiftcode = shiftcode;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

}
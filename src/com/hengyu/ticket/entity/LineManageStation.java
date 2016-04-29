package com.hengyu.ticket.entity;

/**
 * @author 李冠锋 2016-03-02
 */
public class LineManageStation {

    //------------------字段-----------------

    private Integer id;
    //线路编号
    private Integer lmid;
    //属性0出发站1到达站
    private Integer sort;
    //属性名称
    private String sortname;
    private String stid;
    //站点拼音
    private String stpinyin;
    //站点名称
    private String stname;
    private Integer sortnum;
    private Integer requiretime;
    private Integer submileage;
    //线路时间规则id
    private Integer strid;
    //标识是否删除
    private Integer isdel;

    //----------------构造方法----------------

    public LineManageStation() {

    }

    //-------------- get/set方法 --------------


    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public Integer getStrid() {
        return strid;
    }

    public void setStrid(Integer strid) {
        this.strid = strid;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getSortname() {
        return sortname;
    }

    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    public String getStid() {
        return stid;
    }

    public void setStid(String stid) {
        this.stid = stid;
    }

    public String getStpinyin() {
        return stpinyin;
    }

    public void setStpinyin(String stpinyin) {
        this.stpinyin = stpinyin;
    }

    public String getStname() {
        return stname;
    }

    public void setStname(String stname) {
        this.stname = stname;
    }

    public Integer getSortnum() {
        return sortnum;
    }

    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    public Integer getRequiretime() {
        return requiretime;
    }

    public void setRequiretime(Integer requiretime) {
        this.requiretime = requiretime;
    }

    public Integer getSubmileage() {
        return submileage;
    }

    public void setSubmileage(Integer submileage) {
        this.submileage = submileage;
    }
}
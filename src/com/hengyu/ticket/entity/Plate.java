package com.hengyu.ticket.entity;

/**
 * @author 李冠锋 2015-10-08
 */
public class Plate {

    //------------------字段-----------------
    //编号
    private Integer id;
    //车牌号
    private String platenum;
    //核载
    private Integer nuclearload;

    private Integer tcid;
    private String companyname;


    //车型
    private Integer carmodelid;
    private String carmodelname;


    //----------------构造方法----------------

    public Plate() {

    }

    //-------------- get/set方法 --------------


    public Integer getTcid() {
        return tcid;
    }

    public void setTcid(Integer tcid) {
        this.tcid = tcid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    /**
     * 获取id
     *
     * @return Integer
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取platenum
     *
     * @return String
     */
    public String getPlatenum() {
        return platenum;
    }

    /**
     * 设置platenum
     *
     * @param platenum
     */
    public void setPlatenum(String platenum) {
        this.platenum = platenum;
    }

    /**
     * 获取nuclearload
     *
     * @return Integer
     */
    public Integer getNuclearload() {
        return nuclearload;
    }

    /**
     * 设置nuclearload
     *
     * @param nuclearload
     */
    public void setNuclearload(Integer nuclearload) {
        this.nuclearload = nuclearload;
    }

    public Integer getCarmodelid() {
        return carmodelid;
    }

    public void setCarmodelid(Integer carmodelid) {
        this.carmodelid = carmodelid;
    }

    public String getCarmodelname() {
        return carmodelname;
    }

    public void setCarmodelname(String carmodelname) {
        this.carmodelname = carmodelname;
    }
}
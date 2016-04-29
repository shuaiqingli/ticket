package com.hengyu.ticket.entity;

/**
 * @author 李冠锋 2015-09-26
 */
public class CityStation {

    //------------------字段-----------------

    //编号
    private String id;
    //城市名称
    private String cityname;
    //父编号
    private String parentid;
    //优先级
    private Integer rank;
    //是否热门
    private Integer ishot;
    //父城市
    private CityStation parent;
    //拼音
    private String stpinyin;
    //站点地址
    private String stationaddr;
    //取票地址
    private String ticketaddr;
    //取票示意图
    private String ticketaddrpicture;

    private Integer ticketpercent;
    private Integer couponticketpercent;
    private Integer couponpercent;

    //0始发城市站点，1到达城市站点
    private Integer sort;
    //站点简称
    private  String shortname;

    //----------------构造方法----------------

    public CityStation() {

    }

    //-------------- get/set方法 --------------


    public String getId() {
        return id;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getIshot() {
        return ishot;
    }

    public void setIshot(Integer ishot) {
        this.ishot = ishot;
    }

    public CityStation getParent() {
        return parent;
    }

    public void setParent(CityStation parent) {
        this.parent = parent;
    }

    public String getStpinyin() {
        return stpinyin;
    }

    public void setStpinyin(String stpinyin) {
        this.stpinyin = stpinyin;
    }

    public String getStationaddr() {
        return stationaddr;
    }

    public void setStationaddr(String stationaddr) {
        this.stationaddr = stationaddr;
    }

    public String getTicketaddr() {
        return ticketaddr;
    }

    public void setTicketaddr(String ticketaddr) {
        this.ticketaddr = ticketaddr;
    }

    public Integer getTicketpercent() {
        return ticketpercent;
    }

    public void setTicketpercent(Integer ticketpercent) {
        this.ticketpercent = ticketpercent;
    }

    public Integer getCouponticketpercent() {
        return couponticketpercent;
    }

    public void setCouponticketpercent(Integer couponticketpercent) {
        this.couponticketpercent = couponticketpercent;
    }

    public Integer getCouponpercent() {
        return couponpercent;
    }

    public void setCouponpercent(Integer couponpercent) {
        this.couponpercent = couponpercent;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getTicketaddrpicture() {
        return ticketaddrpicture;
    }

    public void setTicketaddrpicture(String ticketaddrpicture) {
        this.ticketaddrpicture = ticketaddrpicture;
    }
}
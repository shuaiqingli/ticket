package com.hengyu.ticket.entity;

import java.math.BigDecimal;


/**
 * @author 李冠锋 2015-10-10
 */
public class TicketLine {

    //------------------字段-----------------

    //编号
    private Integer id;
    //线路编号
    private Integer lmid;
    //班次号
    private Long shiftid;
    //余票
    private Integer balancequantity;
    //总票
    private Integer allquantity;
    //优惠票
    private Integer couponquantity;
    //优惠余票
    private Integer balancecouponquantity;
    //状态
    private Integer status;
    //线路名称
    private String linename;
    //班次编号
    private String shiftcode;
    //始发站城市编号
    private String citystartid;
    //(班次首发车时间)起始时间
    private String originstarttime;
    //始发站编号
    private String ststartid;
    //始发站名称
    private String ststartname;
    //到达城市编号
    private String cityarriveid;
    //到达站点编号
    private String starriveid;
    //到达站点名称
    private String starrivename;
    //发车时间
    private String starttime;
    //到达时间
    private String arrivetime;
    //票价
    private BigDecimal ticketprice;
    //运输公司
    private String transcompany;
    //票面日期
    private String ticketdate;
    //制单日期
    private String makedate;
    //票库
    private TicketStore ticketStore;
    //审核
    private Integer isapprove = 0;
    //里程
    private Integer mileage;
    //FavPrice 优惠价
    private BigDecimal favprice;

    //总票
    private Integer salequantity;
    //优惠票
    private Integer couponsalequantity;
    //时间段出票百分比
    private Integer couponticketpercent;
    //站点票库id
    private Integer tlsid;

    //查询条件字段
    private String time;
    private int istoday = 0;
    private String ordertime;
    private Integer iscustomer = 1;
    private Integer ordertype;//默认时间正序,1.时间正序 2.时间逆序 3.价格正序 4.价格逆序

    private BigDecimal promotionprice;


    //----------------构造方法----------------

    public TicketLine() {

    }

    //-------------- get/set方法 --------------


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

    public Long getShiftid() {
        return shiftid;
    }

    public void setShiftid(Long shiftid) {
        this.shiftid = shiftid;
    }

    public String getLinename() {
        return linename;
    }

    public void setLinename(String linename) {
        this.linename = linename;
    }

    public String getShiftcode() {
        return shiftcode;
    }

    public void setShiftcode(String shiftcode) {
        this.shiftcode = shiftcode;
    }

    public String getCitystartid() {
        return citystartid;
    }

    public void setCitystartid(String citystartid) {
        this.citystartid = citystartid;
    }

    public String getOriginstarttime() {
        return originstarttime;
    }

    public void setOriginstarttime(String originstarttime) {
        this.originstarttime = originstarttime;
    }

    public String getStstartid() {
        return ststartid;
    }

    public void setStstartid(String ststartid) {
        this.ststartid = ststartid;
    }

    public String getStstartname() {
        return ststartname;
    }

    public void setStstartname(String ststartname) {
        this.ststartname = ststartname;
    }

    public String getCityarriveid() {
        return cityarriveid;
    }

    public void setCityarriveid(String cityarriveid) {
        this.cityarriveid = cityarriveid;
    }

    public String getStarriveid() {
        return starriveid;
    }

    public void setStarriveid(String starriveid) {
        this.starriveid = starriveid;
    }

    public String getStarrivename() {
        return starrivename;
    }

    public void setStarrivename(String starrivename) {
        this.starrivename = starrivename;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getArrivetime() {
        return arrivetime;
    }

    public void setArrivetime(String arrivetime) {
        this.arrivetime = arrivetime;
    }

    public BigDecimal getTicketprice() {
        return ticketprice;
    }

    public void setTicketprice(BigDecimal ticketprice) {
        this.ticketprice = ticketprice;
    }

    public String getTranscompany() {
        return transcompany;
    }

    public void setTranscompany(String transcompany) {
        this.transcompany = transcompany;
    }

    public String getTicketdate() {
        return ticketdate;
    }

    public void setTicketdate(String ticketdate) {
        this.ticketdate = ticketdate;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public TicketStore getTicketStore() {
        return ticketStore;
    }

    public void setTicketStore(TicketStore ticketStore) {
        this.ticketStore = ticketStore;
    }

    public Integer getIsapprove() {
        return isapprove;
    }

    public void setIsapprove(Integer isapprove) {
        this.isapprove = isapprove;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getFavprice() {
        return favprice;
    }

    public void setFavprice(BigDecimal favprice) {
        this.favprice = favprice;
    }

    public Integer getSalequantity() {
        return salequantity;
    }

    public void setSalequantity(Integer salequantity) {
        this.salequantity = salequantity;
    }

    public Integer getCouponsalequantity() {
        return couponsalequantity;
    }

    public void setCouponsalequantity(Integer couponsalequantity) {
        this.couponsalequantity = couponsalequantity;
    }

    public Integer getCouponticketpercent() {
        return couponticketpercent;
    }

    public void setCouponticketpercent(Integer couponticketpercent) {
        this.couponticketpercent = couponticketpercent;
    }

    public Integer getTlsid() {
        return tlsid;
    }

    public void setTlsid(Integer tlsid) {
        this.tlsid = tlsid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIstoday() {
        return istoday;
    }

    public void setIstoday(int istoday) {
        this.istoday = istoday;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public Integer getIscustomer() {
        return iscustomer;
    }

    public void setIscustomer(Integer iscustomer) {
        this.iscustomer = iscustomer;
    }

    public BigDecimal getPromotionprice() {
        return promotionprice;
    }

    public void setPromotionprice(BigDecimal promotionprice) {
        this.promotionprice = promotionprice;
    }

    public Integer getBalancequantity() {
        return balancequantity;
    }

    public void setBalancequantity(Integer balancequantity) {
        this.balancequantity = balancequantity;
    }

    public Integer getAllquantity() {
        return allquantity;
    }

    public void setAllquantity(Integer allquantity) {
        this.allquantity = allquantity;
    }

    public Integer getCouponquantity() {
        return couponquantity;
    }

    public void setCouponquantity(Integer couponquantity) {
        this.couponquantity = couponquantity;
    }

    public Integer getBalancecouponquantity() {
        return balancecouponquantity;
    }

    public void setBalancecouponquantity(Integer balancecouponquantity) {
        this.balancecouponquantity = balancecouponquantity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(Integer ordertype) {
        this.ordertype = ordertype;
    }
}
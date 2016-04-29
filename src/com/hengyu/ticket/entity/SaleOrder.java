package com.hengyu.ticket.entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hengyu.ticket.service.SaleOrderTicketService;

/**
 * @author 李冠锋 2015-09-26
 */
public class SaleOrder {

    // ------------------字段-----------------

    private Integer ticketlineid;
    // 编号
    private String id;
    //班次号
    private Long shiftid;
    // 取票码
    private String ticketcode;
    // 客户编号
    private String cid;
    // 客户姓名
    private String cname;
    // 客户手机号码
    private String mobile;
    // 出发日期
    private String ridedate;
    // 线路编号
    private Integer lmid;
    // 线路名称
    private String linename;
    // 出发站点编号
    private String ststartid;
    // 出发站点名称
    private String ststartname;
    // 到达站点编号
    private String starriveid;
    // 到达站点名称
    private String starrivename;
    // 发车时间
    private String starttime;
    // 到达时间
    private String arrivetime;
    // 单价
    private BigDecimal price;
    // 数量
    private Integer quantity;
    // 订单总价
    private BigDecimal totalsum;
    // 优惠券编号
    private String coupons;
    // 优惠金额
    private BigDecimal couponssum;
    // 实付金额
    private BigDecimal actualsum;
    // 联系人姓名
    private String lname;
    // 联系人手机号码
    private String lmobile;
    // 联系人身份证
    private String idcode;
    // 支付方式 Ali,wx，bal（余额）
    private String paymode;
    // 余额支付
    private BigDecimal balancepay;
    // 其他支付
    private BigDecimal otherpay;
    // 支付状态
    private Integer paystatus;
    // 支付状态名称
    private String paystatusname;
    // 订单状态0未取票1已取票2退款中3已退款4.已取消
    private Integer status;
    // 状态名称
    private String statusname;
    // 订单日期
    private String makedate;
    // 下单方式，wx，android，ios
    private String buyway;
    // 线路编号
    private String shiftcode;
    // 外部订单号
    private String outcode;
    // 微信支付加密数据
    private String payfeedback;
    // 二维码路径
    private String codeurl;
    //优惠票
    private Integer discountquantity;
    //优惠单价
    private BigDecimal vprice;
    //级别
    private Integer rank;
    //站务结算
    private Integer issett;

    // IsAbnormal,GetWay,SID,SName,TakeDate

    private Integer isabnormal = 0;
    private String memo;
    private Integer getway = 1;
    private String sid;
    private String sname;
    private String takedate;

    //里程
    private Integer mileage;
    //座位号
    private String seatnos;
    //子订单
    private List<SaleOrderTicket> sots;

    //查询条件
    private String begindate;
    private String enddate;

    //取票地址
    private String stationaddr;
    private String ticketaddr;

    //提示内容
    private String tipcontent;
    //结算积分
    private Integer isintegralsett;

    //始发时间
    private String originstarttime;
    //订单总数
    private Integer ordercount;
    //退票数量
    private Integer refundcount;
    //客户注册日期
    private String customermakedate;

    // ----------------构造方法----------------

    public SaleOrder() {

    }

    // -------------- get/set方法 --------------


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketcode() {
        return ticketcode;
    }

    public void setTicketcode(String ticketcode) {
        this.ticketcode = ticketcode;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRidedate() {
        return ridedate;
    }

    public void setRidedate(String ridedate) {
        this.ridedate = ridedate;
    }

    public Integer getLmid() {
        return lmid;
    }

    public void setLmid(Integer lmid) {
        this.lmid = lmid;
    }

    public String getLinename() {
        return linename;
    }

    public void setLinename(String linename) {
        this.linename = linename;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalsum() {
        return totalsum;
    }

    public void setTotalsum(BigDecimal totalsum) {
        this.totalsum = totalsum;
    }

    public String getCoupons() {
        return coupons;
    }

    public void setCoupons(String coupons) {
        this.coupons = coupons;
    }

    public BigDecimal getCouponssum() {
        return couponssum;
    }

    public void setCouponssum(BigDecimal couponssum) {
        this.couponssum = couponssum;
    }

    public BigDecimal getActualsum() {
        return actualsum;
    }

    public void setActualsum(BigDecimal actualsum) {
        this.actualsum = actualsum;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getLmobile() {
        return lmobile;
    }

    public void setLmobile(String lmobile) {
        this.lmobile = lmobile;
    }

    public String getIdcode() {
        return idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public String getPaymode() {
        return paymode;
    }

    public void setPaymode(String paymode) {
        this.paymode = paymode;
    }

    public BigDecimal getBalancepay() {
        return balancepay;
    }

    public void setBalancepay(BigDecimal balancepay) {
        this.balancepay = balancepay;
    }

    public Long getShiftid() {
        return shiftid;
    }

    public void setShiftid(Long shiftid) {
        this.shiftid = shiftid;
    }

    public BigDecimal getOtherpay() {
        return otherpay;
    }

    public void setOtherpay(BigDecimal otherpay) {
        this.otherpay = otherpay;
    }

    public Integer getPaystatus() {
        return paystatus;
    }

    public void setPaystatus(Integer paystatus) {
        this.paystatus = paystatus;
    }

    public String getPaystatusname() {
        return paystatusname;
    }

    public void setPaystatusname(String paystatusname) {
        this.paystatusname = paystatusname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusname() {
        return statusname;
    }

    public void setStatusname(String statusname) {
        this.statusname = statusname;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public String getBuyway() {
        return buyway;
    }

    public void setBuyway(String buyway) {
        this.buyway = buyway;
    }

    public String getShiftcode() {
        return shiftcode;
    }

    public void setShiftcode(String shiftcode) {
        this.shiftcode = shiftcode;
    }

    public String getOutcode() {
        return outcode;
    }

    public void setOutcode(String outcode) {
        this.outcode = outcode;
    }

    public String getPayfeedback() {
        return payfeedback;
    }

    public void setPayfeedback(String payfeedback) {
        this.payfeedback = payfeedback;
    }

    public String getCodeurl() {
        return codeurl;
    }

    public void setCodeurl(String codeurl) {
        this.codeurl = codeurl;
    }

    public Integer getDiscountquantity() {
        return discountquantity;
    }

    public void setDiscountquantity(Integer discountquantity) {
        this.discountquantity = discountquantity;
    }

    public BigDecimal getVprice() {
        return vprice;
    }

    public void setVprice(BigDecimal vprice) {
        this.vprice = vprice;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getIssett() {
        return issett;
    }

    public void setIssett(Integer issett) {
        this.issett = issett;
    }

    public Integer getIsabnormal() {
        return isabnormal;
    }

    public void setIsabnormal(Integer isabnormal) {
        this.isabnormal = isabnormal;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getGetway() {
        return getway;
    }

    public void setGetway(Integer getway) {
        this.getway = getway;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getTakedate() {
        return takedate;
    }

    public void setTakedate(String takedate) {
        this.takedate = takedate;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getSeatnos() {
        return seatnos;
    }

    public void setSeatnos(String seatnos) {
        this.seatnos = seatnos;
    }

    public List<SaleOrderTicket> getSots() {
        return sots;
    }

    public void setSots(List<SaleOrderTicket> sots) {
        this.sots = sots;
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

    public String getTipcontent() {
        return tipcontent;
    }

    public void setTipcontent(String tipcontent) {
        this.tipcontent = tipcontent;
    }

    public Integer getIsintegralsett() {
        return isintegralsett;
    }

    public void setIsintegralsett(Integer isintegralsett) {
        this.isintegralsett = isintegralsett;
    }

    public String getOriginstarttime() {
        return originstarttime;
    }

    public void setOriginstarttime(String originstarttime) {
        this.originstarttime = originstarttime;
    }

    public int getDaysForAdvance() throws ParseException {
        if(makedate == null || ridedate == null) return 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date makeDateTemp = sdf.parse(makedate.substring(0, 10));
        Date rideDateTemp = sdf.parse(ridedate.substring(0, 10));
        Calendar cal = Calendar.getInstance();
        cal.setTime(makeDateTemp);
        long makeDateTime = cal.getTimeInMillis();
        cal.setTime(rideDateTemp);
        long rideDateTime = cal.getTimeInMillis();
        return Integer.parseInt(String.valueOf((rideDateTime-makeDateTime)/(1000*3600*24)));
    }

    public Integer getOrdercount() {
        if(ordercount!=null&&ordercount<=0){
            return null;
        }
        return ordercount;
    }

    public void setOrdercount(Integer ordercount) {
        this.ordercount = ordercount;
    }

    public String getCustomermakedate() {
        return customermakedate;
    }

    public void setCustomermakedate(String customermakedate) {
        this.customermakedate = customermakedate;
    }

    public Integer getTicketlineid() {
        return ticketlineid;
    }

    public void setTicketlineid(Integer ticketlineid) {
        this.ticketlineid = ticketlineid;
    }

    public Integer getRefundcount() {
        return refundcount;
    }

    public void setRefundcount(Integer refundcount) {
        this.refundcount = refundcount;
    }
}
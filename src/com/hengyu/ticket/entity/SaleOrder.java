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

    // 编号
    private String id;
    // 取票码
    private String ticketCode;
    // 客户编号
    private String cID;
    // 客户姓名
    private String cName;
    // 客户手机号码
    private String mobile;
    // 出发日期
    private String rideDate;
    // 线路编号
    private Integer lmid;
    // 线路名称
    private String lineName;
    // 出发站点编号
    private String sTStartID;
    // 出发站点名称
    private String sTStartName;
    // 到达站点编号
    private String sTArriveID;
    // 到达站点名称
    private String sTArriveName;
    // 发车时间
    private String startTime;
    // 到达时间
    private String arriveTime;
    // 单价
    private BigDecimal price;
    // 数量
    private Integer quantity;
    // 订单总价
    private BigDecimal totalSum;
    // 优惠券编号
    private String coupons;
    // 优惠金额
    private BigDecimal couponsSum;
    // 实付金额
    private BigDecimal actualSum;
    // 联系人姓名
    private String lName;
    // 联系人手机号码
    private String lMobile;
    // 联系人身份证
    private String iDCode;
    // 支付方式 Ali,wx，bal（余额）
    private String payMode;
    // 余额支付
    private BigDecimal balancePay;
    // 其他支付
    private BigDecimal otherPay;
    // 支付状态
    private Integer payStatus;
    // 支付状态名称
    private String payStatusName;
    // 订单状态0未取票1已取票2退款中3已退款4.已取消
    private Integer status;
    // 状态名称
    private String statusName;
    // 订单日期
    private String makeDate;
    // 下单方式，wx，android，ios
    private String buyWay;
    // 线路编号
    private String shiftNum;
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

    // ----------------构造方法----------------

    public SaleOrder() {

    }

    // -------------- get/set方法 --------------


    public String getOriginstarttime() {
        return originstarttime;
    }

    public void setOriginstarttime(String originstarttime) {
        this.originstarttime = originstarttime;
    }

    /**
     * 获取cID
     *
     * @return String
     */
    public String getCID() {
        return cID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    /**
     * 设置cID
     *
     * @param cID
     */
    public void setCID(String cID) {
        this.cID = cID;
    }

    /**
     * 获取cName (客户编号)
     *
     * @return String
     */
    public String getCName() {
        return cName;
    }

    /**
     * 设置cName (客户编号)
     *
     * @param cName
     */
    public void setCName(String cName) {
        this.cName = cName;
    }

    /**
     * 获取mobile (客户手机号码)
     *
     * @return String
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置mobile (客户手机号码)
     *
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取rideDate (出发日期)
     *
     * @return String
     */
    public String getRideDate() {
        return rideDate;
    }

    /**
     * 设置rideDate (出发日期)
     *
     * @param rideDate
     */
    public void setRideDate(String rideDate) {
        this.rideDate = rideDate;
    }

    public Integer getLmid() {
        return lmid;
    }

    public void setLmid(Integer lmid) {
        this.lmid = lmid;
    }

    /**
     * 获取lineName (线路名称)
     *
     * @return String
     */
    public String getLinename() {
        return lineName;
    }

    /**
     * 设置lineName (线路名称)
     *
     * @param lineName
     */
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    /**
     * 获取sTStartID (出发站点编号)
     *
     * @return String
     */
    public String getSTStartID() {
        return sTStartID;
    }

    /**
     * 设置sTStartID (出发站点编号)
     *
     * @param sTStartID
     */
    public void setSTStartID(String sTStartID) {
        this.sTStartID = sTStartID;
    }

    /**
     * 获取sTStartName (出发站点名称)
     *
     * @return String
     */
    public String getStstartname() {
        return sTStartName;
    }

    /**
     * 设置sTStartName (出发站点名称)
     *
     * @param sTStartName
     */
    public void setSTStartName(String sTStartName) {
        this.sTStartName = sTStartName;
    }

    /**
     * 获取sTArriveID (到达站点编号)
     *
     * @return String
     */
    public String getStarriveid() {
        return sTArriveID;
    }

    /**
     * 设置sTArriveID (到达站点编号)
     *
     * @param sTArriveID
     */
    public void setSTArriveID(String sTArriveID) {
        this.sTArriveID = sTArriveID;
    }

    /**
     * 获取sTArriveName (到达站点名称)
     *
     * @return String
     */
    public String getStarrivename() {
        return sTArriveName;
    }

    /**
     * 设置sTArriveName (到达站点名称)
     *
     * @param sTArriveName
     */
    public void setSTArriveName(String sTArriveName) {
        this.sTArriveName = sTArriveName;
    }

    /**
     * 获取startTime (发车时间)
     *
     * @return String
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 设置startTime (发车时间)
     *
     * @param startTime
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取arriveTime (到达时间)
     *
     * @return String
     */
    public String getArriveTime() {
        return arriveTime;
    }

    /**
     * 设置arriveTime (到达时间)
     *
     * @param arriveTime
     */
    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    /**
     * 获取price (单价)
     *
     * @return String
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置price (单价)
     *
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取quantity (数量)
     *
     * @return Integer
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 设置quantity (数量)
     *
     * @param quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * 获取totalSum (订单总价)
     *
     * @return String
     */
    public BigDecimal getTotalSum() {
        return totalSum;
    }

    /**
     * 设置totalSum (订单总价)
     *
     * @param totalSum
     */
    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    /**
     * 获取coupons (优惠券编号)
     *
     * @return String
     */
    public String getCoupons() {
        return coupons;
    }

    /**
     * 设置coupons (优惠券编号)
     *
     * @param coupons
     */
    public void setCoupons(String coupons) {
        this.coupons = coupons;
    }

    /**
     * 获取couponsSum (优惠金额)
     *
     * @return String
     */
    public BigDecimal getCouponsSum() {
        return couponsSum;
    }

    /**
     * 设置couponsSum (优惠金额)
     *
     * @param couponsSum
     */
    public void setCouponsSum(BigDecimal couponsSum) {
        this.couponsSum = couponsSum;
    }

    /**
     * 获取actualSum (实付金额)
     *
     * @return String
     */
    public BigDecimal getActualSum() {
        return actualSum;
    }

    /**
     * 设置actualSum (实付金额)
     *
     * @param actualSum
     */
    public void setActualSum(BigDecimal actualSum) {
        this.actualSum = actualSum;
    }

    /**
     * 获取lName (联系人姓名)
     *
     * @return String
     */
    public String getLName() {
        return lName;
    }

    /**
     * 设置lName (联系人姓名)
     *
     * @param lName
     */
    public void setLName(String lName) {
        this.lName = lName;
    }

    /**
     * 获取lMobile (联系人手机号码)
     *
     * @return String
     */
    public String getLMobile() {
        return lMobile;
    }

    /**
     * 设置lMobile (联系人手机号码)
     *
     * @param lMobile
     */
    public void setLMobile(String lMobile) {
        this.lMobile = lMobile;
    }

    /**
     * 获取iDCode (联系人身份证)
     *
     * @return String
     */
    public String getIDCode() {
        return iDCode;
    }

    /**
     * 设置iDCode (联系人身份证)
     *
     * @param iDCode
     */
    public void setIDCode(String iDCode) {
        this.iDCode = iDCode;
    }

    /**
     * 获取payMode (支付方式 Ali,wx，bal（余额）)
     *
     * @return String
     */
    public String getPayMode() {
        return payMode;
    }

    /**
     * 设置payMode (支付方式 Ali,wx，bal（余额）)
     *
     * @param payMode
     */
    public void setPayMode(String payMode) {
        this.payMode = payMode;
    }

    /**
     * 获取balancePay (余额支付)
     *
     * @return String
     */
    public BigDecimal getBalancePay() {
        return balancePay;
    }

    /**
     * 设置balancePay (余额支付)
     *
     * @param balancePay
     */
    public void setBalancePay(BigDecimal balancePay) {
        this.balancePay = balancePay;
    }

    /**
     * 获取otherPay (其他支付)
     *
     * @return String
     */
    public BigDecimal getOtherPay() {
        return otherPay;
    }

    /**
     * 设置otherPay (其他支付)
     *
     * @param otherPay
     */
    public void setOtherPay(BigDecimal otherPay) {
        this.otherPay = otherPay;
    }

    /**
     * 获取payStatus (支付状态)
     *
     * @return Integer
     */
    public Integer getPayStatus() {
        return payStatus;
    }

    /**
     * 设置payStatus (支付状态)
     *
     * @param payStatus
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 获取payStatusName (支付状态名称)
     *
     * @return String
     */
    public String getPayStatusName() {
        return payStatusName;
    }

    /**
     * 设置payStatusName (支付状态名称)
     *
     * @param payStatusName
     */
    public void setPayStatusName(String payStatusName) {
        this.payStatusName = payStatusName;
    }

    /**
     * 获取status (订单状态0未取票1已取票2退款中3已退款4.已取消)
     *
     * @return Integer
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置status (订单状态0未取票1已取票2退款中3已退款4.已取消)
     *
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取statusName (状态名称)
     *
     * @return String
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * 设置statusName (状态名称)
     *
     * @param statusName
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * 获取makeDate (订单日期)
     *
     * @return String
     */
    public String getMakeDate() {
        return makeDate;
    }

    /**
     * 设置makeDate (订单日期)
     *
     * @param makeDate
     */
    public void setMakedate(String makeDate) {
        this.makeDate = makeDate;
    }

    /**
     * 获取buyWay (下单方式，wx，android，ios)
     *
     * @return String
     */
    public String getBuyWay() {
        return buyWay;
    }

    /**
     * 设置buyWay (下单方式，wx，android，ios)
     *
     * @param buyWay
     */
    public void setBuyWay(String buyWay) {
        this.buyWay = buyWay;
    }

    public String getShiftNum() {
        return shiftNum;
    }

    public void setShiftNum(String shiftNum) {
        this.shiftNum = shiftNum;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getIsabnormal() {
        return isabnormal;
    }

    public void setIsabnormal(Integer isabnormal) {
        this.isabnormal = isabnormal;
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

    public Integer getGetway() {
        return getway;
    }

    public void setGetway(Integer getway) {
        this.getway = getway;
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

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
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

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getLineName() {
        return lineName;
    }

    public String getsTStartID() {
        return sTStartID;
    }

    public void setsTStartID(String sTStartID) {
        this.sTStartID = sTStartID;
    }

    public String getsTStartName() {
        return sTStartName;
    }

    public void setsTStartName(String sTStartName) {
        this.sTStartName = sTStartName;
    }

    public String getsTArriveID() {
        return sTArriveID;
    }

    public void setsTArriveID(String sTArriveID) {
        this.sTArriveID = sTArriveID;
    }

    public String getsTArriveName() {
        return sTArriveName;
    }

    public void setsTArriveName(String sTArriveName) {
        this.sTArriveName = sTArriveName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getlMobile() {
        return lMobile;
    }

    public void setlMobile(String lMobile) {
        this.lMobile = lMobile;
    }

    public String getiDCode() {
        return iDCode;
    }

    public void setiDCode(String iDCode) {
        this.iDCode = iDCode;
    }

    public void setMakeDate(String makeDate) {
        this.makeDate = makeDate;
    }

    public int getDaysForAdvance() throws ParseException {
        if(makeDate == null || rideDate == null) return 0;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date makeDateTemp = sdf.parse(makeDate.substring(0, 10));
        Date rideDateTemp = sdf.parse(rideDate.substring(0, 10));
        Calendar cal = Calendar.getInstance();
        cal.setTime(makeDateTemp);
        long makeDateTime = cal.getTimeInMillis();
        cal.setTime(rideDateTemp);
        long rideDateTime = cal.getTimeInMillis();
        return Integer.parseInt(String.valueOf((rideDateTime-makeDateTime)/(1000*3600*24)));
    }
}
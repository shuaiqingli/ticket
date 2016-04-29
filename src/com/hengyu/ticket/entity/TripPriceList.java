package com.hengyu.ticket.entity;

import java.util.List;

import com.hengyu.ticket.util.DateHanlder;

/**
 * @author 李冠锋 2015-11-28
 */
public class TripPriceList {

    //------------------字段-----------------

    private Integer id;


    //规则名称
    private String tplname;
    //总票
    private Integer ticketquantity = 0;
    //总优惠票
    private Integer couponticketquantity = 0;
    //锁票数量
    private Integer lockquantity = 0;
    //位置
    private Integer isstart = 0;
    //开始卖票位置
    private Integer startseat = 0;
    //线路规则编号
    private Integer strid;
    //是否默认
    private Integer isdefault;

    //行程价格列表
    private List<TripPriceSub> tps;

    //线路规则名称
    private  String strname;

    //-------------------------------------
    private Integer lmid;
    private String begindate;
    private String enddate;
    private Integer isforever = 0;
    private Integer iseffect = 0;
    private Integer weekday;
    private String weekdaystr;
    private List<TripPriceRule> rules;

    //缺少的站点
    private List<LineManageStation> differStations;

    //----------------构造方法----------------

    public TripPriceList() {

    }

    //-------------- get/set方法 --------------


    public List<LineManageStation> getDifferStations() {
        return differStations;
    }

    public void setDifferStations(List<LineManageStation> differStations) {
        this.differStations = differStations;
    }

    public String getStrname() {
        return strname;
    }

    public void setStrname(String strname) {
        this.strname = strname;
    }

    public Integer getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Integer isdefault) {
        this.isdefault = isdefault;
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

    public Integer getIsforever() {
        return isforever;
    }

    public void setIsforever(Integer isforever) {
        this.isforever = isforever;
    }

    public Integer getIseffect() {
        return iseffect;
    }

    public void setIseffect(Integer iseffect) {
        this.iseffect = iseffect;
    }

    public List<TripPriceRule> getRules() {
        return rules;
    }

    public void setRules(List<TripPriceRule> rules) {
        this.rules = rules;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public void setWeekdaystr(String weekdaystr) {
        this.weekdaystr = weekdaystr;
    }

    public String getWeekdaystr() {
        if (this.weekday == null) {
            weekday = 0;
        }
        List<String> list = DateHanlder.getweekString(127 - this.getWeekday());
        if (list != null) {
            StringBuffer sb = new StringBuffer();
            int i = 0;
            for (String s : list) {
                sb.append(s);
                if (i != list.size() - 1) {
                    sb.append("、");
                }
                i++;
            }
            this.weekdaystr = sb.toString();
        }
        return weekdaystr;
    }


    public String getTplname() {
        return tplname;
    }

    public void setTplname(String tplname) {
        this.tplname = tplname;
    }

    public Integer getTicketquantity() {
        return ticketquantity;
    }

    public void setTicketquantity(Integer ticketquantity) {
        this.ticketquantity = ticketquantity;
    }

    public Integer getCouponticketquantity() {
        return couponticketquantity;
    }

    public void setCouponticketquantity(Integer couponticketquantity) {
        this.couponticketquantity = couponticketquantity;
    }

    public Integer getLockquantity() {
        return lockquantity;
    }

    public void setLockquantity(Integer lockquantity) {
        this.lockquantity = lockquantity;
    }

    public Integer getIsstart() {
        return isstart;
    }

    public void setIsstart(Integer isstart) {
        this.isstart = isstart;
    }

    public Integer getStartseat() {
        return startseat;
    }

    public void setStartseat(Integer startseat) {
        this.startseat = startseat;
    }

    public List<TripPriceSub> getTps() {
        return tps;
    }

    public void setTps(List<TripPriceSub> tps) {
        this.tps = tps;
    }
}
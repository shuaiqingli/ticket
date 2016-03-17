package com.hengyu.ticket.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李冠锋 2015-09-26
 */
public class LineManage implements Cloneable {

    // ------------------字段-----------------

    // 编号
    private Integer id;

    // 线路编号
    private String lineid;

    // 线路名称
    private String linename;

    // 始发站城市编号
    private String citystartid;

    // 始发站城市名称
    private String citystartname;

    // 始发站编号
    private String ststartid;

    // 始发站名称
    private String ststartname;

    // 到达城市编号
    private String cityarriveid;

    // 到达城市名称
    private String cityarrivename;

    // 到达城市车站编号
    private String starriveid;

    // 到达城市名称
    private String starrivename;
    // 票价
    private BigDecimal ticketprice = new BigDecimal(0.00);
    // 车票数量
    private Integer ticketquantity = 0;

    // 运输公司
    private String transcompany;
    // 运输公司
    private Integer tcid;
    //余票警告
    private Integer balanceticketwarn;

    // 制作人编号
    private String makeid;
    // 制作人名称
    private String makename;
    // 制作日期
    private String makedate;
    // 制作车站编号
    private String makestid;
    // 是否审核
    private Integer isapprove;
    // 审核人编号
    private String approveid;
    // 审核人名称
    private String approvename;
    // 审核日期
    private String approvedate;
    // 是否删除 0 或 1
    private Integer isdel;
    // 删除日期
    private String deldate;
    // 线路前缀编号
    private String linepre;
    // 默认到达终点时间（分钟）
    private Integer defaulttime;

    // 备注
    private String memo;

    // 车型
    private Integer carmodelid;
    private String carmodelname;

    // 默认里程
    private Integer mileage = 0;
    // 自动发布天数
    private Integer releaseday = 0;
    // 自动审核天数
    private Integer approvescheduleday;
    // 司机数量
    private Integer driverquantity;

    // 分组id
    private String groupid;

    // 线路途经站点列表
    private List<LineManageStation> lineManageStataions;
    // 排班列表
    private List<LineSchedue> lineSchedues;
    // 价格列表
    private List<LinePrice> linePrices;

    // ---------- 查询字段
    private Integer isallowupdate;

    private String begindate;
    private String enddate;

    // 是否建立反向线路
    private Integer isreverse;

    // 最后审核的班次日期
    private String lastshiftdate;
    // 最后审核的车票日期
    private String lastticketdate;

    // ----------------构造方法----------------

    public LineManage() {

    }

    // -------------- get/set方法 --------------

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLineid() {
        return lineid;
    }

    public void setLineid(String lineid) {
        this.lineid = lineid;
    }

    public String getLinename() {
        return linename;
    }

    public void setLinename(String linename) {
        this.linename = linename;
    }

    public String getCitystartid() {
        return citystartid;
    }

    public void setCitystartid(String citystartid) {
        this.citystartid = citystartid;
    }

    public String getCitystartname() {
        return citystartname;
    }

    public void setCitystartname(String citystartname) {
        this.citystartname = citystartname;
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

    public String getCityarrivename() {
        return cityarrivename;
    }

    public void setCityarrivename(String cityarrivename) {
        this.cityarrivename = cityarrivename;
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

    public BigDecimal getTicketprice() {
        return ticketprice;
    }

    public void setTicketprice(BigDecimal ticketprice) {
        this.ticketprice = ticketprice;
    }

    public Integer getTicketquantity() {
        return ticketquantity;
    }

    public void setTicketquantity(Integer ticketquantity) {
        this.ticketquantity = ticketquantity;
    }

    public String getTranscompany() {
        return transcompany;
    }

    public void setTranscompany(String transcompany) {
        this.transcompany = transcompany;
    }

    public Integer getTcid() {
        return tcid;
    }

    public void setTcid(Integer tcid) {
        this.tcid = tcid;
    }

    public String getMakeid() {
        return makeid;
    }

    public void setMakeid(String makeid) {
        this.makeid = makeid;
    }

    public String getMakename() {
        return makename;
    }

    public void setMakename(String makename) {
        this.makename = makename;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public String getMakestid() {
        return makestid;
    }

    public void setMakestid(String makestid) {
        this.makestid = makestid;
    }

    public Integer getIsapprove() {
        return isapprove;
    }

    public void setIsapprove(Integer isapprove) {
        this.isapprove = isapprove;
    }

    public String getApproveid() {
        return approveid;
    }

    public void setApproveid(String approveid) {
        this.approveid = approveid;
    }

    public String getApprovename() {
        return approvename;
    }

    public void setApprovename(String approvename) {
        this.approvename = approvename;
    }

    public String getApprovedate() {
        return approvedate;
    }

    public void setApprovedate(String approvedate) {
        this.approvedate = approvedate;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public String getDeldate() {
        return deldate;
    }

    public void setDeldate(String deldate) {
        this.deldate = deldate;
    }

    public String getLinepre() {
        return linepre;
    }

    public void setLinepre(String linepre) {
        this.linepre = linepre;
    }

    public Integer getDefaulttime() {
        return defaulttime;
    }

    public void setDefaulttime(Integer defaulttime) {
        this.defaulttime = defaulttime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Integer getReleaseday() {
        return releaseday;
    }

    public void setReleaseday(Integer releaseday) {
        this.releaseday = releaseday;
    }

    public Integer getApprovescheduleday() {
        return approvescheduleday;
    }

    public void setApprovescheduleday(Integer approvescheduleday) {
        this.approvescheduleday = approvescheduleday;
    }

    public Integer getDriverquantity() {
        return driverquantity;
    }

    public void setDriverquantity(Integer driverquantity) {
        this.driverquantity = driverquantity;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public List<LineManageStation> getLineManageStataions() {
        return lineManageStataions;
    }

    public void setLineManageStataions(List<LineManageStation> lineManageStataions) {
        this.lineManageStataions = lineManageStataions;
    }

    public List<LineSchedue> getLineSchedues() {
        return lineSchedues;
    }

    public void setLineSchedues(List<LineSchedue> lineSchedues) {
        this.lineSchedues = lineSchedues;
    }

    public List<LinePrice> getLinePrices() {
        return linePrices;
    }

    public void setLinePrices(List<LinePrice> linePrices) {
        this.linePrices = linePrices;
    }

    public Integer getIsallowupdate() {
        return isallowupdate;
    }

    public void setIsallowupdate(Integer isallowupdate) {
        this.isallowupdate = isallowupdate;
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

    public Integer getIsreverse() {
        return isreverse;
    }

    public void setIsreverse(Integer isreverse) {
        this.isreverse = isreverse;
    }

    public String getLastshiftdate() {
        return lastshiftdate;
    }

    public void setLastshiftdate(String lastshiftdate) {
        this.lastshiftdate = lastshiftdate;
    }

    public String getLastticketdate() {
        return lastticketdate;
    }

    public void setLastticketdate(String lastticketdate) {
        this.lastticketdate = lastticketdate;
    }

    public Integer getBalanceticketwarn() {
        return balanceticketwarn;
    }

    public void setBalanceticketwarn(Integer balanceticketwarn) {
        this.balanceticketwarn = balanceticketwarn;
    }
}
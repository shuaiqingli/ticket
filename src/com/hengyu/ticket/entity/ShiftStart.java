package com.hengyu.ticket.entity;

import java.math.BigDecimal;

/**
 * @author 李冠锋 2015-10-10
 */
public class ShiftStart {

	// ------------------字段-----------------

	// 编码
	private Long id;
	// 出行日期
	private String ridedate;
	// 线路编号
	private Integer lmid;
    //班次号
	private Long shiftid;
	// 线路名称
	private String linename;
	// 始发站ID
	private String originstid;
	// 始发站名称
	private String originstname;
	// 到达站编号
	private String starriveid;
	// 到达站名称
	private String starrivename;
	// 首发时间
	private String originstarttime;
	// 车牌号码
	private String platenum;
	// 司机编号
	private String driverid;
	// 司机名称
	private String drivername;
	// 核载
	private Integer nuclearload;
	// 当前人数
	private Integer currpeople;
	// 当前车站
	private String currstationid;
	// 当前车站名称
	private String currstationname;
	// 正点发车时间
	private String punctualstart;
	// 实际发车时间
	private String actualstart;
	// 是否发车
	private Integer isstart;
	// 是否发车名称
	private String isstartname;
	// 班次号
	private String shiftcode;
	// 备注
	private String memo;
	//发车备注
	private String startmemo;
	// 全票人数
	private Integer allticketnum;
	// 半票人数
	private Integer halfticketnum;
	// 免票人数
	private Integer freeticketnum;
	// 制单人
	private String makeid;
	// 制单人姓名
	private String makename;
	// 制单日期
	private String makedate;
	// 托运行李件数
	private Integer consignquantity = 0;
	// 托运行李金额
	private BigDecimal consignsum = new BigDecimal("0");
	// 乘客行李金额
	private BigDecimal passengersum = new BigDecimal("0");
	// 乘客行李件数
	private Integer passengerquantity = 0;
	// 改车牌、司
	private String revisememo;
	// 司机签名
	private String driversign;
	// 临时班次
	
	//司机
	private String driveridii;
	private String drivernameii;
	private String changelog;

	private Integer istemp = 0;
	
	private Integer iscancel;
	private String iscancelname;

	// ----------------构造方法----------------

	public ShiftStart() {

	}

	// -------------- get/set方法 --------------


    public void setShiftid(Long shiftid) {
        this.shiftid = shiftid;
    }

    public Long getShiftid() {
		return shiftid;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getOriginstid() {
        return originstid;
    }

    public void setOriginstid(String originstid) {
        this.originstid = originstid;
    }

    public String getOriginstname() {
        return originstname;
    }

    public void setOriginstname(String originstname) {
        this.originstname = originstname;
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

    public String getOriginstarttime() {
        return originstarttime;
    }

    public void setOriginstarttime(String originstarttime) {
        this.originstarttime = originstarttime;
    }

    public String getPlatenum() {
        return platenum;
    }

    public void setPlatenum(String platenum) {
        this.platenum = platenum;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public Integer getNuclearload() {
        return nuclearload;
    }

    public void setNuclearload(Integer nuclearload) {
        this.nuclearload = nuclearload;
    }

    public Integer getCurrpeople() {
        return currpeople;
    }

    public void setCurrpeople(Integer currpeople) {
        this.currpeople = currpeople;
    }

    public String getCurrstationid() {
        return currstationid;
    }

    public void setCurrstationid(String currstationid) {
        this.currstationid = currstationid;
    }

    public String getCurrstationname() {
        return currstationname;
    }

    public void setCurrstationname(String currstationname) {
        this.currstationname = currstationname;
    }

    public String getPunctualstart() {
        return punctualstart;
    }

    public void setPunctualstart(String punctualstart) {
        this.punctualstart = punctualstart;
    }

    public String getActualstart() {
        return actualstart;
    }

    public void setActualstart(String actualstart) {
        this.actualstart = actualstart;
    }

    public Integer getIsstart() {
        return isstart;
    }

    public void setIsstart(Integer isstart) {
        this.isstart = isstart;
    }

    public String getIsstartname() {
        return isstartname;
    }

    public void setIsstartname(String isstartname) {
        this.isstartname = isstartname;
    }

    public String getShiftcode() {
        return shiftcode;
    }

    public void setShiftcode(String shiftcode) {
        this.shiftcode = shiftcode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getStartmemo() {
        return startmemo;
    }

    public void setStartmemo(String startmemo) {
        this.startmemo = startmemo;
    }

    public Integer getAllticketnum() {
        return allticketnum;
    }

    public void setAllticketnum(Integer allticketnum) {
        this.allticketnum = allticketnum;
    }

    public Integer getHalfticketnum() {
        return halfticketnum;
    }

    public void setHalfticketnum(Integer halfticketnum) {
        this.halfticketnum = halfticketnum;
    }

    public Integer getFreeticketnum() {
        return freeticketnum;
    }

    public void setFreeticketnum(Integer freeticketnum) {
        this.freeticketnum = freeticketnum;
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

    public Integer getConsignquantity() {
        return consignquantity;
    }

    public void setConsignquantity(Integer consignquantity) {
        this.consignquantity = consignquantity;
    }

    public BigDecimal getConsignsum() {
        return consignsum;
    }

    public void setConsignsum(BigDecimal consignsum) {
        this.consignsum = consignsum;
    }

    public BigDecimal getPassengersum() {
        return passengersum;
    }

    public void setPassengersum(BigDecimal passengersum) {
        this.passengersum = passengersum;
    }

    public Integer getPassengerquantity() {
        return passengerquantity;
    }

    public void setPassengerquantity(Integer passengerquantity) {
        this.passengerquantity = passengerquantity;
    }

    public String getRevisememo() {
        return revisememo;
    }

    public void setRevisememo(String revisememo) {
        this.revisememo = revisememo;
    }

    public String getDriversign() {
        return driversign;
    }

    public void setDriversign(String driversign) {
        this.driversign = driversign;
    }

    public String getDriveridii() {
        return driveridii;
    }

    public void setDriveridii(String driveridii) {
        this.driveridii = driveridii;
    }

    public String getDrivernameii() {
        return drivernameii;
    }

    public void setDrivernameii(String drivernameii) {
        this.drivernameii = drivernameii;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    public Integer getIstemp() {
        return istemp;
    }

    public void setIstemp(Integer istemp) {
        this.istemp = istemp;
    }

    public Integer getIscancel() {
        return iscancel;
    }

    public void setIscancel(Integer iscancel) {
        this.iscancel = iscancel;
    }

    public String getIscancelname() {
        return iscancelname;
    }

    public void setIscancelname(String iscancelname) {
        this.iscancelname = iscancelname;
    }
}
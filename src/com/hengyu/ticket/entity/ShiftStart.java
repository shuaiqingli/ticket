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

	/**
	 * 获取id
	 * 
	 * @return String
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取shiftcode
	 * 
	 * @return String
	 */
	public String getShiftcode() {
		return shiftcode;
	}

	/**
	 * 设置shiftcode
	 * 
	 * @param shiftcode
	 */
	public void setShiftcode(String shiftcode) {
		this.shiftcode = shiftcode;
	}

	/**
	 * 获取ridedate
	 * 
	 * @return String
	 */
	public String getRidedate() {
		return ridedate;
	}

	/**
	 * 设置ridedate
	 * 
	 * @param ridedate
	 */
	public void setRidedate(String ridedate) {
		this.ridedate = ridedate;
	}

	public Integer getLmid() {
		return lmid;
	}

	public void setLmid(Integer lmid) {
		this.lmid = lmid;
	}

	/**
	 * 获取linename
	 * 
	 * @return String
	 */
	public String getLinename() {
		return linename;
	}

	/**
	 * 设置linename
	 * 
	 * @param linename
	 */
	public void setLinename(String linename) {
		this.linename = linename;
	}

	/**
	 * 获取originstid
	 * 
	 * @return String
	 */
	public String getOriginstid() {
		return originstid;
	}

	/**
	 * 设置originstid
	 * 
	 * @param originstid
	 */
	public void setOriginstid(String originstid) {
		this.originstid = originstid;
	}

	/**
	 * 获取originstname
	 * 
	 * @return String
	 */
	public String getOriginstname() {
		return originstname;
	}

	/**
	 * 设置originstname
	 * 
	 * @param originstname
	 */
	public void setOriginstname(String originstname) {
		this.originstname = originstname;
	}

	/**
	 * 获取starriveid
	 * 
	 * @return String
	 */
	public String getStarriveid() {
		return starriveid;
	}

	/**
	 * 设置starriveid
	 * 
	 * @param starriveid
	 */
	public void setStarriveid(String starriveid) {
		this.starriveid = starriveid;
	}

	/**
	 * 获取starrivename
	 * 
	 * @return String
	 */
	public String getStarrivename() {
		return starrivename;
	}

	/**
	 * 设置starrivename
	 * 
	 * @param starrivename
	 */
	public void setStarrivename(String starrivename) {
		this.starrivename = starrivename;
	}

	/**
	 * 获取originstarttime
	 * 
	 * @return String
	 */
	public String getOriginstarttime() {
		return originstarttime;
	}

	/**
	 * 设置originstarttime
	 * 
	 * @param originstarttime
	 */
	public void setOriginstarttime(String originstarttime) {
		this.originstarttime = originstarttime;
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
	 * 获取driverid
	 * 
	 * @return String
	 */
	public String getDriverid() {
		return driverid;
	}

	/**
	 * 设置driverid
	 * 
	 * @param driverid
	 */
	public void setDriverid(String driverid) {
		this.driverid = driverid;
	}

	/**
	 * 获取drivername
	 * 
	 * @return String
	 */
	public String getDrivername() {
		return drivername;
	}

	/**
	 * 设置drivername
	 * 
	 * @param drivername
	 */
	public void setDrivername(String drivername) {
		this.drivername = drivername;
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

	/**
	 * 获取currpeople
	 * 
	 * @return Integer
	 */
	public Integer getCurrpeople() {
		return currpeople;
	}

	/**
	 * 设置currpeople
	 * 
	 * @param currpeople
	 */
	public void setCurrpeople(Integer currpeople) {
		this.currpeople = currpeople;
	}

	/**
	 * 获取currstationid
	 * 
	 * @return String
	 */
	public String getCurrstationid() {
		return currstationid;
	}

	/**
	 * 设置currstationid
	 * 
	 * @param currstationid
	 */
	public void setCurrstationid(String currstationid) {
		this.currstationid = currstationid;
	}

	/**
	 * 获取currstationname
	 * 
	 * @return String
	 */
	public String getCurrstationname() {
		return currstationname;
	}

	/**
	 * 设置currstationname
	 * 
	 * @param currstationname
	 */
	public void setCurrstationname(String currstationname) {
		this.currstationname = currstationname;
	}

	/**
	 * 获取punctualstart
	 * 
	 * @return String
	 */
	public String getPunctualstart() {
		return punctualstart;
	}

	/**
	 * 设置punctualstart
	 * 
	 * @param punctualstart
	 */
	public void setPunctualstart(String punctualstart) {
		this.punctualstart = punctualstart;
	}

	/**
	 * 获取actualstart
	 * 
	 * @return String
	 */
	public String getActualstart() {
		return actualstart;
	}

	/**
	 * 设置actualstart
	 * 
	 * @param actualstart
	 */
	public void setActualstart(String actualstart) {
		this.actualstart = actualstart;
	}

	/**
	 * 获取isstart
	 * 
	 * @return Integer
	 */
	public Integer getIsstart() {
		return isstart;
	}

	/**
	 * 设置isstart
	 * 
	 * @param isstart
	 */
	public void setIsstart(Integer isstart) {
		this.isstart = isstart;
	}

	/**
	 * 获取isstartname
	 * 
	 * @return String
	 */
	public String getIsstartname() {
		return isstartname;
	}

	/**
	 * 设置isstartname
	 * 
	 * @param isstartname
	 */
	public void setIsstartname(String isstartname) {
		this.isstartname = isstartname;
	}

	/**
	 * 获取memo
	 * 
	 * @return String
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * 设置memo
	 * 
	 * @param memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * 获取allticketnum
	 * 
	 * @return Integer
	 */
	public Integer getAllticketnum() {
		return allticketnum;
	}

	/**
	 * 设置allticketnum
	 * 
	 * @param allticketnum
	 */
	public void setAllticketnum(Integer allticketnum) {
		this.allticketnum = allticketnum;
	}

	/**
	 * 获取halfticketnum
	 * 
	 * @return Integer
	 */
	public Integer getHalfticketnum() {
		return halfticketnum;
	}

	/**
	 * 设置halfticketnum
	 * 
	 * @param halfticketnum
	 */
	public void setHalfticketnum(Integer halfticketnum) {
		this.halfticketnum = halfticketnum;
	}

	/**
	 * 获取freeticketnum
	 * 
	 * @return Integer
	 */
	public Integer getFreeticketnum() {
		return freeticketnum;
	}

	/**
	 * 设置freeticketnum
	 * 
	 * @param freeticketnum
	 */
	public void setFreeticketnum(Integer freeticketnum) {
		this.freeticketnum = freeticketnum;
	}

	/**
	 * 获取makeid
	 * 
	 * @return String
	 */
	public String getMakeid() {
		return makeid;
	}

	/**
	 * 设置makeid
	 * 
	 * @param makeid
	 */
	public void setMakeid(String makeid) {
		this.makeid = makeid;
	}

	/**
	 * 获取makename
	 * 
	 * @return String
	 */
	public String getMakename() {
		return makename;
	}

	/**
	 * 设置makename
	 * 
	 * @param makename
	 */
	public void setMakename(String makename) {
		this.makename = makename;
	}

	/**
	 * 获取makedate
	 * 
	 * @return String
	 */
	public String getMakedate() {
		return makedate;
	}

	/**
	 * 设置makedate
	 * 
	 * @param makedate
	 */
	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}

	public Integer getConsignQuantity() {
		return consignquantity;
	}

	public void setConsignQuantity(Integer consignquantity) {
		this.consignquantity = consignquantity;
	}

	public BigDecimal getConsignSum() {
		return consignsum;
	}

	public void setConsignSum(BigDecimal consignsum) {
		this.consignsum = consignsum;
	}

	public Integer getPassengerQuantity() {
		return passengerquantity;
	}

	public void setPassengerQuantity(Integer passengerquantity) {
		this.passengerquantity = passengerquantity;
	}

	public String getReviseMemo() {
		return revisememo;
	}

	public void setReviseMemo(String revisememo) {
		this.revisememo = revisememo;
	}

	public String getDriverSign() {
		return driversign;
	}

	public void setDriverSign(String driversign) {
		this.driversign = driversign;
	}

	public Integer getIsTemp() {
		return istemp;
	}

	public void setIsTemp(Integer istemp) {
		this.istemp = istemp;
	}

	public BigDecimal getPassengerSum() {
		return passengersum;
	}

	public void setPassengerSum(BigDecimal passengersum) {
		this.passengersum = passengersum; 
	}

	public String getStartmemo() {
		return startmemo;
	}

	public void setStartmemo(String startmemo) {
		this.startmemo = startmemo;
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
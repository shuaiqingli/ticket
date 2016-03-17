package com.hengyu.ticket.entity;

import java.math.BigDecimal;

/**
  * @author 李冠锋 2015-10-30 
  */
public class SaleOrderTicket{

	//------------------字段-----------------
	
	//验票码
	private String checkcode;
	//订单号
	private String soid;
	//取票码
	private String ticketcode;
	//线路名称
	private String linename;
	//班次名称
	private String shiftnum;
	//出发日期
	private String ridedate;
	//发车时间
	private String starttime;
	//价格
	private BigDecimal price;
	//是否取票
	private Integer istake = 0;
	private String istakename;
	//取票日期
	private String takedate;
	//是否上车
	private Integer isontrain = 0;
	private String isontrainname;
	//上车日期
	private String ontraindate;
	private Integer isabnormal = 0;
	//备注
	private String memo;
	private Integer getway = 0;
	private String sid;
	private String sname;
//	0正常购票1退票
	private Integer status;
	
	private String starrivename;
	//运输公司
	private String transcompany;
	//座位号
	private Integer seatno;
	//座位id
	private Integer seatid;
	//是否优惠票
	private Integer isdiscount;
	//支付状态
	private Integer paystatus;
	
	//出发城市编号
	private String ststartid;
	//出发城市名称
	private String ststartname;
	//到达城市名称
	private String starriveid;
	//线路编号
	private Integer lmid;
	//优惠价格
	private BigDecimal vprice;
	
//	取票日期
	private String sdate;

	
	//----------------构造方法----------------
	
	public SaleOrderTicket(){

	}

	//-------------- get/set方法 --------------
	
	
	/**
	 *获取checkcode 
	 * @return String  
	 */
	public String getCheckcode(){
		return checkcode;
	}

	public String getStarrivename() {
		return starrivename;
	}

	public void setStarrivename(String starrivename) {
		this.starrivename = starrivename;
	}

	/**
	 *设置checkcode 
	 * @param checkcode 
 	 */
	public void setCheckcode(String checkcode){
		this.checkcode = checkcode;
	}

	/**
	 *获取soid 
	 * @return String  
	 */
	public String getSoid(){
		return soid;
	}

	/**
	 *设置soid 
	 * @param soid 
 	 */
	public void setSoid(String soid){
		this.soid = soid;
	}

	/**
	 *获取ticketcode 
	 * @return String  
	 */
	public String getTicketcode(){
		return ticketcode;
	}

	/**
	 *设置ticketcode 
	 * @param ticketcode 
 	 */
	public void setTicketcode(String ticketcode){
		this.ticketcode = ticketcode;
	}

	/**
	 *获取linename 
	 * @return String  
	 */
	public String getLinename(){
		return linename;
	}

	/**
	 *设置linename 
	 * @param linename 
 	 */
	public void setLinename(String linename){
		this.linename = linename;
	}

	/**
	 *获取shiftnum 
	 * @return String  
	 */
	public String getShiftnum(){
		return shiftnum;
	}

	/**
	 *设置shiftnum 
	 * @param shiftnum 
 	 */
	public void setShiftnum(String shiftnum){
		this.shiftnum = shiftnum;
	}

	/**
	 *获取ridedate 
	 * @return String  
	 */
	public String getRidedate(){
		return ridedate;
	}

	/**
	 *设置ridedate 
	 * @param ridedate 
 	 */
	public void setRidedate(String ridedate){
		this.ridedate = ridedate;
	}

	/**
	 *获取starttime 
	 * @return String  
	 */
	public String getStarttime(){
		return starttime;
	}

	/**
	 *设置starttime 
	 * @param starttime 
 	 */
	public void setStarttime(String starttime){
		this.starttime = starttime;
	}

	/**
	 *获取price 
	 * @return String  
	 */
	public BigDecimal getPrice(){
		return price;
	}

	/**
	 *设置price 
	 * @param price 
 	 */
	public void setPrice(BigDecimal price){
		this.price = price;
	}

	/**
	 *获取istake 
	 * @return Integer  
	 */
	public Integer getIstake(){
		return istake;
	}

	/**
	 *设置istake 
	 * @param istake 
 	 */
	public void setIstake(Integer istake){
		this.istake = istake;
	}

	/**
	 *获取istakename 
	 * @return String  
	 */
	public String getIstakename(){
		return istakename;
	}

	/**
	 *设置istakename 
	 * @param istakename 
 	 */
	public void setIstakename(String istakename){
		this.istakename = istakename;
	}

	/**
	 *获取takedate 
	 * @return String  
	 */
	public String getTakedate(){
		return takedate;
	}

	/**
	 *设置takedate 
	 * @param takedate 
 	 */
	public void setTakedate(String takedate){
		this.takedate = takedate;
	}

	/**
	 *获取isontrain 
	 * @return Integer  
	 */
	public Integer getIsontrain(){
		return isontrain;
	}

	/**
	 *设置isontrain 
	 * @param isontrain 
 	 */
	public void setIsontrain(Integer isontrain){
		this.isontrain = isontrain;
	}

	/**
	 *获取isontrainname 
	 * @return String  
	 */
	public String getIsontrainname(){
		return isontrainname;
	}

	/**
	 *设置isontrainname 
	 * @param isontrainname 
 	 */
	public void setIsontrainname(String isontrainname){
		this.isontrainname = isontrainname;
	}

	/**
	 *获取ontraindate 
	 * @return String  
	 */
	public String getOntraindate(){
		return ontraindate;
	}

	/**
	 *设置ontraindate 
	 * @param ontraindate 
 	 */
	public void setOntraindate(String ontraindate){
		this.ontraindate = ontraindate;
	}

	/**
	 *获取isabnormal 
	 * @return Integer  
	 */
	public Integer getIsabnormal(){
		return isabnormal;
	}

	/**
	 *设置isabnormal 
	 * @param isabnormal 
 	 */
	public void setIsabnormal(Integer isabnormal){
		this.isabnormal = isabnormal;
	}

	/**
	 *获取memo 
	 * @return String  
	 */
	public String getMemo(){
		return memo;
	}

	/**
	 *设置memo 
	 * @param memo 
 	 */
	public void setMemo(String memo){
		this.memo = memo;
	}

	/**
	 *获取getway 
	 * @return Integer  
	 */
	public Integer getGetway(){
		return getway;
	}

	/**
	 *设置getway 
	 * @param getway 
 	 */
	public void setGetway(Integer getway){
		this.getway = getway;
	}

	/**
	 *获取sid 
	 * @return String  
	 */
	public String getSid(){
		return sid;
	}

	/**
	 *设置sid 
	 * @param sid 
 	 */
	public void setSid(String sid){
		this.sid = sid;
	}

	/**
	 *获取sname 
	 * @return String  
	 */
	public String getSname(){
		return sname;
	}

	/**
	 *设置sname 
	 * @param sname 
 	 */
	public void setSname(String sname){
		this.sname = sname;
	}

	/**
	 *获取status 
	 * @return Integer  
	 */
	public Integer getStatus(){
		return status;
	}

	/**
	 *设置status 
	 * @param status 
 	 */
	public void setStatus(Integer status){
		this.status = status;
	}

	public String getTranscompany() {
		return transcompany;
	}

	public void setTranscompany(String transcompany) {
		this.transcompany = transcompany;
	}

	public Integer getSeatno() {
		return seatno;
	}

	public void setSeatno(Integer seatno) {
		this.seatno = seatno;
	}

	public Integer getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(Integer paystatus) {
		this.paystatus = paystatus;
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

	public Integer getSeatid() {
		return seatid;
	}

	public void setSeatid(Integer seatid) {
		this.seatid = seatid;
	}

	public Integer getIsdiscount() {
		return isdiscount;
	}

	public void setIsdiscount(Integer isdiscount) {
		this.isdiscount = isdiscount;
	}

	public Integer getLmid() {
		return lmid;
	}

	public void setLmid(Integer lmid) {
		this.lmid = lmid;
	}

	public BigDecimal getVprice() {
		return vprice;
	}

	public void setVprice(BigDecimal vprice) {
		this.vprice = vprice;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
}
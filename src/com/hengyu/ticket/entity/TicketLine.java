package com.hengyu.ticket.entity;

import java.math.BigDecimal;


 /**
  * @author 李冠锋 2015-10-10 
  */
public class TicketLine{

	//------------------字段-----------------
	
	//编号
	private Integer id;
	//线路编号
	private Integer lmid;
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
	
	private BigDecimal promotionprice;

	//----------------构造方法----------------
	
	public TicketLine(){

	}

	//-------------- get/set方法 --------------
	
	/**
	 *获取id (编号)
	 * @return Integer  
	 */
	public Integer getId(){
		return id;
	}

	/**
	 *设置id (编号)
	 * @param id 
 	 */
	public void setId(Integer id){
		this.id = id;
	}

	

	public Integer getLmid() {
		return lmid;
	}

	public void setLmid(Integer lmid) {
		this.lmid = lmid;
	}

	/**
	 *获取linename (线路名称)
	 * @return String  
	 */
	public String getLinename(){
		return linename;
	}

	/**
	 *设置linename (线路名称)
	 * @param linename 
 	 */
	public void setLinename(String linename){
		this.linename = linename;
	}

	/**
	 *获取shiftcode 
	 * @return String  
	 */
	public String getShiftcode(){
		return shiftcode;
	}

	/**
	 *设置shiftcode 
	 * @param shiftcode 
 	 */
	public void setShiftcode(String shiftcode){
		this.shiftcode = shiftcode;
	}

	/**
	 *获取citystartid (始发站城市编号)
	 * @return String  
	 */
	public String getCitystartid(){
		return citystartid;
	}

	/**
	 *设置citystartid (始发站城市编号)
	 * @param citystartid 
 	 */
	public void setCitystartid(String citystartid){
		this.citystartid = citystartid;
	}

	/**
	 *获取originstarttime 
	 * @return String  
	 */
	public String getOriginstarttime(){
		return originstarttime;
	}

	/**
	 *设置originstarttime 
	 * @param originstarttime 
 	 */
	public void setOriginstarttime(String originstarttime){
		this.originstarttime = originstarttime;
	}

	/**
	 *获取ststartid (始发站编号)
	 * @return String  
	 */
	public String getStstartid(){
		return ststartid;
	}

	/**
	 *设置ststartid (始发站编号)
	 * @param ststartid 
 	 */
	public void setStstartid(String ststartid){
		this.ststartid = ststartid;
	}

	/**
	 *获取ststartname (始发站名称)
	 * @return String  
	 */
	public String getStstartname(){
		return ststartname;
	}

	/**
	 *设置ststartname (始发站名称)
	 * @param ststartname 
 	 */
	public void setStstartname(String ststartname){
		this.ststartname = ststartname;
	}

	/**
	 *获取cityarriveid (到达城市编号)
	 * @return String  
	 */
	public String getCityarriveid(){
		return cityarriveid;
	}

	/**
	 *设置cityarriveid (到达城市编号)
	 * @param cityarriveid 
 	 */
	public void setCityarriveid(String cityarriveid){
		this.cityarriveid = cityarriveid;
	}

	/**
	 *获取starriveid (到达站点编号)
	 * @return String  
	 */
	public String getStarriveid(){
		return starriveid;
	}

	/**
	 *设置starriveid (到达站点编号)
	 * @param starriveid 
 	 */
	public void setStarriveid(String starriveid){
		this.starriveid = starriveid;
	}

	/**
	 *获取starrivename (到达站点名称)
	 * @return String  
	 */
	public String getStarrivename(){
		return starrivename;
	}

	/**
	 *设置starrivename (到达站点名称)
	 * @param starrivename 
 	 */
	public void setStarrivename(String starrivename){
		this.starrivename = starrivename;
	}

	/**
	 *获取starttime (发车时间)
	 * @return String  
	 */
	public String getStarttime(){
		return starttime;
	}

	/**
	 *设置starttime (发车时间)
	 * @param starttime 
 	 */
	public void setStarttime(String starttime){
		this.starttime = starttime;
	}

	/**
	 *获取arrivetime (到达时间)
	 * @return String  
	 */
	public String getArrivetime(){
		return arrivetime;
	}

	/**
	 *设置arrivetime (到达时间)
	 * @param arrivetime 
 	 */
	public void setArrivetime(String arrivetime){
		this.arrivetime = arrivetime;
	}

	/**
	 *获取ticketprice (票价)
	 * @return String  
	 */
	public BigDecimal getTicketprice(){
		return ticketprice;
	}

	/**
	 *设置ticketprice (票价)
	 * @param ticketprice 
 	 */
	public void setTicketprice(BigDecimal ticketprice){
		this.ticketprice = ticketprice;
	}

	/**
	 *获取transcompany (运输公司)
	 * @return String  
	 */
	public String getTranscompany(){
		return transcompany;
	}

	/**
	 *设置transcompany (运输公司)
	 * @param transcompany 
 	 */
	public void setTranscompany(String transcompany){
		this.transcompany = transcompany;
	}

	/**
	 *获取ticketdate 
	 * @return String  
	 */
	public String getTicketdate(){
		return ticketdate;
	}

	/**
	 *设置ticketdate 
	 * @param ticketdate 
 	 */
	public void setTicketdate(String ticketdate){
		this.ticketdate = ticketdate;
	}

	/**
	 *获取makedate (制单日期)
	 * @return String  
	 */
	public String getMakedate(){
		return makedate;
	}

	/**
	 *设置makedate (制单日期)
	 * @param makedate 
 	 */
	public void setMakedate(String makedate){
		this.makedate = makedate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public TicketStore getTicketStore() {
		return ticketStore;
	}

	public void setTicketStore(TicketStore ticketStore) {
		this.ticketStore = ticketStore;
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
//		if(favprice==null){
//			favprice = ticketprice;
//		}
		return favprice;
	}

	public void setFavprice(BigDecimal favprice) {
		this.favprice = favprice;
	}

	public BigDecimal getPromotionprice() {
//		if(getFavprice()!=null&&ticketprice!=null){
//			promotionprice = getFavprice().subtract(ticketprice);
//		}
		return promotionprice;
	}

	public void setPromotionprice(BigDecimal promotionprice) {
		this.promotionprice = promotionprice;
	}

	public Integer getIscustomer() {
		return iscustomer;
	}

	public void setIscustomer(Integer iscustomer) {
		this.iscustomer = iscustomer;
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
}
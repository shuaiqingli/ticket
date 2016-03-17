package com.hengyu.ticket.entity;

import java.math.BigDecimal;
import java.util.List;

/**
  * @author 李冠锋 2015-12-29 
  */
public class RefundTicket{

	//------------------字段-----------------
	
	private String id;
	private String soid;
	private String cid;
	private String cname;
	private String mobile;
	private String ridedate;
	private BigDecimal totalprice;
	private BigDecimal actualprice;
	private Integer rtstatus;
	private String approveid;
	private String approvename;
	private String approvedate;
	private String makedate;
	private String outcode;
	private String memo;
	//退款发起类型 0 客户 1 客服
	private Integer makesort;
	
	private String starriveid;
	private String starrivename;
	private String ststartid;
	private String ststartname;
	private String starttime;
	private Integer isaffirm;
	private String isaffirmname;
	private String affirmid;
	private String affirmname;
	private String affirmdate;
	private String affirmmemo;
	
	
	//原价
	private BigDecimal originalprice;
	//应退百分比
	private Integer rpercent;
	private Integer quantity;
	private Integer rank;
	
	//退票详情
	private List<RefundTicketDetail> details;

	//----------------构造方法----------------
	
	public RefundTicket(){

	}

	//-------------- get/set方法 --------------
	
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getSoid(){
		return soid;
	}
	public void setSoid(String soid){
		this.soid = soid;
	}
	public String getCid(){
		return cid;
	}
	public void setCid(String cid){
		this.cid = cid;
	}
	public String getCname(){
		return cname;
	}
	public void setCname(String cname){
		this.cname = cname;
	}
	public String getMobile(){
		return mobile;
	}
	public void setMobile(String mobile){
		this.mobile = mobile;
	}
	public String getRidedate(){
		return ridedate;
	}
	public void setRidedate(String ridedate){
		this.ridedate = ridedate;
	}
	
	public BigDecimal getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	public BigDecimal getActualprice(){
		return actualprice;
	}
	public void setActualprice(BigDecimal actualprice){
		this.actualprice = actualprice;
	}
	public Integer getRtstatus(){
		return rtstatus;
	}
	public void setRtstatus(Integer rtstatus){
		this.rtstatus = rtstatus;
	}
	public String getApproveid(){
		return approveid;
	}
	public void setApproveid(String approveid){
		this.approveid = approveid;
	}
	public String getApprovename(){
		return approvename;
	}
	public void setApprovename(String approvename){
		this.approvename = approvename;
	}
	public String getApprovedate(){
		return approvedate;
	}
	public void setApprovedate(String approvedate){
		this.approvedate = approvedate;
	}
	public String getMakedate(){
		return makedate;
	}
	public void setMakedate(String makedate){
		this.makedate = makedate;
	}
	public String getOutcode() {
		return outcode;
	}

	public void setOutcode(String outcode) {
		this.outcode = outcode;
	}

	public List<RefundTicketDetail> getDetails() {
		return details;
	}

	public void setDetails(List<RefundTicketDetail> details) {
		this.details = details;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BigDecimal getOriginalprice() {
		return originalprice;
	}

	public void setOriginalprice(BigDecimal originalprice) {
		this.originalprice = originalprice;
	}

	public Integer getRpercent() {
		return rpercent;
	}

	public void setRpercent(Integer rpercent) {
		this.rpercent = rpercent;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getMakesort() {
		return makesort;
	}

	public void setMakesort(Integer makesort) {
		this.makesort = makesort;
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

	public Integer getIsaffirm() {
		return isaffirm;
	}

	public void setIsaffirm(Integer isaffirm) {
		this.isaffirm = isaffirm;
	}

	public String getIsaffirmname() {
		return isaffirmname;
	}

	public void setIsaffirmname(String isaffirmname) {
		this.isaffirmname = isaffirmname;
	}

	public String getAffirmid() {
		return affirmid;
	}

	public void setAffirmid(String affirmid) {
		this.affirmid = affirmid;
	}

	public String getAffirmname() {
		return affirmname;
	}

	public void setAffirmname(String affirmname) {
		this.affirmname = affirmname;
	}

	public String getAffirmdate() {
		return affirmdate;
	}

	public void setAffirmdate(String affirmdate) {
		this.affirmdate = affirmdate;
	}

	public String getAffirmmemo() {
		return affirmmemo;
	}

	public void setAffirmmemo(String affirmmemo) {
		this.affirmmemo = affirmmemo;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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
	
}
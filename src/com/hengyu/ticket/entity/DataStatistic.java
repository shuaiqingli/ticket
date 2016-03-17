package com.hengyu.ticket.entity;

//数据统计
public class DataStatistic {
	
	//今天出票
	private Integer todayticket;
	//今天订单
	private Integer todayorder;
	//今天关注用户
	private Integer todaysubscribe;
	
	
	//昨天出票
	private Integer yesterdayticket;
	//昨天订单
	private Integer yesterdayorder;
	//昨天关注用户
	private Integer yesterdasubscribe;
	
	//总关注人数
	private Integer sumsubscribe;
	//总订单
	private Integer sumorder;
	//总出票
	private Integer sumticket;
	
	
	public Integer getTodayticket() {
		return todayticket;
	}
	public void setTodayticket(Integer todayticket) {
		this.todayticket = todayticket;
	}
	public Integer getTodayorder() {
		return todayorder;
	}
	public void setTodayorder(Integer todayorder) {
		this.todayorder = todayorder;
	}
	public Integer getTodaysubscribe() {
		return todaysubscribe;
	}
	public void setTodaysubscribe(Integer todaysubscribe) {
		this.todaysubscribe = todaysubscribe;
	}
	public Integer getYesterdayticket() {
		return yesterdayticket;
	}
	public void setYesterdayticket(Integer yesterdayticket) {
		this.yesterdayticket = yesterdayticket;
	}
	public Integer getYesterdayorder() {
		return yesterdayorder;
	}
	public void setYesterdayorder(Integer yesterdayorder) {
		this.yesterdayorder = yesterdayorder;
	}
	public Integer getYesterdasubscribe() {
		return yesterdasubscribe;
	}
	public void setYesterdasubscribe(Integer yesterdasubscribe) {
		this.yesterdasubscribe = yesterdasubscribe;
	}
	public Integer getSumsubscribe() {
		return sumsubscribe;
	}
	public void setSumsubscribe(Integer sumsubscribe) {
		this.sumsubscribe = sumsubscribe;
	}
	public Integer getSumorder() {
		return sumorder;
	}
	public void setSumorder(Integer sumorder) {
		this.sumorder = sumorder;
	}
	public Integer getSumticket() {
		return sumticket;
	}
	public void setSumticket(Integer sumticket) {
		this.sumticket = sumticket;
	}
}

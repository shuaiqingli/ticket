package com.hengyu.ticket.dao;

import java.util.Map;

public interface DataStatisticDao {

	//统计订单
	public Map<String,Integer> dataStatisticOrder(String todaybegin,String todayend,String yesterdaybegin,String yesterdayend ) throws Exception;
	//统计出票
	public Map<String,Integer> dataStatisticTicket(String todaybegin,String todayend,String yesterdaybegin,String yesterdayend) throws Exception;
	//统计关注数量
	public Map<String,Integer> dataStatisticFollow() throws Exception;
	//注册人数
	public Map<String,Integer> dataStatisticRegister(String todaybegin,String todayend,String yesterdaybegin,String yesterdayend) throws Exception;
	//按日期查询关注记录
	public Map<String,Integer> findByDate(String date) throws Exception;
	//保存关注记录
	public int saveWXFollow(Map<String,String> params) throws Exception;
	//更新关注数量
	public int updateWXFollow(String date) throws Exception;
	
}

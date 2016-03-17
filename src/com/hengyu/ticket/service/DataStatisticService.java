package com.hengyu.ticket.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.DataStatisticDao;
import com.hengyu.ticket.util.DateHanlder;

@Service
public class DataStatisticService {

	@Autowired
	private DataStatisticDao dsdao;
	
	//获取订单，出票，数据统计
	public Map<String,Object> getDataStatistic() throws Exception{
		Map<String,Object> data = new HashMap<String, Object>();
		SimpleDateFormat df = DateHanlder.getDateFromat();
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
//		c.add(Calendar.DAY_OF_MONTH, -1);
		c.add(Calendar.WEEK_OF_MONTH, -1);
		String tb = df.format(new Date())+" 00:00";
		String te = df.format(new Date())+" 23:59";
		String yb = df.format(c.getTime())+" 00:00";
		String ye = df.format(c.getTime())+" 23:59";
		Map<String, Integer> dataStatisticOrder = dsdao.dataStatisticOrder(tb,te,yb,ye);
		Map<String, Integer> dataStatisticTicket = dsdao.dataStatisticTicket(tb,te,yb,ye);
		Map<String, Integer> dataStatisticRegister = dsdao.dataStatisticRegister(tb,te,yb,ye);
		data.putAll(dataStatisticOrder);
		data.putAll(dataStatisticTicket);
		data.putAll(dataStatisticRegister);
		data.putAll(dsdao.dataStatisticFollow());
		return data;
	}
	
	//更新关注记录
	public int updateWXFollow() throws Exception{
		String date = DateHanlder.getCurrentDate();
		Map<String, Integer> map = dsdao.findByDate(date);
		if(map==null||map.isEmpty()==true){
			Map<String,String> m = new HashMap<String,String>();
			m.put("fdate",date);
			m.put("quantity","1");
			dsdao.saveWXFollow(m);
		}else{
			dsdao.updateWXFollow(date);
		}
		return 1;
	}
	
}

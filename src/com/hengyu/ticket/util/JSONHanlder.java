package com.hengyu.ticket.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JSONHanlder {

	/**
	 * 获取JSON数据
	 * @param key
	 * @param source
	 * @return
	 */
	public static String getVal(String key,String json){
		if(key!=null&&key.trim().isEmpty()==false&&key.indexOf(".")!=-1&&json!=null&&json.trim().isEmpty()==false){
			String[] ks = key.split("[.]");
			List<String> list = Arrays.asList(ks);
			return getVal(json,list.iterator());
		}else if(key!=null&&key.trim().isEmpty()==false){
			return JSONObject.fromObject(json).get(key).toString();
		}
		return null;
	}
	
	/**
	 * 将对象转换成 JSON
	 * @param obj
	 * @return
	 */
	public static String getObjectAsString(Object obj){
		return getObjectAsString(null,null,obj);
	}
	/**
	 * 将对象转换成 JSON
	 * @param obj
	 * @return
	 */
	public static String getObjectAsString(Object ... obj){
		return getObjectAsString(null,null,obj);
	}
	
	/**
	 * 将对象转换成 JSON
	 * @param parrent 时间格式
	 * @param excludes 不包含的字段
	 * @param obj
	 * @return
	 */
	public static String getObjectAsString(String parrent,String excludes,Object... obj){
		if(obj==null||obj.length==0){
			return "{}";
		}
		if(obj.length==1){
			if(obj[0] instanceof Collection){
				return JSONArray.toJSONString(obj[0]);
			}else{
				return JSONObject.fromObject(obj[0]).toString();
			}
		}
		Data data = new Data();
		data.getRecords().addAll(Arrays.asList(obj));
		return JSONObject.fromObject(data).toString();
	}
	
	/**
	 * 将对象转换成 JSON
	 * @param excludes
	 * @param obj
	 * @return
	 */
	public static String getObjectAsString(String excludes,Object... obj){
		return getObjectAsString(null,excludes,obj);
	}
	
	/**
	 * 将对象转换成 JSON
	 * @param parrent
	 * @param obj
	 * @return
	 */
	public static String getObjectAsString(Object parrent,Object... obj){
		if(parrent!=null){
			return getObjectAsString(parrent.toString(),null,obj);
		}
		return getObjectAsString(null,null,obj);
	}
	
	/**
	 * 获取JSON数据
	 * @param source
	 * @param ks
	 * @return
	 */
	private static String getVal(Object source,Iterator<String> ks){
		if(ks.hasNext()){
			source = JSONObject.fromObject(source).get(ks.next());
			if(source==null){
				return null;
			}
			return getVal(source,ks);
		}
		return  source.toString();
	}
	
	/**
	 * JSON 配置
	 * @author LGF
	 *
	 */
	public static class Config extends JsonConfig implements JsonValueProcessor {
		
		/**
		 * 日期格式化
		 */
		private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		/**
		 * 默认实例
		 */
		private static Config config = new Config();
		
		
		/**
		 * 默认构造方法
		 */
		private Config() {
			this.registerJsonValueProcessor(Date.class, this);
		}
		
		/**
		 * 构造方法
		 * @param parrent 日期格式化
		 * @param excludes 不包含的字段
		 */
		private Config(String parrent,String excludes) {
			if(parrent!=null){
				sdf = new SimpleDateFormat(parrent);
			}
			if(excludes!=null){
				this.setExcludes(excludes.split(","));
			}
			this.registerJsonValueProcessor(Date.class, this);
		}
		
		@Override
		public Object processArrayValue(Object date, JsonConfig cfg) {
			if(date instanceof Date){
				return sdf.format(date);
			}
			return null;
		}

		@Override
		public Object processObjectValue(String name, Object date,
				JsonConfig cfg) {
			if(date instanceof Date){
				return sdf.format(date);
			}
			return null;
		}
		
		/**
		 * 获取默认实例
		 * @return
		 */
		public static Config getDefaultInstance(){
			return config;
		}
		
		/**
		 * 日期格式化实例
		 * @param parrent
		 * @return
		 */
		public static Config getInstance(String parrent,String excludes){
			return new Config(parrent,excludes);
		}
		
	}
	
	/**
	 * JSON 数据储存实体
	 * @author LGF
	 *
	 */
	public static class Data{
		private List<Object> records = new ArrayList<Object>();

		public List<Object> getRecords() {
			return records;
		}
		public void setRecords(List<Object> records) {
			this.records = records;
		}
	}
}


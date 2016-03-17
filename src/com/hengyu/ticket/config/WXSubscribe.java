package com.hengyu.ticket.config;

import java.util.List;
import java.util.Map;

public class WXSubscribe {
	
	//标题
	private String title;
	//描述
	private String desc;
	//图片封面url
	private String picurl; 
	//原文url
	private String url;
	
	private List<Map<String,String>> items;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<Map<String, String>> getItems() {
		return items;
	}
	public void setItems(List<Map<String, String>> items) {
		this.items = items;
	}
}

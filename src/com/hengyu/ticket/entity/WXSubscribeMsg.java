package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2016-02-24 
  */
public class WXSubscribeMsg{

	//------------------字段-----------------
	
	//编号
	private Integer id;
	//图片链接
	private String picurl;
	//文章链接
	private String url;
	//标题
	private String title;
	//描述
	private String description;
	//创建时间
	private String makedate;
	//是否推送
	private Integer issend;
	//级别（排序）
	private Integer rank;

	//----------------构造方法----------------
	
	public WXSubscribeMsg(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getPicurl(){
		return picurl;
	}
	public void setPicurl(String picurl){
		this.picurl = picurl;
	}
	public String getUrl(){
		return url;
	}
	public void setUrl(String url){
		this.url = url;
	}
	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public String getMakedate(){
		return makedate;
	}
	public void setMakedate(String makedate){
		this.makedate = makedate;
	}
	public Integer getIssend(){
		return issend;
	}
	public void setIssend(Integer issend){
		this.issend = issend;
	}
	public Integer getRank(){
		return rank;
	}
	public void setRank(Integer rank){
		this.rank = rank;
	}
}
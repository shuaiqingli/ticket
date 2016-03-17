package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2015-12-02 
  */
public class Company{

	//------------------字段-----------------
	
	private Integer id;
	private String companyname;
	private String shortname;
	private String pyname;

	//----------------构造方法----------------
	
	public Company(){

	}

	//-------------- get/set方法 --------------
	
	public Integer getId(){
		return id;
	}
	public void setId(Integer id){
		this.id = id;
	}
	public String getCompanyname(){
		return companyname;
	}
	public void setCompanyname(String companyname){
		this.companyname = companyname;
	}
	public String getShortname(){
		return shortname;
	}
	public void setShortname(String shortname){
		this.shortname = shortname;
	}
	public String getPyname(){
		return pyname;
	}
	public void setPyname(String pyname){
		this.pyname = pyname;
	}
}
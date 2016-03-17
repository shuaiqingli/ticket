package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2015-11-09 
  */
public class BaseResource{

	//------------------字段-----------------
	
	//编号
	private Integer id;
	//资源类型
	private String tagname;
	
	private String pyname;
	//资源名称
	private String tagsubvalue;
	//简称
	private String shortname;
	//排序
	private Integer tagorder;
	//是否删除
	private Integer isdel;

	//----------------构造方法----------------
	
	public BaseResource(){

	}

	//-------------- get/set方法 --------------
	
	/**
	 *获取id 
	 * @return Integer  
	 */
	public Integer getId(){
		return id;
	}

	/**
	 *设置id 
	 * @param id 
 	 */
	public void setId(Integer id){
		this.id = id;
	}

	/**
	 *获取tagname 
	 * @return String  
	 */
	public String getTagname(){
		return tagname;
	}

	/**
	 *设置tagname 
	 * @param tagname 
 	 */
	public void setTagname(String tagname){
		this.tagname = tagname;
	}

	/**
	 *获取tagsubvalue 
	 * @return String  
	 */
	public String getTagsubvalue(){
		return tagsubvalue;
	}

	/**
	 *设置tagsubvalue 
	 * @param tagsubvalue 
 	 */
	public void setTagsubvalue(String tagsubvalue){
		this.tagsubvalue = tagsubvalue;
	}

	/**
	 *获取tagorder 
	 * @return Integer  
	 */
	public Integer getTagorder(){
		return tagorder;
	}

	/**
	 *设置tagorder 
	 * @param tagorder 
 	 */
	public void setTagorder(Integer tagorder){
		this.tagorder = tagorder;
	}

	/**
	 *获取isdel 
	 * @return Integer  
	 */
	public Integer getIsdel(){
		return isdel;
	}

	/**
	 *设置isdel 
	 * @param isdel 
 	 */
	public void setIsdel(Integer isdel){
		this.isdel = isdel;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getPyname() {
		return pyname;
	}

	public void setPyname(String pyname) {
		this.pyname = pyname;
	}
}
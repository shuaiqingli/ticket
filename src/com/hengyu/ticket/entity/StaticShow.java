package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2015-11-09 
  */
public class StaticShow{

	//------------------字段-----------------
	
	private Integer id;
	private String areamode;
	private String showcontent;
	private String makedate;
	private String showtitle;

	//----------------构造方法----------------
	
	public StaticShow(){

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
	 *获取areamode 
	 * @return String  
	 */
	public String getAreamode(){
		return areamode;
	}

	/**
	 *设置areamode 
	 * @param areamode 
 	 */
	public void setAreamode(String areamode){
		this.areamode = areamode;
	}

	/**
	 *获取showcontent 
	 * @return String  
	 */
	public String getShowcontent(){
		return showcontent;
	}

	/**
	 *设置showcontent 
	 * @param showcontent 
 	 */
	public void setShowcontent(String showcontent){
		this.showcontent = showcontent;
	}

	/**
	 *获取makedate 
	 * @return String  
	 */
	public String getMakedate(){
		return makedate;
	}

	/**
	 *设置makedate 
	 * @param makedate 
 	 */
	public void setMakedate(String makedate){
		this.makedate = makedate;
	}

	public String getShowtitle() {
		return showtitle;
	}

	public void setShowtitle(String showtitle) {
		this.showtitle = showtitle;
	}
}
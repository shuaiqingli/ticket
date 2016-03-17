package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2015-09-26 
  */
public class StorePutModel{

	//------------------字段-----------------
	
	//编号
	private String tLID;
	//线路编号
	private String lineID;
	//票数
	private Integer ticketQuantity;

	//----------------构造方法----------------
	
	public StorePutModel(){

	}

	//-------------- get/set方法 --------------
	
	/**
	 *获取tLID (编号)
	 * @return String  
	 */
	public String getTLID(){
		return tLID;
	}

	/**
	 *设置tLID (编号)
	 * @param tLID 
 	 */
	public void setTLID(String tLID){
		this.tLID = tLID;
	}

	/**
	 *获取lineID (线路编号)
	 * @return String  
	 */
	public String getLineid(){
		return lineID;
	}

	/**
	 *设置lineID (线路编号)
	 * @param lineID 
 	 */
	public void setLineID(String lineID){
		this.lineID = lineID;
	}

	/**
	 *获取ticketQuantity (票数)
	 * @return Integer  
	 */
	public Integer getTicketQuantity(){
		return ticketQuantity;
	}

	/**
	 *设置ticketQuantity (票数)
	 * @param ticketQuantity 
 	 */
	public void setTicketQuantity(Integer ticketQuantity){
		this.ticketQuantity = ticketQuantity;
	}

}
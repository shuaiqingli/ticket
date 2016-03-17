package com.hengyu.ticket.entity;

 /**
  * @author 李冠锋 2015-09-26 
  */
public class MakeConf{

	//------------------字段-----------------
	
	//表名
	private String tableName;
	//当前值
	private Integer currentValue;

	//----------------构造方法----------------
	
	public MakeConf(){

	}

	//-------------- get/set方法 --------------
	
	/**
	 *获取tableName (表名)
	 * @return String  
	 */
	public String getTableName(){
		return tableName;
	}

	/**
	 *设置tableName (表名)
	 * @param tableName 
 	 */
	public void setTableName(String tableName){
		this.tableName = tableName;
	}

	/**
	 *获取currentValue (当前值)
	 * @return Integer  
	 */
	public Integer getCurrentValue(){
		return currentValue;
	}

	/**
	 *设置currentValue (当前值)
	 * @param currentValue 
 	 */
	public void setCurrentValue(Integer currentValue){
		this.currentValue = currentValue;
	}

}
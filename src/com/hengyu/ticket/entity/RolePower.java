package com.hengyu.ticket.entity;

import java.util.List;

 /**
  * @author 李冠锋 2015-09-26 
  */
public class RolePower{

	//------------------字段-----------------
	
	//编号
	private Integer iD;
	//角色编号
	private Integer roleID;
	//菜单编号
	private Integer fMID;
	//是否删除
	private Integer isDel;
	
	//----------------构造方法----------------
	
	public RolePower(){

	}

	//-------------- get/set方法 --------------
	
	/**
	 *获取iD (编号)
	 * @return Integer  
	 */
	public Integer getID(){
		return iD;
	}

	/**
	 *设置iD (编号)
	 * @param iD 
 	 */
	public void setID(Integer iD){
		this.iD = iD;
	}

	/**
	 *获取roleID (角色编号)
	 * @return Integer  
	 */
	public Integer getRoleID(){
		return roleID;
	}

	/**
	 *设置roleID (角色编号)
	 * @param roleID 
 	 */
	public void setRoleID(Integer roleID){
		this.roleID = roleID;
	}

	/**
	 *获取fMID (菜单编号)
	 * @return Integer  
	 */
	public Integer getFMID(){
		return fMID;
	}

	/**
	 *设置fMID (菜单编号)
	 * @param fMID 
 	 */
	public void setFMID(Integer fMID){
		this.fMID = fMID;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
}
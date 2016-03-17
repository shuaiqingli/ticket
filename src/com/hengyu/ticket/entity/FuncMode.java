package com.hengyu.ticket.entity;

import java.util.List;

 /**
  * @author 李冠锋 2015-09-26 
  */
public class FuncMode{

	//------------------字段-----------------
	
	private Integer iD;
	//菜单名称
	private String menuName;
	//权限类
	private String menuClass;
	//菜单路径
	private String menuUrl;
	//父编号
	private Integer parentID;
	//优先级
	private Integer rank;
	
	//父级
	private FuncMode parent;
	
	//二级菜单
	private List<FuncMode> children;
	//是否删除
	private Integer isDel;

	//----------------构造方法----------------
	
	public FuncMode(){

	}

	//-------------- get/set方法 --------------
	
	/**
	 *获取iD 
	 * @return Integer  
	 */
	public Integer getID(){
		return iD;
	}

	/**
	 *设置iD 
	 * @param iD 
 	 */
	public void setID(Integer iD){
		this.iD = iD;
	}

	/**
	 *获取menuName (菜单名称)
	 * @return String  
	 */
	public String getMenuName(){
		return menuName;
	}

	/**
	 *设置menuName (菜单名称)
	 * @param menuName 
 	 */
	public void setMenuName(String menuName){
		this.menuName = menuName;
	}

	/**
	 *获取menuClass (权限类)
	 * @return String  
	 */
	public String getMenuClass(){
		return menuClass;
	}

	/**
	 *设置menuClass (权限类)
	 * @param menuClass 
 	 */
	public void setMenuClass(String menuClass){
		this.menuClass = menuClass;
	}

	/**
	 *获取menuUrl (菜单路径)
	 * @return String  
	 */
	public String getMenuUrl(){
		return menuUrl;
	}

	/**
	 *设置menuUrl (菜单路径)
	 * @param menuUrl 
 	 */
	public void setMenuUrl(String menuUrl){
		this.menuUrl = menuUrl;
	}

	/**
	 *获取parentID (父编号)
	 * @return Integer  
	 */
	public Integer getParentid(){
		return parentID;
	}

	/**
	 *设置parentID (父编号)
	 * @param parentID 
 	 */
	public void setParentid(Integer parentID){
		this.parentID = parentID;
	}

	/**
	 *获取rank (优先级)
	 * @return Integer  
	 */
	public Integer getRank(){
		return rank;
	}

	/**
	 *设置rank (优先级)
	 * @param rank 
 	 */
	public void setRank(Integer rank){
		this.rank = rank;
	}

	public List<FuncMode> getChildren() {
		return children;
	}

	public void setChildren(List<FuncMode> children) {
		this.children = children;
	}

	public FuncMode getParent() {
		return parent;
	}

	public void setParent(FuncMode parent) {
		this.parent = parent;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
}
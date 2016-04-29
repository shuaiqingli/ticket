package com.hengyu.ticket.entity;

import java.util.List;

 /**
  * @author 李冠锋 2015-09-26 
  */
public class Role{

	//------------------字段-----------------
	
	//角色 id
	private Integer id;
	//角色名称
	private String rolename;
	//菜单
	private List<FuncMode> menus;
	//角色与权限列表
	private List<RolePower> rolepowers;
	//----------------构造方法----------------
	
	public Role(){

	}

	//-------------- get/set方法 --------------


     public Integer getId() {
         return id;
     }

     public void setId(Integer id) {
         this.id = id;
     }

     public String getRolename() {
         return rolename;
     }

     public void setRolename(String rolename) {
         this.rolename = rolename;
     }

     public List<FuncMode> getMenus() {
         return menus;
     }

     public void setMenus(List<FuncMode> menus) {
         this.menus = menus;
     }

     public List<RolePower> getRolepowers() {
         return rolepowers;
     }

     public void setRolepowers(List<RolePower> rolepowers) {
         this.rolepowers = rolepowers;
     }
 }
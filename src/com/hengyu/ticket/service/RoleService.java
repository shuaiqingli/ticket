package com.hengyu.ticket.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hengyu.ticket.dao.RoleDao;
import com.hengyu.ticket.dao.RolePowerDao;
import com.hengyu.ticket.entity.Role;
import com.hengyu.ticket.entity.RolePower;
import org.springframework.util.Assert;

/**
 * 
 * @author 李冠锋 2015-09-26
 *
 */
 @Service
public class RoleService{
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private RolePowerDao rpDao;
	
	/**
	 * 保存一个对象
	 * @param role
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int save(Role role) throws Exception{
		int ret = roleDao.save(role);
		saveOrupdatePower(role);
		return ret;
	}

	/**
	 * 删除角色
	 * @param id
	 * @return
	 * @throws Exception
     */
	public int delete(Integer id) throws Exception{
		int ret = roleDao.delete(id);
		Assert.isTrue(ret==1,"删除角色失败！");
		return ret;
	}


	
	/**
	 * 更新或删除权限
	 * @param role
	 * @throws Exception
	 */
	public void saveOrupdatePower(Role role) throws Exception{
		List<RolePower> rolePowers = role.getRolepowers();
		if(rolePowers!=null){
			for (RolePower rp : rolePowers) {
				rp.setRoleID(role.getId());
				if(rp!=null&&rp.getIsDel()!=null){
					//删除
					if(rp.getIsDel()==1){
						rpDao.deleteByRoleFuncModel(rp);
					}
					//保存
					else{
						RolePower _rp = rpDao.findByRoleFuncModel(rp);
						if(_rp==null){
							rpDao.save(rp);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 更新一个对象
	 * @param role
	 * @return 返回受影响行数 (int)
	 * @throws Exception
	 */
	public int update(Role role) throws Exception{
		saveOrupdatePower(role);
		return roleDao.update(role);
	}
	
	/**
	 * 根据主键查询一个对象
	 * @param iD 主键
	 * @return 返回Role对象
	 * @throws Exception
	 */
	public Role find(Integer iD) throws Exception{
		return roleDao.find(iD);
	}
	
	/**
	 * 查询所有角色
	 * @return
	 * @throws Exception
	 */
	public List<Role> findAll() throws Exception{
		return roleDao.findAll();
	}
	
	/**
	 * 查询所有角色,菜单
	 * @return
	 * @throws Exception
	 */
	public List<Role> findList(Role role) throws Exception{
		return roleDao.findList(role);
	}
	
	public void setRoleDao(RoleDao roleDao){
		this.roleDao = roleDao;
	}
}

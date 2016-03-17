package com.hengyu.ticket.control;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengyu.ticket.entity.FuncMode;
import com.hengyu.ticket.entity.Page;
import com.hengyu.ticket.entity.Role;
import com.hengyu.ticket.entity.RolePower;
import com.hengyu.ticket.service.FuncModeService;
import com.hengyu.ticket.service.RoleService;

/**
 * 角色控制器
 * @author LGF 215-09-30
 *
 */
@RequestMapping("/admin")
@Controller
public class RoleControl {
	
	@Autowired
	private RoleService rs;
	
	@Autowired
	private FuncModeService fs;
	
	//前置方法
	@ModelAttribute
	public void before(Model m,Role role) throws Exception{
		if(role!=null&&role.getId()!= null){
			role = rs.find(role.getId());
			List<FuncMode> roleFuncModes = fs.findMeunByRole(role.getId());
			if(roleFuncModes!=null){
				m.addAttribute("roleFuncModes",roleFuncModes);
			}
			m.addAttribute(role);
		}
	}

	//角色列表
	@RequestMapping("rolelist.do")
	public String roleList(Model m,Page page,Role role) throws Exception{
		List<Role> roles = rs.findList(role);
		page.setParam(role);
		page.setData(roles);
		if(roles!=null){
			m.addAttribute("roles", roles);
			page.setTotalCount(roles.size());
		}
		return "admin/roleList";
	}
	
	//编辑角色
	@RequestMapping(value={"roleadd.do","roleupdate.do"})
	public String roleEditPage(Model m,Role role) throws Exception{
		List<FuncMode> funcModes = fs.findMeunByRole(null);
		if(funcModes!=null){
			m.addAttribute("funcModes",funcModes);
		}
		return "admin/roleedit";
	}
	
	//更新或保存角色
	@RequestMapping(value="roleedit.do")
	@ResponseBody
	public String roleEdit(Role role) throws Exception{
		Integer result = -1; 
		//update
		if(role!=null&&role.getId()!=null){
			result = rs.update(role);
		}
		//save
		else if(role!=null&&role.getId()==null){
			result = rs.save(role);
		}
		return String.valueOf(result);
	}

	//删除角色
	@RequestMapping(value="deleteRole.do")
	@ResponseBody
	public String deleteRole(Integer id) throws Exception{
		Assert.notNull(id,"角色编号不能为空！");
		Integer result = -1;
		result = rs.delete(id);
		return String.valueOf(result);
	}

}

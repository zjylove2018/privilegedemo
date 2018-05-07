package cn.itcast.oa.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.domain.Role;

/**
 * 岗位管理Action
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
	private Long[] privilegeIds;//属性驱动，权限id
	
	/**
	 * 查询岗位列表
	 */
	public String list(){
		List<Role> list = roleService.findAll();
		getValueStack().set("list", list);
		return "list";
	}
	
	/**
	 * 根据id删除岗位
	 */
	public String delete(){
		roleService.delete(model);
		return "toList";
	}
	
	/**
	 * 跳转到添加岗位页面
	 */
	public String saveUI(){
		return "saveUI";
	}
	
	/**
	 * 添加岗位
	 */
	public String save(){
		roleService.save(model);
		return "toList";
	}
	
	/**
	 * 跳转到修改岗位页面
	 */
	public String updateUI(){
		Role role = roleService.findRoleById(model.getId());
		getValueStack().push(role);
		return "updateUI";
	}
	
	/**
	 * 修改岗位
	 */
	public String update(){
		//先查询，再修改
		Role role = roleService.findRoleById(model.getId());
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		
		roleService.update(role);
		return "toList";
	}
	
	/**
	 * 根据name查询岗位名称是否存在，如果存在返回1 ，否则返回0
	 */
	public String checkRoleName(){
		List<Role> list = roleService.findRoleByName(model.getName());
		
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		PrintWriter writer = null;
		try {
			writer = ServletActionContext.getResponse().getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(list != null && list.size() > 0){
			//当前岗位名称已经存在
			writer.print("1");
		}else{
			//当前岗位名称不存在
			writer.print("0");
		}
		
		return NONE;
	}
	
	/**
	 * 跳转到设置权限页面
	 */
	public String setPrivilegeUI(){
		//根据id查询当前要设置权限的角色，放入值栈
		Role role = roleService.findRoleById(model.getId());
		getValueStack().push(role);
		
		//查询权限数据，用于页面展示，放入值栈
		//List<Privilege> privilegeList = privilegeService.findAll();
		List<Privilege> privilegeList = privilegeService.findTopList();
		getValueStack().set("privilegeList", privilegeList);
		
		//获取当前角色对应的权限集合
		Set<Privilege> privileges = role.getPrivileges();
		if(privileges != null && privileges.size() > 0){
			privilegeIds = new Long[privileges.size()];
			int i = 0;
			for(Privilege p : privileges){
				privilegeIds[i++] = p.getId();
			}
		}
		
		return "setPrivilegeUI";
	}
	
	/**
	 * 为角色设置权限
	 */
	public String setPrivilege(){
		Role role = roleService.findRoleById(model.getId());
		
		if(privilegeIds != null && privilegeIds.length > 0){
			List<Privilege> privilegeList = privilegeService.findPrivilegesByIds(privilegeIds);
			role.setPrivileges(new HashSet<Privilege>(privilegeList));//角色关联权限
		}else{
			role.setPrivileges(null);//将角色对应的权限清除
		}
		
		roleService.update(role);
		
		return "toList";
	}

	public void setPrivilegeIds(Long[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

	public Long[] getPrivilegeIds() {
		return privilegeIds;
	}

}

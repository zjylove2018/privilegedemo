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
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.utils.DepartmentUtils;
import cn.itcast.oa.utils.MD5Utils;

/**
 * 用户管理Action
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class UserAction_bak extends BaseAction<User>{
	private Long departmentId;//属性驱动，部门id
	private Long[] roleIds;//属性驱动，岗位id
	/**
	 * 查询用户列表
	 */
	public String list(){
		List<User> list = userService.findAll();
		getValueStack().set("userList", list);
		return "list";
	}
	
	/**
	 * 根据id删除用户
	 */
	public String delete(){
		userService.delete(model);
		return "toList";
	}
	
	/**
	 * 跳转到添加用户页面
	 */
	public String saveUI(){
		//准备数据：树形结构的部门列表数据
		List<Department> topList = departmentService.findTopList();
		List<Department> treeList = DepartmentUtils.getTreeList(topList, null);
		getValueStack().set("deptList", treeList);
		
		//准备数据：岗位列表数据
		List<Role> roleList = roleService.findAll();
		getValueStack().set("roleList", roleList);
		
		return "saveUI";
	}
	
	/**
	 * 保存用户
	 */
	public String save(){
		if(departmentId != null){
			Department dept = departmentService.findDepartmentById(departmentId);
			model.setDepartment(dept);//用户关联部门
		}
		
		if(roleIds != null && roleIds.length > 0){
			List<Role> roleList = roleService.findRolesByIds(roleIds);
			model.setRoles(new HashSet<Role>(roleList));//用户关联岗位
		}
		
		userService.save(model);
		return "toList";
	}

	/**
	 * 跳转到修改用户页面
	 */
	public String updateUI(){
		//准备数据：树形结构的部门列表数据
		List<Department> topList = departmentService.findTopList();
		List<Department> treeList = DepartmentUtils.getTreeList(topList, null);
		getValueStack().set("deptList", treeList);
		
		//准备数据：岗位列表数据
		List<Role> roleList = roleService.findAll();
		getValueStack().set("roleList", roleList);
		
		User user = userService.findUserById(model.getId());
		getValueStack().push(user);
		
		if(user.getDepartment() != null){
			departmentId = user.getDepartment().getId();
		}
		
		Set<Role> roles = user.getRoles();
		if(roles != null && roles.size() > 0){
			int c = 0;
			roleIds = new Long[roles.size()];
			for(Role r : roles){
				roleIds[c++] = r.getId();
			}
		}
		
		return "updateUI";
	}
	
	/**
	 * 修改用户
	 */
	public String update(){
		//先查询，再修改
		User user = userService.findUserById(model.getId());
		user.setLoginName(model.getLoginName());
		user.setGender(model.getGender());
		user.setPhoneNumber(model.getPhoneNumber());
		user.setEmail(model.getEmail());
		user.setDescription(model.getDescription());
		
		if(departmentId != null){
			Department dept = departmentService.findDepartmentById(departmentId);
			user.setDepartment(dept);//设置当前用户所在部门
		}else{
			user.setDepartment(null);//设置当前用户没有部门
		}
		
		if(roleIds != null && roleIds.length > 0){
			List<Role> roleList = roleService.findRolesByIds(roleIds);
			user.setRoles(new HashSet<Role>(roleList));//设置当前用户对应的岗位
		}else{
			user.setRoles(null);//设置当前用户没有任何岗位
		}
		
		userService.update(user);
		
		return "toList";
	}
	
	/**
	 * 初始化密码
	 */
	public String reSetPassword(){
		User user = userService.findUserById(model.getId());
		user.setPassword(MD5Utils.md5("1234"));
		
		userService.update(user);
		
		return "toList";
	}
	
	/**
	 * 检查登录名是否存在
	 * @throws IOException 
	 */
	public String checkLoginName() {
		List<User> userList = userService.findUserByLoginName(model.getLoginName());
		
		ServletActionContext.getResponse().setContentType("text/html;charset=UTF-8");
		PrintWriter writer = null;
		try {
			writer = ServletActionContext.getResponse().getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(userList != null && userList.size() > 0){
			//当前输入的登录名已经存在,返回1
			writer.print("1");
		}else{
			//当前输入的登录名不存在，返回0
			writer.print("0");
		}
		
		
		return NONE;
	}
	
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}
}

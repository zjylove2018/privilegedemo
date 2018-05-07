package cn.itcast.oa.web.action;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.utils.DepartmentUtils;

/**
 * 部门管理Action
 * @author zhaoqx
 *
 */
@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department>{
	private Long parentId;//属性驱动，上级部门id
	
	/**
	 * 查询部门列表
	 */
	public String list(){
		List<Department> list = null;
		if(parentId == null){
			//查询顶级部门列表
			list = departmentService.findTopList();
		}else{
			//根据parentId查询下级部门列表
			list = departmentService.findChildren(parentId);
			Department dept = departmentService.findDepartmentById(parentId);
			getValueStack().set("dept", dept);
		}
		
		getValueStack().set("list", list);
		return "list";
	}
	
	/**
	 * 根据id删除部门
	 */
	public String delete(){
		Department dept = departmentService.findDepartmentById(model.getId());
		if(dept.getParent() != null){
			parentId = dept.getParent().getId();//设置当前删除部门的上级部门id用于页面跳转
		}
		departmentService.delete(model);
		
		return "toList";
	}
	
	/**
	 * 跳转到添加部门页面
	 */
	public String saveUI(){
		//准备数据：部门列表数据 
		//List<Department> list = departmentService.findAll();
		List<Department> list = departmentService.findTopList();
		List<Department> treeList = DepartmentUtils.getTreeList(list,null);
		getValueStack().set("deptList", treeList);
		return "saveUI";
	}
	
	/**
	 * 添加部门
	 */
	public String save(){
		if(parentId != null){
			Department parent = departmentService.findDepartmentById(parentId);
			model.setParent(parent);//子部门关联上级部门
		}
		departmentService.save(model);
		return "toList";
	}
	
	/**
	 * 跳转到修改部门页面
	 */
	public String updateUI(){
		//根据id查询部门，用于页面回显
		Department dept = departmentService.findDepartmentById(model.getId());
		getValueStack().push(dept);
		
		//准备数据：部门列表数据 
		//List<Department> list = departmentService.findAll();
		List<Department> list = departmentService.findTopList();
		List<Department> treeList = DepartmentUtils.getTreeList(list,dept.getId());
		getValueStack().set("deptList", treeList);
		
		if(dept.getParent() != null){
			parentId = dept.getParent().getId();
		}
		return "updateUI";
	}
	
	/**
	 * 修改部门
	 */
	public String update(){
		//先查询，再修改
		Department dept = departmentService.findDepartmentById(model.getId());
		dept.setName(model.getName());
		dept.setDescription(model.getDescription());
		
		if(parentId == null){
			//设置当前部门为顶级部门
			dept.setParent(null);
		}else{
			Department parent = departmentService.findDepartmentById(parentId);
			dept.setParent(parent);//设置当前部门的上级部门
		}
		
		departmentService.update(dept);
		
		return "toList";
	}
	
	/**
	 * 查询部门树形列表数据，返回json格式
	 */
	public String findDepartmentTreeList(){
		List<Department> topList = departmentService.findTopList();
		List<Department> treeList = DepartmentUtils.getTreeList(topList, null);
		
		JsonConfig jc = new JsonConfig();
		jc.setExcludes(new String[]{"children","users","description","parent"});
		
		JSONArray jsonArray = JSONArray.fromObject(treeList,jc);
		System.out.println(jsonArray);
		
		ServletActionContext.getResponse().setContentType("text/json;charset=UTF-8");
		try {
			ServletActionContext.getResponse().getWriter().print(jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

}

package cn.itcast.oa.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.dao.IDepartmentDao;
import cn.itcast.oa.domain.Department;

/**
 * 部门管理Service
 * @author zhaoqx
 *
 */
@Service
@Transactional
public class DepartmentServiceImpl implements IDepartmentService {
	@Resource
	private IDepartmentDao departmentDao;
	
	/**
	 * 查询部门列表
	 */
	@Transactional(readOnly=true)
	public List<Department> findAll() {
		return departmentDao.findAll();
	}

	/**
	 * 删除部门 
	 */
	public void delete(Department model) {
		departmentDao.delete(model.getId());
	}

	/**
	 * 根据id查询部门
	 */
	public Department findDepartmentById(Long parentId) {
		return departmentDao.findById(parentId);
	}
	
	/**
	 * 添加部门
	 */
	public void save(Department model) {
		departmentDao.save(model);
	}

	/**
	 * 修改部门 
	 */
	public void update(Department dept) {
		departmentDao.update(dept);
	}

	/**
	 * 查询顶级部门列表
	 */
	public List<Department> findTopList() {
		return departmentDao.findTopList();
	}

	/**
	 * 查询指定部门的下级部门列表
	 */
	public List<Department> findChildren(Long parentId) {
		return departmentDao.findChildren(parentId);
	}

}

package cn.itcast.oa.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.itcast.oa.base.BaseDaoImpl;
import cn.itcast.oa.domain.Department;

/**
 * 部门管理Dao
 * 
 * @author zhaoqx
 * 
 */
@Repository
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements
		IDepartmentDao {
	/**
	 * 查询顶级部门列表
	 */
	public List<Department> findTopList() {
		String hql = "FROM Department d WHERE d.parent IS NULL ORDER BY d.id DESC";
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}

	/**
	 * 查询指定部门的下级部门列表
	 */
	public List<Department> findChildren(Long parentId) {
		String hql = "FROM Department d WHERE d.parent.id = ? ORDER BY d.id DESC";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, parentId);
		return query.list();
	}
}

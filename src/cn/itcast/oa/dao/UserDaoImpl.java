package cn.itcast.oa.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.itcast.oa.base.BaseDaoImpl;
import cn.itcast.oa.domain.User;

/**
 * 用户管理Dao
 * @author zhaoqx
 *
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao{

	/**
	 * 根据登录名查询用户
	 */
	public List<User> findUserByLoginName(String loginName) {
		String hql = "FROM User u WHERE u.loginName = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, loginName);
		return query.list();
	}

	/**
	 * 根据登录名和密码查询用户
	 */
	public User findUserByLoginNameAndPassword(String loginName, String password) {
		String hql = "FROM User u WHERE u.loginName = ? AND u.password = ?";
		Query query = this.getSession().createQuery(hql);
		query.setParameter(0, loginName);
		query.setParameter(1, password);
		return (User) query.uniqueResult();
	}

}

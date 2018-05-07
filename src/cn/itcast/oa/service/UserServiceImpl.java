package cn.itcast.oa.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.dao.IUserDao;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.utils.MD5Utils;

/**
 * 用户管理Service
 * @author zhaoqx
 *
 */
@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;
	/**
	 * 查询用户列表
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}
	
	/**
	 * 删除用户
	 */
	public void delete(User model) {
		userDao.delete(model.getId());
	}

	/**
	 * 保存用户
	 */
	public void save(User model) {
		model.setPassword(MD5Utils.md5("1234"));//默认密码为1234
		userDao.save(model);
	}

	/**
	 * 根据id查询用户
	 */
	public User findUserById(Long id) {
		return userDao.findById(id);
	}

	/**
	 * 根据id修改用户
	 */
	public void update(User user) {
		userDao.update(user);
	}

	/**
	 * 根据登录名查询用户
	 */
	public List<User> findUserByLoginName(String loginName) {
		return userDao.findUserByLoginName(loginName);
	}

	/**
	 * 用户登录
	 */
	public User login(User model) {
		if(model.getLoginName() == null || model.getPassword() == null){
			return null;
		}
		User user = userDao.findUserByLoginNameAndPassword(model.getLoginName(),MD5Utils.md5(model.getPassword()));
		return user;
	}
}

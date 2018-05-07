package cn.itcast.oa.dao;

import java.util.List;

import cn.itcast.oa.base.IBaseDao;
import cn.itcast.oa.domain.User;

public interface IUserDao extends IBaseDao<User> {

	public List<User> findUserByLoginName(String loginName);

	public User findUserByLoginNameAndPassword(String loginName, String md5);

}

package cn.itcast.oa.dao;

import java.util.List;

import cn.itcast.oa.base.IBaseDao;
import cn.itcast.oa.domain.Role;

public interface IRoleDao extends IBaseDao<Role>{

	public List<Role> findRoleByName(String name);
}

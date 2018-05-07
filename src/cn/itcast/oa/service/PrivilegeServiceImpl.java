package cn.itcast.oa.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.dao.IPrivilegeDao;
import cn.itcast.oa.domain.Privilege;

/**
 * 权限操作Service
 * @author zhaoqx
 *
 */
@Service
@Transactional
public class PrivilegeServiceImpl implements IPrivilegeService{
	@Resource
	private IPrivilegeDao privilegeDao;
	/**
	 * 查询所有权限数据
	 */
	public List<Privilege> findAll() {
		return privilegeDao.findAll();
	}
	
	/**
	 * 根据权限id数组查询多个权限
	 */
	public List<Privilege> findPrivilegesByIds(Long[] privilegeIds) {
		return privilegeDao.findByIds(privilegeIds);
	}

	/**
	 * 查询顶级权限列表
	 */
	public List<Privilege> findTopList() {
		return privilegeDao.findTopList();
	}

	/**
	 * 查询所有需要进行校验的请求url
	 */
	public List<String> findAllUrls() {
		// TODO Auto-generated method stub
		return privilegeDao.findAllUrls();
	}

}

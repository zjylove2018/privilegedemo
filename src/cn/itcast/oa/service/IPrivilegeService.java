package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.domain.Privilege;

public interface IPrivilegeService {

	public List<Privilege> findAll();

	public List<Privilege> findPrivilegesByIds(Long[] privilegeIds);

	public List<Privilege> findTopList();

	public List<String> findAllUrls();

}

package edu.tsinghua.dmcs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.tsinghua.dmcs.entity.Role;
import edu.tsinghua.dmcs.mapper.RoleMapper;
import edu.tsinghua.dmcs.service.RoleService;

@Component
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleMapper roleMapper;
	
	public List<Role> getRoleListByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer setRoleForUser(Long roleId, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Role> listAllRoles() {
		return roleMapper.listAllRoles();
	}

	public Integer removeRoleForUser(Long roleId, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}

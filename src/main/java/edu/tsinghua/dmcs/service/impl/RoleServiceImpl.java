package edu.tsinghua.dmcs.service.impl;

import java.util.List;

import edu.tsinghua.dmcs.entity.UserRoleMapping;
import edu.tsinghua.dmcs.mapper.UserRoleMappingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.tsinghua.dmcs.entity.Role;
import edu.tsinghua.dmcs.mapper.RoleMapper;
import edu.tsinghua.dmcs.service.RoleService;

@Component
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleMapper roleMapper;

	@Autowired
	UserRoleMappingMapper userRoleMappingMapper;

	public List<Role> getRoleListByUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Role> getRoleListByUserName(String userName) {

		return roleMapper.getRoleListByUserName(userName);
	}

	public Integer setRoleForUser(Long roleId, Long userId) {
		UserRoleMapping urm = new UserRoleMapping();
		urm.setRoleId(roleId);
		urm.setUserId(userId);
		int num = userRoleMappingMapper.insert(urm);
		return num;
	}

	public List<Role> listAllRoles() {
		return roleMapper.listAllRoles();
	}

	public Integer removeRoleForUser(Long roleId, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}

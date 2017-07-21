package edu.tsinghua.dmcs.service;

import java.util.List;

import edu.tsinghua.dmcs.entity.Role;

public interface RoleService {

	public List<Role> getRoleListByUserName(String userName);
	
	public Integer setRoleForUser(Long roleId, Long userId);
	
	public List<Role> listAllRoles();
	
	public Integer removeRoleForUser(Long roleId, Long userId);
	
}

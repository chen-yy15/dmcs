package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.Role;

public interface RoleService {

	public Role [] getRoleListByUserId(Long userId);
}

package edu.tsinghua.dmcs.web;

import java.util.List;

import edu.tsinghua.dmcs.mapper.UserRoleMappingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.entity.Role;
import edu.tsinghua.dmcs.interceptor.DmcsController;
import edu.tsinghua.dmcs.service.RoleService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/dmcs/api/admin")
public class AdminRestController {
	
	Logger logger = LoggerFactory.getLogger(AdminRestController.class);
	
	@Autowired
	private RoleService roleService;
	
	@DmcsController(loginRequired=true, roleAllowed = "administrator", description = "指定角色给用户")
    @ApiOperation(value="assignRole", notes="指定用户设置角色")
	@RequestMapping(value = "/assignRole", method = RequestMethod.GET)
	public Response assignRole(@RequestParam Long userId, @RequestParam  Long roleId) {
		int num = roleService.setRoleForUser(roleId, userId);
		return Response.SUCCESS().setData(num);
	}
	
	
	@DmcsController(loginRequired=true, roleAllowed = "administrator")
    @ApiOperation(value="listAllRoles", notes="指定用户设置角色")
	@RequestMapping(value = "/listAllRoles", method = RequestMethod.GET)
	public Response listAllRoles() {
		List<Role> rlist = roleService.listAllRoles();
		return Response.SUCCESS().setData(rlist);
	}

}

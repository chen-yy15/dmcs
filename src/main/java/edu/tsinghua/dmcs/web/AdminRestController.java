package edu.tsinghua.dmcs.web;

import com.alibaba.fastjson.JSONObject;
import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.entity.AdminGroup;
import edu.tsinghua.dmcs.entity.Role;
import edu.tsinghua.dmcs.entity.User;
import edu.tsinghua.dmcs.interceptor.DmcsController;
import edu.tsinghua.dmcs.service.AdminGroupService;
import edu.tsinghua.dmcs.service.RoleService;
import edu.tsinghua.dmcs.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value="/dmcs/api/v1/admin")
public class AdminRestController {

	private static final Logger logger = LoggerFactory.getLogger(AdminRestController.class);
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private AdminGroupService adminGroupService;
	@Autowired
	private UserService userService;
	
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


	@DmcsController(loginRequired=false)
	@ApiOperation(value="addAdminuser", notes="添加管理员")
	@RequestMapping(value = "/addAdminuser", method = RequestMethod.POST)
	public Response addAdminuser(@RequestBody String body) throws ParseException {
		JSONObject o = JSONObject.parseObject(body);
		String userid = o.getString("userid");
		String Userid = o.getString("Userid");
		int checkifhost = adminGroupService.checkifhost(Userid);
		if(checkifhost == 0) {
			return Response.FAILWRONG();
		}//判断是否是0号管理员
		if(adminGroupService.checkexistUser(userid)!=null){
			return Response.FAILWRONG();
		}//判断用户是否已经存在
		AdminGroup adminGroup = new AdminGroup();
		adminGroup.setUserid(userid);
		adminGroup.setAuthorityNumber(0);
		int num = adminGroupService.addAdminuser(adminGroup);
		if( num!=0 ) { //需要对人的身份信息进行更新
			User user = userService.getUserByuserid(userid);
			if( user!=null ){
				user.setCurrentAuthority("admin");
				if(userService.update(user)!=0)
					return Response.SUCCESSOK();
			}
		}
		return Response.FAILWRONG();
	}

	@DmcsController(loginRequired=false)
	@ApiOperation(value="deleteAdminuser", notes="删除管理员")
	@RequestMapping(value = "/deleteAdminuser", method = RequestMethod.POST)
	public Response deleteAdminuser(@RequestBody String body) throws ParseException {
		JSONObject o = JSONObject.parseObject(body);
		String userid = o.getString("userid");
		String Userid = o.getString("Userid");
		int checkifhost = adminGroupService.checkifhost(Userid);
		if(checkifhost == 0 ) {
			return Response.FAILWRONG();
		}//判断是否是管理员
		int num = adminGroupService.deleteByuserid(userid);
		if(num !=0){
			//还需要对该人的身份信息进行更新
			User user = userService.getUserByuserid(userid);
			if(user!=null){
				user.setCurrentAuthority("admin");
				if(userService.update(user)!=0)
					return Response.SUCCESSOK();
			}
		}
		return Response.FAILWRONG();
	}

	@DmcsController(loginRequired=false)
	@ApiOperation(value="changeAuthority", notes="更新权限")
	@RequestMapping(value="/changeAuthority", method=RequestMethod.POST)
	public Response changeAuthority (@RequestBody String body)throws ParseException{
		JSONObject o = JSONObject.parseObject(body);
		String userid = o.getString("userid");
		String Userid = o.getString("Userid");
		String authority = o.getString("authority");
		int checkifhost = adminGroupService.checkifhost(Userid);
		if(checkifhost==0){
			return Response.FAILWRONG();
		}
		AdminGroup adminGroup = adminGroupService.selectUser(userid);
		if(adminGroup!=null){
			adminGroup.setAuthorityNumber(Integer.parseInt(authority));
			if(adminGroupService.updateAdminuser(adminGroup)!=0){
				return Response.SUCCESSOK();
			}//这里需要注意针对于0号权限的更改问题的处理;
		}
		return Response.FAILWRONG();
	}
    @DmcsController(loginRequired = false)
	@ApiOperation(value="getAdminuser", notes = "获取管理员信息")
	@RequestMapping(value = "/getAdminuser", method = RequestMethod.POST)
	public Response getAdminuser(@RequestBody String body) throws ParseException {
		JSONObject o = JSONObject.parseObject(body);
		String Userid = o.getString("Userid");
		int checkifhost= adminGroupService.checkifhost(Userid);
		if( checkifhost == 0 )
			return Response.FAILWRONG();
		//这里需要返回用户和表的全部信息
		return Response.FAILWRONG();
	}


}

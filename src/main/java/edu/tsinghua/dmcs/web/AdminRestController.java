package edu.tsinghua.dmcs.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.entity.AdminGroup;
import edu.tsinghua.dmcs.entity.AdminGroupUser;
import edu.tsinghua.dmcs.entity.Role;
import edu.tsinghua.dmcs.entity.User;
import edu.tsinghua.dmcs.interceptor.DmcsController;
import edu.tsinghua.dmcs.service.AdminGroupService;
import edu.tsinghua.dmcs.service.RoleService;
import edu.tsinghua.dmcs.service.UserService;
import edu.tsinghua.dmcs.util.TockenCache;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
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
	@Autowired
	private TockenCache tockenCache;

	@Value("${security.sault.login}")
	private String securitySault;
	
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
		if(o == null ){
			return Response.FAILWRONG().setMsg("发送失败");
		}
		String Userid = o.getString("Userid");
		JSONArray Userids = o.getJSONArray("userids");
		if( Userids==null || Userid==null){
			return Response.FAILWRONG().setMsg("发送失败");
		}
		int checkifhost = adminGroupService.checkifhost(Userid);
		if(checkifhost == 0) {
			return Response.FAILWRONG().setMsg("身份不正确");
		}//判断是否是0号管理员

		JSONObject object = (JSONObject) Userids.get(0);
		String userid= object.getString("userid");

		if(adminGroupService.checkexistUser(userid)!=null){
			return Response.FAILWRONG().setMsg("用户已存在");
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
		return Response.FAILWRONG().setMsg("用户添加失败");
	}

	@DmcsController(loginRequired=false)
	@ApiOperation(value="deleteAdminuser", notes="删除管理员")
	@RequestMapping(value = "/deleteAdminuser", method = RequestMethod.POST)
	public Response deleteAdminuser(@RequestBody String body) throws ParseException {
		//JSONArray o = JSON.parseArray(body);
		JSONObject o = JSONObject.parseObject(body);
		JSONArray userids = o.getJSONArray("userids");
		String Userid = o.getString("Userid");
		if( Userid==null || userids==null){
			return Response.FAILWRONG().setMsg("信息缺失");
		}
		int checkifhost = adminGroupService.checkifhost(Userid);
		if(checkifhost == 0 ) {
			return Response.FAILWRONG().setMsg("身份不正确");
		}//判断是否是管理员
		for(int i=0; i<userids.size();i++){
			JSONObject object = (JSONObject) userids.get(i);
			String userid = object.getString("userid");
			User user = userService.getUserByuserid(userid);
			if(user!=null){
				user.setCurrentAuthority("user");
				if(userService.update(user)==0)
					return Response.FAILWRONG().setMsg("删除管理员失败");
				if( adminGroupService.deleteByuserid(userid) == 0 ){
					return Response.FAILWRONG().setMsg("删除管理员失败");
				}
			}
			else
				return Response.FAILWRONG().setMsg("用户不存在");
		}
		List<AdminGroupUser> adminGroupUsers = adminGroupService.selectadmingroup();
		return Response.SUCCESSOK().setData(adminGroupUsers);
	}

	@DmcsController(loginRequired=false)
	@ApiOperation(value="changeAuthority", notes="更新权限")
	@RequestMapping(value="/changeAuthority", method=RequestMethod.POST)
	public Response changeAuthority (@RequestBody String body)throws ParseException{
		JSONObject o = JSONObject.parseObject(body);
		String Userid = o.getString("Userid");
		JSONArray Users = o.getJSONArray("Users");
		if(Userid==null||Users==null){
			return Response.FAILWRONG().setMsg("信息缺失");
		}
		int checkifhost = adminGroupService.checkifhost(Userid);
		if(checkifhost==0){
			return Response.FAILWRONG().setMsg("身份不正确");
		}
		int i =0;
		int fail=0;
		for (;i<Users.size();i++){
			JSONObject object = (JSONObject) Users.get(i);
			String userid = object.getString("userid");
			String auth_string = object.getString("authority");
			AdminGroup adminGroup = adminGroupService.selectUser(userid);
			if(adminGroup!=null){
				adminGroup.setAuthorityNumber(Integer.parseInt(auth_string));
				int num = adminGroupService.updateAdminuser(adminGroup);
				if(num==0)
					fail++;
			}
		}
		if(fail==0)
		    return Response.SUCCESSOK().setMsg("更新成功");
		return Response.FAILWRONG().setMsg(fail+"个用户更新失败");
	}
    @DmcsController(loginRequired = false)
	@ApiOperation(value="getAdminuser", notes = "获取管理员信息")
	@RequestMapping(value = "/getAdminuser", method = RequestMethod.POST)
	public Response getAdminuser(@RequestBody String body) throws ParseException {
		JSONObject o = JSONObject.parseObject(body);
		String Userid = o.getString("Userid");
		if(Userid==null){
			return Response.FAILWRONG().setMsg("信息缺失");
		}
		int checkifhost= adminGroupService.checkifhost(Userid);
		if( checkifhost == 0 )
			return Response.FAILWRONG().setMsg("身份不正确");
		List<AdminGroupUser> adminGroupUsers = adminGroupService.selectadmingroup();

		return Response.SUCCESSOK().setData(adminGroupUsers);
		//这里需要返回用户和表的全部信息
		//return Response.FAILWRONG().setMsg("信息获取失败");
	}
	@DmcsController(loginRequired = false)
	@ApiOperation(value="getSelfuser", notes = "获取管理员自身信息")
	@RequestMapping(value = "/getSelfuser", method = RequestMethod.POST)
	public  Response getSelfuser(@RequestBody String body ,
								 HttpServletResponse response) throws ParseException{
		JSONObject o = JSONObject.parseObject(body);
		String Userid = o.getString("Userid");
		String admin_token = o.getString("admin_token");
		if(Userid!=null) {
		   AdminGroup adminGroup = adminGroupService.selectUser(Userid);
		   if(adminGroup==null) {
			   return Response.FAILWRONG().setMsg("用户不存在");
		   }//首次登录采用账号Userid登录
		   if(admin_token==null) {
			   try {
				   String token = this.getToken(adminGroup.getUserid());
				   String tem_token = URLEncoder.encode(token);
				   Cookie cookie = new Cookie("admin_token", tem_token);
				   cookie.setMaxAge(3600);
				   cookie.setPath("/");
				   response.addCookie(cookie);
				   tockenCache.setTokenForUser(token,adminGroup.getUserid());
			   } catch (Exception e) {
				   return Response.FAILWRONG().setMsg("cookie设置失败");
			   }
			   return Response.SUCCESSOK().setData(adminGroup).setMsg("首次申请成功");
		   }
		   return Response.FAILWRONG().setMsg("请求既有Userid又有token存在申请错误");
		}//首次登录采用Userid的方式进行登录。如果两种方式均存在问题，则直接进行跳转操作;
		if(Userid==null||admin_token!=null){
			admin_token= URLDecoder.decode(admin_token);
			String userid = tockenCache.getUserid(admin_token);
			if(userid!=null){
				AdminGroup adminGroup = adminGroupService.selectUser(userid);
				if(adminGroup==null) {
					return Response.FAILWRONG().setMsg("用户不存在");
				}
				return Response.SUCCESSOK().setData(adminGroup);
			}
		}
		return Response.FAILWRONG();
	}

	private String  getToken(String Userid) {
		String token = Userid + this.securitySault + System.currentTimeMillis();
		String securetoken  = null;
		try {
			MessageDigest Digest= MessageDigest.getInstance("md5");
			byte [] bs=Digest.digest(token.getBytes());
			securetoken = new  String(bs);
			securetoken = securetoken + "|" + (System.currentTimeMillis() + 1000*3600);
			securetoken = new BASE64Encoder().encode(securetoken.getBytes());
		}catch( Exception e) {
			logger.error("fail to get md5 algorithm");
		}
		return securetoken;
	}


}

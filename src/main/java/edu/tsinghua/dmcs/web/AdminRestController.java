package edu.tsinghua.dmcs.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.entity.*;
import edu.tsinghua.dmcs.interceptor.DmcsController;
import edu.tsinghua.dmcs.service.AdminGroupService;
import edu.tsinghua.dmcs.service.RoleService;
import edu.tsinghua.dmcs.service.TechDocuService;
import edu.tsinghua.dmcs.service.UserService;
import edu.tsinghua.dmcs.util.TockenCache;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
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
	@Autowired
	private TechDocuService techDocuService;

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

	@DmcsController(loginRequired = false)
	@ApiOperation(value="addocument",notes = "添加文件")
	@RequestMapping(value = "/addocument",method = RequestMethod.POST)
	public Response addocument(HttpServletRequest request) throws ParseException {
		MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);

		MultipartFile image = params.getFile("image");
		MultipartFile file = params.getFile("file");
		String title=params.getParameter("title");
		String description=params.getParameter("description");
		Cookie[] cookies = params.getCookies();
		//String identityNumber=params.getParameter("identityNumber");
		System.out.println("title:"+title);
		System.out.println("description:"+description);
		String identityNumber=params.getParameter("identityNumber");
		System.out.println("identityNumber:"+identityNumber);
		String admin_token=null;
		if(image!=null&&file!=null&&cookies!=null) {
			for ( Cookie cookie:cookies) {
				if(cookie.getName().equals("admin_token")){
					 admin_token = cookie.getValue();
					System.out.println(admin_token);
				}
			}
			admin_token= URLDecoder.decode(admin_token);
			String userid = tockenCache.getUserid(admin_token);
			if(userid==null){
				return Response.FAILWRONG().setMsg("身份验证失败");
			}
			if(userid!=null){
				AdminGroup adminGroup = adminGroupService.selectUser(userid);
				int tem = adminGroup.getAuthorityNumber();
				int identity_int = Integer.parseInt(identityNumber);
				if(tem == 0)
					return Response.FAILWRONG().setMsg("权限错误");
				int b = this.power(2, identity_int-1).intValue();
				if((tem&b)!=b)
					return Response.FAILWRONG().setMsg("权限错误");
			}
			try {
				String imageName = image.getOriginalFilename();
				String fileName = file.getOriginalFilename();
				logger.info("上传图片名为：" + imageName);
				logger.info("上传的文件名为：" + fileName);
				// 设置文件存储路径
				String IMAGEPATH = "http://39.104.208.4:80/home/dmcs/iamge/";
				String imagePath = "//home/dmcs/image/";
				String IMAGE_PATH = IMAGEPATH + imageName;
				String image_path = imagePath + imageName;

				String FILEPATH = "http://39.104.208.4:80/home/dmcs/file/";
				String filePath = "//home/dmcs/file/";
				String PATH = FILEPATH + fileName;
				String path = filePath + fileName;
				File dest_file = new File(path);
				if (!dest_file.getParentFile().exists()) {
					dest_file.getParentFile().mkdirs();// 新建文件夹
				}
				if (dest_file.createNewFile()) {
					System.out.println("File is created");
					dest_file.setExecutable(true);
					dest_file.setReadable(true);
					dest_file.setWritable(true);
				}
				// 检测是否存在目录
				file.transferTo(dest_file);// 文件写入
				/*******/
				File dest_image = new File(image_path);
				if (!dest_image.getParentFile().exists()) {
					dest_image.getParentFile().mkdirs();
				}
				if (dest_image.createNewFile()) {
					System.out.println("Image is created");
					dest_image.setExecutable(true);
					dest_image.setReadable(true);
					dest_image.setWritable(true);
				}
				image.transferTo(dest_image);//图片写入
				/****创建文件对象*/
				TechDocument techdocu = new TechDocument();
				techdocu.setDescription(description);
				techdocu.setTitle(title);
				techdocu.setDocument_addressd(PATH);
				techdocu.setImage_address(IMAGE_PATH);
				techdocu.setIdentityNumber(Integer.parseInt(identityNumber));
				int num = techDocuService.addTechDocument(techdocu);
				if (num != 0) {
					List <TechDocument> techDocuments = techDocuService.queryDocuByNumber(Integer.parseInt(identityNumber));
					return Response.SUCCESSOK().setData(techDocuments);
				} else
					return Response.FAILWRONG().setMsg("数据更新错误");

			} catch (IllegalStateException e) {
				e.printStackTrace();
				return Response.FAILWRONG();
			} catch (IOException e) {
				e.printStackTrace();
				return Response.FAILWRONG();
			}
		}
		return Response.SUCCESSOK();
	}


	@DmcsController(loginRequired = false)
	@ApiOperation(value = "getdocument",notes = "获取文件信息")
	@RequestMapping(value = "/getdocument",method = RequestMethod.POST)
	public Response getDocument(@RequestBody String body) throws ParseException{
		JSONObject o = JSONObject.parseObject(body);
		String authority = o.getString("authority");
		if(authority!=null){
			List<TechDocument> techDocuments = techDocuService.queryDocuByNumber(Integer.parseInt(authority));
			return Response.SUCCESSOK().setData(techDocuments);
		}
		return Response.FAILWRONG();
	}

	@DmcsController(loginRequired = false)
	@ApiOperation(value="deletedocument", notes = "删除文件")
	@RequestMapping(value = "deletedocument", method = RequestMethod.POST)
	public Response deleteDocument(@RequestBody String body) throws ParseException {
		JSONObject o = JSONObject.parseObject(body);
		String authority_string = o.getString("authority");
		String documentId = o.getString("documentId");
		String cookie_info = o.getString("cookie");
		JSONObject object = JSONObject.parseObject(cookie_info);
		String admin_token = object.getString("admin_token");
		if(authority_string==null||documentId==null||admin_token==null){
			return Response.FAILWRONG().setMsg("信息丢失");
		}
		admin_token = URLDecoder.decode(admin_token);
		String userid = tockenCache.getUserid(admin_token);
		if(userid==null)
			return Response.FAILWRONG().setMsg("身份验证失败");
		AdminGroup adminGroup = adminGroupService.selectUser(userid);
		if(adminGroup==null)
			return Response.FAILWRONG().setMsg("无相关用户");
		int tem = adminGroup.getAuthorityNumber();
		int authority = Integer.parseInt(authority_string);
		if(tem==0)
			return Response.FAILWRONG().setMsg("无相关权限");
		int b = this.power(2,authority-1).intValue();
		if((tem&b)!=b)
			return Response.FAILWRONG().setMsg("权限错误");
		int num = 0;
		Long ID = Long.parseLong(documentId);
		num = techDocuService.deleteDevice(ID);
		if(num==0){
			return Response.FAILWRONG().setMsg("删除操作失败");
		}
		List<TechDocument> techDocuments = techDocuService.queryDocuByNumber(authority);
		return Response.SUCCESSOK().setData(techDocuments);
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
	public static BigInteger power(int a, int b){
		BigInteger result = BigInteger.valueOf(1);
		int i=0;
		while(i<b){
			result=result.multiply(BigInteger.valueOf(a));
			i++;
		}
		return result;
	}


}

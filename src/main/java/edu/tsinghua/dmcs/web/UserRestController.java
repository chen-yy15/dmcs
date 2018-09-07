package edu.tsinghua.dmcs.web;

import com.alibaba.fastjson.JSONObject;
import edu.tsinghua.dmcs.Response;
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
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/////

/////
@RestController
@RequestMapping(value="/dmcs/api/v1/user")
public class UserRestController {

	private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private TockenCache tockenCache;
	@Autowired
	private AdminGroupService adminGroupService;


	@Value("${security.sault.login}")
	private String securitySault;
	
	@DmcsController(loginRequired=false)
    @ApiOperation(value="查询用户名是否存在", notes="data中true or false代表用户是否已存在")
	@RequestMapping(value = "checkUserExistence", method = RequestMethod.GET)
	public Response checkExistence(@RequestParam String username) {
    	logger.trace("checkExistence");
		User user = userService.checkExistence(username);
		return Response.SUCCESS().setData(user);
	}
	//这样定义的是一个共有类的方法，可以正常的使用的
	public int selectUser_num() {
		logger.trace("selectUser_num");
		int user_num=userService.selectUser_num();
		return user_num;
	}

	public static String getUserDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	@DmcsController(loginRequired=false)
    @ApiOperation(value="注册新用户", notes="")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Response register(@RequestBody String body) throws ParseException {
		System.out.println(body);
		JSONObject o = JSONObject.parseObject(body);
		String username = o.getString("username");
		User exist_u = userService.checkExistence(username);
			if(exist_u!=null){
			  return Response.FAILWRONG();
			}
		String sex = o.getString("sex");
		String mail = o.getString("mail");
		String password = o.getString("password");
		String mobile = o.getString("mobile");
		User u = new User();
		int user_num = selectUser_num();
		u.setUserid(getUserDate()+String.format("%04d",user_num)); // TODO
		u.setUsername(username);
        u.setUsersex(sex);
		String securedPasswd = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte [] bs = digest.digest((this.securitySault + password).getBytes());
			securedPasswd = new String(bs);

		} catch (Exception e) {
			logger.error("fail to get md5 algorithm");
		}
		// if securedPasswd is null throw exception
		u.setPassword(securedPasswd);
		u.setCurrentAuthority("user");
		u.setUserTelephone(mobile);
		u.setUserEmail(mail);
		u.setRealname(null);
		u.setAlias(null);
		u.setAvatar("http://47.92.126.195:80/image/ZiESqWwCXBRQoaPONSJe.png");
		u.setUserEmail_1(null);
		u.setUserTelephone_1(null);
		u.setUserworkPlace(null);
		u.setUserWeixin(null);
		u.setUserQq(null);
		u.setRegtime(new Date());
		int num = userService.addUser(u);
		u.setPassword(null);
		return Response.SUCCESSOK().setData(u);
		
	}
	
	@DmcsController(loginRequired=false)
    @ApiOperation(value="用户登陆", notes="true登陆成功")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Response login(@RequestBody String body,
			HttpServletResponse response) {
		JSONObject o = JSONObject.parseObject(body);
		String username_mobile_email = o.getString("username");
		String password = o.getString("password");
		User u = userService.checkExistence(username_mobile_email);
		if(u != null) {
			String securedPasswd = null;
			try {
				MessageDigest digest = MessageDigest.getInstance("md5");
				byte [] bs = digest.digest((this.securitySault + password).getBytes());
				securedPasswd = new String(bs);
				String token = this.getToken(u.getUsername()) ;//
				String temtoken = URLEncoder.encode(token);
				Cookie cookie = new Cookie("dmcstoken", temtoken);
				cookie.setMaxAge(3600);
				cookie.setPath("/");
				response.addCookie(cookie);
				tockenCache.setTokenForUser(token, u.getUsername());


			} catch (Exception e) {
				logger.error("fail to get md5 algorithm");
			}
			if(u.getPassword().equals(securedPasswd)) {
				u.setPassword(null);
				return Response.SUCCESSOK().setData(u);
			}
		}
		
		return Response.FAILWRONG();
		
	}

/********/
	@DmcsController(loginRequired=false)
	@ApiOperation(value="用户验证", notes="true验证成功")
	@RequestMapping(value = "/temcheck", method = RequestMethod.POST)
	public Response temcheck(@RequestBody String body) throws ParseException{
		System.out.println(body);
		JSONObject o = JSONObject.parseObject(body);
		String dmcstoken = o.getString("dmcstoken");
		if(dmcstoken==null)
			return Response.FAILWRONG();
		dmcstoken=URLDecoder.decode(dmcstoken);
		String username=tockenCache.getUserNameByToken(dmcstoken);
		System.out.println(username);
		if(username !=null){
			User u = userService.checkExistence(username);
			if( u!=null ) {
				return Response.SUCCESSOK().setData(u);
			}
			return Response.FAILWRONG();
		}
		return Response.FAILWRONG();
	}
	/*********/
	/*******/
	@DmcsController(loginRequired=false)
	@ApiOperation(value="插入图片", notes="插入成功")
	@RequestMapping(value = "/image", method = RequestMethod.POST)
	public Response image(@RequestParam(value="file",required  = false) MultipartFile file) {
		//这个图片更改与用户有关，所以还是放在这里
		//这里采用更换函数的方案，可以直接对用户的信息进行自动的更新
		if(file==null){
			return Response.FAILWRONG();
		}
		boolean isempty=file.isEmpty();
		if (isempty) {
			System.out.println("文件为空");
			return Response.FAILWRONG();
		}
		else
			try {
			// 获取文件名
			String fileName = file.getOriginalFilename();
			logger.info("上传的文件名为：" + fileName);
			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			//logger.info("文件的后缀名为：" + suffixName);

			// 设置文件存储路径
			String filePath = "//home/caizj/image/";
			String path = filePath +fileName;

			File dest = new File(path);
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();// 新建文件夹
			}
			if (dest.createNewFile()){
				System.out.println("File is created!");
				//Runtime.getRuntime().exec("chmod 777 /home/test3.txt");
				dest.setExecutable(true);//设置可执行权限
				dest.setReadable(true);//设置可读权限
				dest.setWritable(true);//设置可写权限
			}
			// 检测是否存在目录
			file.transferTo(dest);// 文件写入
			return Response.SUCCESSOK();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.FAILWRONG();
	}
	/*******/



	@DmcsController(loginRequired=true)
	@ApiOperation(value="用户登出", notes="true登出成功")
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public Response logout(@RequestParam String username, HttpServletRequest request,
						   HttpServletResponse response) {

		User u = userService.checkExistence(username);
		if(u != null) {
			String securedPasswd = null;
			try {
				Cookie [] cookies = request.getCookies();
				for(Cookie cookie : cookies) {
					String cookieName = cookie.getName();
					if("dmcstoken".equals(cookieName)) {
						String cookieValue = cookie.getValue();
						if(tockenCache.isTokenExist(cookieValue)) {
							tockenCache.removeToken(cookieValue);
							cookie.setValue(null);
							cookie.setMaxAge(0);
							cookie.setPath("/");
							response.addCookie(cookie);
							break;

						}
					}
				}

			} catch (Exception e) {
				logger.error("fail to get md5 algorithm");
			}
		}

		return Response.SUCCESS().setData(null);

	}
	
	@DmcsController(description="更新用户信息")
    @ApiOperation(value="更新用户信息", notes="返回更新成功个数")
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public Response update(
			@RequestParam String username,
			@RequestParam String currentAuthority,
			@RequestParam String password,
			@RequestParam String realname,
			@RequestParam String alias,
			@RequestParam String avatar,
			@RequestParam String userEmail,
			@RequestParam String userEmail_1,
			@RequestParam String userTelephone,
			@RequestParam String userTelephone_1,
			@RequestParam String userworkPlace,
			@RequestParam String userWeixin,
			@RequestParam String userQq) {
    	int num = 0;
		User u = userService.checkExistence(username);
		if(u != null) {
			u.setRealname(realname);
			String securedPasswd = null;
			try {
				MessageDigest digest = MessageDigest.getInstance("md5");
				byte [] bs = digest.digest((this.securitySault + password).getBytes());
				securedPasswd = new String(bs);

			} catch (Exception e) {
				logger.error("fail to get md5 algorithm");
			}
			// if securedPasswd is null throw exception
			u.setPassword(securedPasswd);
			u.setAlias(alias);
			u.setUsername(username);
			u.setCurrentAuthority(currentAuthority);
			u.setPassword(password);// TODO
			u.setAlias(alias);
			u.setAvatar(avatar);
			u.setUserEmail(userEmail);
			u.setUserEmail_1(userEmail_1);
			u.setUserTelephone(userTelephone);
			u.setUserTelephone_1(userTelephone_1);
			u.setUserworkPlace(userworkPlace);
			u.setUserWeixin(userWeixin);
			u.setUserQq(userQq);
		} else {
			 num = userService.update(u);
		}
		return Response.SUCCESS().returnData(num);
		
	}
	
	@DmcsController(loginRequired=true)
    @ApiOperation(value="获得指定用户角色", notes="获得当前用户角色")
	@RequestMapping(value = "/listRolesByUserId", method = RequestMethod.GET)
	public Response listRolesByUserId(String userName) {
		List<Role> rlist = roleService.getRoleListByUserName(userName);
		return Response.SUCCESS().setData(rlist);
	}
	@DmcsController(loginRequired=false)
	@ApiOperation(value="获得用户信息", notes="获得用户信息")
	@RequestMapping(value="/getuser",method = RequestMethod.POST)
	public Response getUser(@RequestBody String body) throws ParseException {
		JSONObject o = JSONObject.parseObject(body);
		String Userid = o.getString("Userid");
		if(Userid == null)
			return Response.FAILWRONG().setMsg("信息丢失");
		int checkifhost = adminGroupService.checkifhost(Userid);
		if(checkifhost == 0 ){//不是管理员
			return Response.FAILWRONG().setMsg("不是管理员");
		}
		else{//是管理员
			List<User> users = userService.nogetUserByuserid(Userid);
			return Response.SUCCESSOK().setData(users);
		}
		//return Response.FAILWRONG().setMsg("获取用户信息失败");
	}

	private String getToken(String userName) {
		String token = userName + this.securitySault + System.currentTimeMillis();
		String securedtoken = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte [] bs = digest.digest(token.getBytes());
			securedtoken = new String(bs);
			securedtoken = securedtoken + "|" + (System.currentTimeMillis() + 1000 * 3600);
			securedtoken = new BASE64Encoder().encode(securedtoken.getBytes());
		} catch (Exception e) {
			logger.error("fail to get md5 algorithm");
		}
		return securedtoken;
	}
	
}
//
//
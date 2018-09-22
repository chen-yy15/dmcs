package edu.tsinghua.dmcs.web;

import com.alibaba.fastjson.JSONObject;
import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.entity.Avatar;
import edu.tsinghua.dmcs.entity.LoginLog;
import edu.tsinghua.dmcs.entity.Role;
import edu.tsinghua.dmcs.entity.User;
import edu.tsinghua.dmcs.interceptor.DmcsController;
import edu.tsinghua.dmcs.service.*;
import edu.tsinghua.dmcs.util.SendEmail;
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
	private SendEmail emailService;

	@Autowired
	private AdminGroupService adminGroupService;

	@Autowired
	private AvatarService avatarService;

	@Autowired
	private LoginLogService loginLogService;


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
		String userName = o.getString("username");
		User exist_u = userService.checkExistence(userName);
			if(exist_u!=null){
			  return Response.FAILWRONG().setErrcode(1).setMsg("用户名已存在");
			}
		String mail = o.getString("mail");
		exist_u = userService.checkExistence(mail);
			if(exist_u!=null){
				return Response.FAILWRONG().setErrcode(2).setMsg("邮箱已存在");
			}
		String mobile = o.getString("mobile");
			exist_u = userService.checkExistence(mobile);
			if(exist_u!=null){
				return Response.FAILWRONG().setErrcode(3).setMsg("手机已存在");
			}
		String sex = o.getString("sex");
		String password = o.getString("password");
		String workPlace = o.getString("workPlace");
		User u = new User();
		int user_num = selectUser_num();
		String userId=getUserDate()+String.format("%04d",user_num);
		u.setUserid(userId); // TODO
		u.setUsername(userName);
        u.setUsersex(sex);
		String securedPasswd = null;

		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte [] bs = digest.digest((this.securitySault + password).getBytes());
			securedPasswd = new String(bs);

		} catch (Exception e) {
			logger.error("fail to get md5 algorithm");
			return Response.FAILWRONG().setMsg("编码错误");
		}
		u.setPassword(securedPasswd);
		u.setCurrentAuthority("guest");
		u.setUserTelephone(mobile);
		u.setUserEmail(mail);
		u.setEmailCheckedFlag("false");
		u.setRealname(null);
		u.setUserIdNumber(null);
		u.setAvatar("http://39.104.208.4:80/image/ZiESqWwCXBRQoaPONSJe.png");
		u.setUserTelephone_1(null);
		u.setUserworkPlace(workPlace);
		u.setUserWeixin(null);
		u.setUserQq(null);
		u.setRegtime(new Date());
		int num = userService.addUser(u);
		if(num==0)
			return Response.FAILWRONG().setErrcode(4).setMsg("注册失败");
		u.setPassword(null);
		/*****添加头像****/

		Avatar avatar = new Avatar();
		avatar.setUserId(userId);
		avatar.setAvatar("http://39.104.208.4:80/image/ZiESqWwCXBRQoaPONSJe.png");
		avatar.setSelectFlag("true");
		avatarService.AddAvatar(avatar);

		return Response.SUCCESSOK().setData(u);
		
	}
	//这里如何解决一台电脑上登录两个不同的账号的问题呢？
	//目前只能解决一台电脑上同一时期只能登录单个账号
	//如何避免一个账号在异地登录呢？
	@DmcsController(loginRequired=false)
    @ApiOperation(value="用户登陆", notes="true登陆成功")
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Response login(@RequestBody String body,
						  HttpServletResponse response, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String dmcs_token=null;
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if(cookie.getName().equals("dmcstoken")){
					dmcs_token = cookie.getValue();
				}
			}
			dmcs_token = URLDecoder.decode(dmcs_token);
			String username = tockenCache.getUserNameByToken(dmcs_token);
			if(username!=null)
				return Response.FAILWRONG().setErrcode(1).setMsg("用户已登录");
		}
		JSONObject o = JSONObject.parseObject(body);
		String username_mobile_email = o.getString("username");
		String password = o.getString("password");
		User u = userService.checkExistence(username_mobile_email);//别人的用户名与自己的邮箱相同如何解决这个问题
		if(u != null) {
			String securedPasswd = null;
			try {
				MessageDigest digest = MessageDigest.getInstance("md5");
				byte [] bs = digest.digest((this.securitySault + password).getBytes());
				securedPasswd = new String(bs);
				String token = this.getToken(u.getUsername()) ;
				String temtoken = URLEncoder.encode(token);
				Cookie cookie = new Cookie("dmcstoken", temtoken);
				cookie.setMaxAge(120);//这是cookie的寿命时间，没有问题;
				cookie.setPath("/");
				response.addCookie(cookie);
				tockenCache.setTokenForUser(token, u.getUsername());

			} catch (Exception e) {
				logger.error("fail to get md5 algorithm");
				return Response.FAILWRONG().setErrcode(1).setMsg("解密失败");
			}
			if(u.getPassword().equals(securedPasswd)) {
				String ip = this.getIpAddress(request);
				LoginLog loginLog = new LoginLog();
				loginLog.setLoginip(ip);

				Date login = new Date();
				Date logout = new Date(login.getTime()+60*1000);
				/*  直接计算消失时间，加入库中*/
				loginLog.setUserid(u.getUserid());
				loginLog.setLogintime(login);
				loginLog.setLoginoutway("false");
				loginLog.setLoginouttime(logout);
				int num = loginLogService.AddLog(loginLog);
				System.out.println(num);
				u.setPassword(null);
				return Response.SUCCESSOK().setData(u);
			}
		}
		
		return Response.FAILWRONG().setErrcode(0).setMsg("用户不存在");
		
	}

/********/

/*******/
	@DmcsController(loginRequired=false)
	@ApiOperation(value="用户验证", notes="true验证成功")
	@RequestMapping(value = "/temcheck", method = RequestMethod.POST)
	public Response temcheck(HttpServletRequest request) throws ParseException{
		Cookie[] cookies = request.getCookies();
		if(cookies.length!=0){
			System.out.println(cookies.length);
		}
		String dmcstoken=null;
		for(Cookie cookie:cookies){
			if(cookie.getName().equals("dmcstoken")){
				dmcstoken=cookie.getValue();
			}
		}
		if(dmcstoken==null)
			return Response.FAILWRONG().setErrcode(1).setMsg("信息验证失败");
		dmcstoken=URLDecoder.decode(dmcstoken);
		String userName=tockenCache.getUserNameByToken(dmcstoken);
		System.out.println(userName);
		if(userName !=null){
			User u = userService.checkExistence(userName);
			if( u!=null ) {
				u.setPassword(null);
				return Response.SUCCESSOK().setData(u);
			}
			return Response.FAILWRONG();
		}
		return Response.FAILWRONG();
	}
	/*********/
	@DmcsController(loginRequired = false)
	@ApiOperation(value = "邮件激活",notes = "邮件激活成功")
	@RequestMapping(value = "/verifyaccount",method = RequestMethod.POST)
	public Response verifyAccount(@RequestBody String body) throws ParseException{
		JSONObject o = JSONObject.parseObject(body);
		String verify = o.getString("verify");
		if(verify==null){
			return Response.FAILWRONG();
		}
		System.out.println(verify);
		emailService.sendSimEmail("caizj15@qq.com","注册邮件","注册信息");
		return Response.SUCCESSOK();
	}
	/*******/
	@DmcsController(loginRequired=false)
	@ApiOperation(value="插入图片", notes="插入成功")
	@RequestMapping(value = "/image", method = RequestMethod.POST)
	public Response image(HttpServletRequest request) {
		//这个图片更改与用户有关，所以还是放在这里
		//这里采用更换函数的方案，可以直接对用户的信息进行自动的更新
		MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
		MultipartFile file = params.getFile("file");
		Cookie[] cookies = params.getCookies();
		String dmcs_token = null;
		if(file==null||cookies==null){
			return Response.FAILWRONG().setMsg("信息丢失");
		}
		boolean isempty=file.isEmpty();
		if (isempty) {
			System.out.println("文件为空");
			return Response.FAILWRONG();
		}
		else
			try {
			for( Cookie cookie:cookies){
				if(cookie.getName().equals("dmcstoken")){
					dmcs_token = cookie.getValue();
				}
			}
			dmcs_token = URLDecoder.decode(dmcs_token);
			String username = tockenCache.getUserNameByToken(dmcs_token);
			if(username == null)
				return Response.FAILWRONG();
			System.out.println(username);
			User u = userService.checkExistence(username);
			if( u == null)
				return Response.FAILWRONG().setMsg("身份验证错误");
			// 获取文件名
			String fileName = file.getOriginalFilename();
			logger.info("上传的文件名为：" + fileName);
			// 获取文件的后缀名
			//String suffixName = fileName.substring(fileName.lastIndexOf("."));
			//logger.info("文件的后缀名为：" + suffixName);

			// 设置文件存储路径
				String FILEPATH = "http://39.104.208.4:80/home/dmcs/image/usr";//实际存储
			String filePath = "//home/dmcs/image/usr";//存储到硬盘上
				String PATH = FILEPATH + fileName;
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
			u.setAvatar(PATH);
			u.setPassword(null);
			int num  = userService.update(u);
			if(num==0)
				return Response.FAILWRONG().setMsg("信息更新失败");
			return Response.SUCCESSOK().setData(u);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return Response.FAILWRONG().setMsg("文件夹创建出问题了!!!");
		} catch (IOException e) {
			e.printStackTrace();
			return Response.FAILWRONG().setMsg("文件夹创建出问题了!!!");
		}
	}
	/*******/



	@DmcsController(loginRequired=false)
	@ApiOperation(value="用户登出", notes="true登出成功")
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public Response logout(HttpServletRequest request, HttpServletResponse response) {
			 {
				Cookie [] cookies = request.getCookies();
				for(Cookie cookie : cookies) {
					String cookieName = cookie.getName();
					if("dmcstoken".equals(cookieName)) {
						String cookieValue = cookie.getValue();
						cookieValue = URLDecoder.decode(cookieValue);
						String username = tockenCache.getUserNameByToken(cookieValue);
						User u = userService.checkExistence(username);
						if(u!=null){
							tockenCache.removeToken(cookieValue);
							cookie.setValue(null);
							cookie.setMaxAge(0);
							cookie.setPath("/");
							response.addCookie(cookie);

							LoginLog a = new LoginLog();
							String ip = this.getIpAddress(request);
							a.setLoginip(ip);
							a.setUserid(u.getUserid());
							LoginLog b = loginLogService.GetLogMax(a);
							if(b==null)
								break;
							b.setLoginouttime(new Date());
							b.setLoginoutway("true");
							loginLogService.UpdateLog(b);
						}//cookie已经清零了，需要进行log设置

					}
				}
			}
		    return Response.SUCCESSOK();

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
			u.setUsername(username);
			u.setCurrentAuthority(currentAuthority);
			u.setPassword(password);// TODO
			u.setAvatar(avatar);
			u.setUserEmail(userEmail);
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

	public final static String getIpAddress(HttpServletRequest request) {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址

		String ip = request.getHeader("X-Forwarded-For");
		if (logger.isInfoEnabled()) {
			logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);
				}
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
				if (logger.isInfoEnabled()) {
					logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);
				}
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}
//
}
//
//
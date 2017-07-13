package edu.tsinghua.dmcs.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.entity.User;
import edu.tsinghua.dmcs.mapper.UserMapper;
import io.swagger.annotations.ApiOperation;



@RestController
@RequestMapping(value="/user")
public class UserRestController {
	
	@Autowired
	private UserMapper userMapper;
	
    @ApiOperation(value="查询用户名是否存在", notes="")
	@RequestMapping("checkUserExistence")
	public Response checkExistence(@RequestParam String username) {
		User user = userMapper.selectByUserName(username);
		return Response.returnData(user != null);
	}
	
    @ApiOperation(value="注册新用户", notes="")
	@RequestMapping("/register")
	public Response register(@RequestParam String username,
			@RequestParam String realname,
			@RequestParam String title,
			@RequestParam String idcard,
			@RequestParam String password,
			@RequestParam String alias,
			@RequestParam String birthday,
			@RequestParam String image,
			@RequestParam String icon,
			@RequestParam String email,
			@RequestParam String mobile) {
		
		User u = new User();
		u.setUid("1"); // TODO
		u.setUsername(username);
		u.setRealname(realname);
		u.setTitle(title);
		u.setIdcard(idcard);
		u.setPassword(password);// TODO
		u.setAlias(alias);
		Date birth = new Date(birthday);
		u.setBirthday(birth); // TODO
		u.setImage(image);
		u.setIcon(icon);
		u.setEmail(email);
		u.setMobile(mobile);
		u.setRegtime(new Date());
		userMapper.insert(u);
		
		return Response.returnData(u);
		
	}
	
    @ApiOperation(value="用户登陆", notes="")
	@RequestMapping("/login")
	public Response login(@RequestParam String username,
			@RequestParam String password) {
		
		User u = userMapper.selectByUserName(username);
		if(u != null) {
			if(u.getPassword().equals(password))
				return Response.returnData(true);
		}
		
		return Response.returnData(false);
		
	}
	
    @ApiOperation(value="更新用户信息", notes="")
	@RequestMapping("/update")
	public Response update(@RequestParam String username,
			@RequestParam String realname,
			@RequestParam String title,
			@RequestParam String idcard,
			@RequestParam String password,
			@RequestParam String alias,
			@RequestParam String birthday,
			@RequestParam String image,
			@RequestParam String icon,
			@RequestParam String email,
			@RequestParam String mobile) {
		
		User u = new User();
		
		u = userMapper.selectByUserName(username);
		if(u != null) {
			u.setRealname(realname);
			u.setTitle(title);
			u.setIdcard(idcard);
			u.setPassword(password);// TODO
			u.setAlias(alias);
			Date birth = new Date(birthday);
			u.setBirthday(birth); // TODO
			u.setImage(image);
			u.setIcon(icon);
			u.setEmail(email);
			u.setMobile(mobile);
		}
		int num = userMapper.updateByPrimaryKey(u);
		return Response.returnData(num);
		
	}
}

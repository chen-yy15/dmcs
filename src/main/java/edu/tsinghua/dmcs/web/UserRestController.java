package edu.tsinghua.dmcs.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.tsinghua.dmcs.entity.User;
import edu.tsinghua.dmcs.mapper.UserMapper;



@RestController
public class UserRestController {
	
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping("checkExistence")
	public boolean checkExistence(@RequestParam String username) {
		User user = userMapper.selectByUserName(username);
		return user != null;
	}
	
	@RequestMapping("/register")
	public User register(@RequestParam String username,
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
		
		return u;
		
	}
	
	@RequestMapping("/login")
	public boolean login(@RequestParam String username,
			@RequestParam String password) {
		
		User u = userMapper.selectByUserName(username);
		if(u != null) {
			if(u.getPassword().equals(password))
				return true;
		}
		
		return false;
		
	}
	
	@RequestMapping("/update")
	public User update(@RequestParam String username,
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
		userMapper.updateByPrimaryKey(u);
		return u;
		
	}
}

package edu.tsinghua.dmcs.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import edu.tsinghua.dmcs.entity.User;
import edu.tsinghua.dmcs.mapper.UserMapper;
import edu.tsinghua.dmcs.service.UserService;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public User checkExistence(@RequestParam String username) {
		User user = userMapper.selectByUserName(username);
		return user;
	}
	
	public Integer addUser(User user) {
		int num = userMapper.insert(user);
		return num;
	}
	
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
	
	public User login(@RequestParam String username,
			@RequestParam String password) {
		
		User u = userMapper.selectByUserName(username);
		if(u != null) {
			if(u.getPassword().equals(password))
				return null;
		}
		
		return u;
		
	}
	
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
		int num = userMapper.updateByPrimaryKey(u);
		return u;
		
	}

	public User getUserById(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public int update(User user) {
		return userMapper.updateByPrimaryKey(user);
	}


}

package edu.tsinghua.dmcs.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tsinghua.dmcs.entity.User;
import edu.tsinghua.dmcs.mapper.UserMapper;



@RestController
public class UserRestController {
	
	@Autowired
	private UserMapper userMapper;
	
	@RequestMapping("/register")
	public User register() {
		
		User u = new User();
		u.setUid("cje");
		userMapper.insert(u);
		// user exists?
		// ingest user
		
		return u;
		
	}
	
	@RequestMapping("/login")
	public User login() {
		
		User u = new User();
		u.setUid("cje");
		userMapper.insert(u);
		// user exists?
		// ingest user
		
		return u;
		
	}
	
	@RequestMapping("/update")
	public User update() {
		
		User u = new User();
		u.setUid("cje");
		userMapper.insert(u);
		// user exists?
		// ingest user
		
		return u;
		
	}
}

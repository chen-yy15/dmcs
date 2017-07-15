package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.User;

public interface UserService {
	
	public User checkExistence(String username);
	
	public Integer addUser(User user);
	
	public User register( String username,
			 String realname,
			 String title,
			 String idcard,
			 String password,
			 String alias,
			 String birthday,
			 String image,
			 String icon,
			 String email,
			 String mobile);
	
	public User login(String username,
			 String password);
	
	public User update(String username,
			 String realname,
			 String title,
			 String idcard,
			 String password,
			 String alias,
			 String birthday,
			 String image,
			 String icon,
			 String email,
			 String mobile);
	
	public int update(User user);
	
	public User getUserById(Long id);

}

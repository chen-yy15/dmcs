package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.User;

public interface UserService {
	
	public User checkExistence(String username_mobile_email);
	public int selectUser_num();
	public Integer addUser(User user);
	
	public User register(  String username,
						   String currentAuthority,
						   String password,
						   String usersex,
						   String realname,
						   String alias,
						   String avatar,
						   String userEmail,
						   String userEmail_1,
						   String userTelephone,
						   String userTelephone_1,
						   String userworkPlace,
						   String userWeixin,
						   String userQq);
	
	public User login(String username,
			 String password);
	
	public User update(String username,
					   String currentAuthority,
					   String password,
					   String usersex,
					   String realname,
					   String alias,
					   String avatar,
					   String userEmail,
					   String userEmail_1,
					   String userTelephone,
					   String userTelephone_1,
					   String userworkPlace,
					   String userWeixin,
					   String userQq);
	
	public int update(User user);
	
	public User getUserById(Long id);

}

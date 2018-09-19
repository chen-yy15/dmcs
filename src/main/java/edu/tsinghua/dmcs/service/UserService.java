package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.User;

import java.util.List;

public interface UserService {
	
	public User checkExistence(String username_mobile_email);

	public int selectUser_num();

	public Integer addUser(User user);
	
	public int update(User user);

	public User getUserByuserid(String userId);

	public List<User> nogetUserByuserid(String userId);

}

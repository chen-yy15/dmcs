package edu.tsinghua.dmcs.service.impl;

import edu.tsinghua.dmcs.entity.User;
import edu.tsinghua.dmcs.mapper.UserMapper;
import edu.tsinghua.dmcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public User checkExistence(@RequestParam String username_mobile_email) {
		User user = userMapper.selectByUserName(username_mobile_email);
		return user;
	}
	//这里需要考虑到返回数组的情况
	public int selectUser_num() {
		Integer number = userMapper.selectUser_num();
		if(number == null)
			return 0;
		return number.intValue();
	}
	public Integer addUser(User user) {
		int num = userMapper.insert(user);
		return num;
	}

	public int update(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}

    public User getUserByuserid (String userid) { return userMapper.selectByuserid(userid); }

	public List<User> nogetUserByuserid(String userid){
		return userMapper.nogetuser(userid);
	}
}

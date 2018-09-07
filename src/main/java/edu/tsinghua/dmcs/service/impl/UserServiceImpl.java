package edu.tsinghua.dmcs.service.impl;

import edu.tsinghua.dmcs.entity.User;
import edu.tsinghua.dmcs.mapper.UserMapper;
import edu.tsinghua.dmcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

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
	
	public User register(@RequestParam String username,
						 @RequestParam String currentAuthority,
						 @RequestParam String password,
						 @RequestParam String usersex,
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
		
		User u = new User();
		u.setUserid("1"); // TODO
		u.setUsername(username);
		u.setCurrentAuthority(currentAuthority);
		u.setPassword(password);// TODO
		u.setUsersex(usersex);
		u.setRealname(realname);
		u.setAlias(alias);
		u.setAvatar(avatar);
        u.setUserEmail(userEmail);
        u.setUserEmail_1(userEmail_1);
        u.setUserTelephone(userTelephone);
        u.setUserTelephone_1(userTelephone_1);
        u.setUserworkPlace(userworkPlace);
        u.setUserWeixin(userWeixin);
        u.setUserQq(userQq);
		u.setRegtime(new Date());
		userMapper.insert(u);
		return u;
		
	}
	
	public User login(
			@RequestParam String username,
			@RequestParam String password) {
		
		User u = userMapper.selectByUserName(username);
		if(u != null) {
			if(u.getPassword().equals(password))
				return null;
		}
		return u;
	}
	
	public User update(@RequestParam String username,
					   @RequestParam String currentAuthority,
					   @RequestParam String password,
					   @RequestParam String usersex,
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
		
		User u = new User();
		
		u = userMapper.selectByUserName(username);
		if(u != null) {
			u.setCurrentAuthority(currentAuthority);
			u.setPassword(password);// TODO
			u.setUsersex(usersex);
			u.setRealname(realname);
			u.setAlias(alias);
			u.setAvatar(avatar);
			u.setUserEmail(userEmail);
			u.setUserEmail_1(userEmail_1);
			u.setUserTelephone(userTelephone);
			u.setUserTelephone_1(userTelephone_1);
			u.setUserworkPlace(userworkPlace);
			u.setUserWeixin(userWeixin);
			u.setUserQq(userQq);
		}
		int num = userMapper.updateByPrimaryKey(u);
		return u;
		
	}

	public User getUserById(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public int update(User user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}

    public User getUserByuserid (String userid) { return userMapper.selectByuserid(userid); }
}

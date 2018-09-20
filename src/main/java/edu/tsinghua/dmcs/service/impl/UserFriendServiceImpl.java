package edu.tsinghua.dmcs.service.impl;

import edu.tsinghua.dmcs.entity.UserFriend;
import edu.tsinghua.dmcs.mapper.UserFriendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by caizj on 18-9-20.
 */
@Component
public class UserFriendServiceImpl {

    @Autowired
    UserFriendMapper userFriendMapper;

    public int AddFriend(UserFriend userFriend){
        int num = 0;
        if(userFriend != null){
           num=userFriendMapper.addFriend(userFriend);
        }
        return num;
    }

    public int DeleteById(Long id){
        return userFriendMapper.deleteById(id);
    }

    public int DeleteByUserId(String userId){
        return userFriendMapper.deleteByUser(userId);
    }

    public int deleteBySelfUser(String userSelf, String userFriend){
        int num = 0;
        UserFriend u = new UserFriend();
        u.setUserSelf(userSelf);
        u.setUserFriend(userFriend);
        if(u!=null){
            num = userFriendMapper.deleteBySelfUser(u);
        }
        return num;
    }

    public UserFriend GetFriend(Long id){
        return userFriendMapper.getFriend(id);
    }
}

package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.UserFriend;

/**
 * Created by caizj on 18-9-20.
 */

public interface UserFriendService {

    public int AddFriend(UserFriend userFriend);

    public int DeleteById(Long id);

    public int DeleteByUserId(String userId);

    public int deleteBySelfUser(String userSelf, String userFriend);

    public UserFriend GetFriend(Long id);
}

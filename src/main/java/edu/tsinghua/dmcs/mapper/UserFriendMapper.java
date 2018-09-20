package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.UserFriend;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by caizj on 18-9-20.
 */
@Mapper
public interface UserFriendMapper {
    int addFriend(UserFriend userFriend);

    int deleteByUser(String userself);

    int deleteById(Long id);

    int deleteBySelfUser(UserFriend userfriend);

    UserFriend getFriend(Long id);
}

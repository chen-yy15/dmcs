package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    int insert(User record);

    int insertSelective(User record);

    int selectUser_num();

    int deleteByUserId(String userid);
    
    User selectByUserName(String username_mobile_email);

    User selectByuserid(String userid);

    User selectByUserEmail(String userEmail);

    User selectByUserTele(String userTele);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> nogetuser(String userid);
}
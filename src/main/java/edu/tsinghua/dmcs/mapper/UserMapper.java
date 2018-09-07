package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    int selectUser_num();

    User selectByPrimaryKey(Long id);
    
    User selectByUserName(String userName);

    User selectByuserid(String userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> nogetuser(String userid);
}
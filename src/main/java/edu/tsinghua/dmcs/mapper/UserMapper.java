package edu.tsinghua.dmcs.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.tsinghua.dmcs.entity.User;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
package edu.tsinghua.dmcs.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.tsinghua.dmcs.entity.UserRoleMapping;

@Mapper
public interface UserRoleMappingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRoleMapping record);

    int insertSelective(UserRoleMapping record);

    UserRoleMapping selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRoleMapping record);

    int updateByPrimaryKey(UserRoleMapping record);
}
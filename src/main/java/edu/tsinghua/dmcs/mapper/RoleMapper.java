package edu.tsinghua.dmcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.tsinghua.dmcs.entity.Role;

@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> listAllRoles();

    List<Role> getRoleListByUserName(String userName);
}
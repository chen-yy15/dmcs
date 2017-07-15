package edu.tsinghua.dmcs.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.tsinghua.dmcs.entity.GroupUserMapping;

@Mapper
public interface GroupUserMappingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupUserMapping record);

    int insertSelective(GroupUserMapping record);

    GroupUserMapping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupUserMapping record);

    int updateByPrimaryKey(GroupUserMapping record);
}
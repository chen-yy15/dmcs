package edu.tsinghua.dmcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.tsinghua.dmcs.entity.Group;
import edu.tsinghua.dmcs.entity.GroupUserMapping;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GroupUserMappingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupUserMapping record);

    int insertSelective(GroupUserMapping record);

    GroupUserMapping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupUserMapping record);

    int updateByPrimaryKey(GroupUserMapping record);
    
    List<Group> listGroupByUserId(Long userId);
    
	public Integer removeMemberForGroup(@Param("groupId") Long groupId, @Param("userId") Long userId);
	
	public GroupUserMapping getGroupUserMapping(@Param("groupId") Long groupId, @Param("userId") Long userId);

}
package edu.tsinghua.dmcs.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.tsinghua.dmcs.entity.GroupDeviceMapping;

@Mapper
public interface GroupDeviceMappingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroupDeviceMapping record);

    int insertSelective(GroupDeviceMapping record);

    GroupDeviceMapping selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroupDeviceMapping record);

    int updateByPrimaryKey(GroupDeviceMapping record);
}
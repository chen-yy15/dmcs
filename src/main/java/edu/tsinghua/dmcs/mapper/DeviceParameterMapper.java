package edu.tsinghua.dmcs.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.tsinghua.dmcs.entity.DeviceParameter;

@Mapper
public interface DeviceParameterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeviceParameter record);

    int insertSelective(DeviceParameter record);

    DeviceParameter selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeviceParameter record);

    int updateByPrimaryKey(DeviceParameter record);
}
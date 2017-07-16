package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.DeviceParameter;

public interface DeviceParameterMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DeviceParameter record);

    int insertSelective(DeviceParameter record);

    DeviceParameter selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DeviceParameter record);

    int updateByPrimaryKey(DeviceParameter record);
}
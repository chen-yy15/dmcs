package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.UserDeviceParameterPosition;

public interface UserDeviceParameterPositionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDeviceParameterPosition record);

    int insertSelective(UserDeviceParameterPosition record);

    UserDeviceParameterPosition selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDeviceParameterPosition record);

    int updateByPrimaryKey(UserDeviceParameterPosition record);
}
package edu.tsinghua.dmcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.tsinghua.dmcs.entity.Device;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DeviceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

    public List<Device> queryDeviceInfo();
    
    List<Device> queryDeviceByGroupId(Long groupId);
    
    List<Device> queryUnbindDevices(@Param("page") Integer page, @Param("size") Integer size);
}
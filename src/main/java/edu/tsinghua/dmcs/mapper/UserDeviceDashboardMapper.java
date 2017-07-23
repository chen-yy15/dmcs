package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.UserDeviceDashboard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDeviceDashboardMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDeviceDashboard record);

    int insertSelective(UserDeviceDashboard record);

    UserDeviceDashboard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDeviceDashboard record);

    int updateByPrimaryKey(UserDeviceDashboard record);

    public List<UserDeviceDashboard> listDeviceLayoutByUser(@Param("userId") Long userId, @Param("deviceId") Long deviceId);
}
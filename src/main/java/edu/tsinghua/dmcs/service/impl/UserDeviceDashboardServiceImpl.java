package edu.tsinghua.dmcs.service.impl;

import edu.tsinghua.dmcs.entity.UserDeviceDashboard;
import edu.tsinghua.dmcs.mapper.UserDeviceDashboardMapper;
import edu.tsinghua.dmcs.service.UserDeviceDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDeviceDashboardServiceImpl implements UserDeviceDashboardService {

    @Autowired
    UserDeviceDashboardMapper userDeviceDashboardMapper;

    public List<UserDeviceDashboard> listDeviceLayoutByUser(Long userId, Long deviceId) {

        return userDeviceDashboardMapper.listDeviceLayoutByUser(userId, deviceId);
    }
}

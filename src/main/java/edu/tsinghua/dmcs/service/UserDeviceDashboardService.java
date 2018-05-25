package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.UserDeviceDashboard;

import java.util.List;

public interface UserDeviceDashboardService {

    public List<UserDeviceDashboard> listDeviceLayoutByUser(Long userId, Long deviceId);

}

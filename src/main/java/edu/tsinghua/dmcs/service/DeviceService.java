package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.Device;

import java.util.List;

public interface DeviceService {
	
	
	public Integer addDevice(Device device);
	
	public Integer updateDevice(Device device);
	
	public int deleteDevice(Long id);
    
	public List<Device> queryDeviceByGroupId(Long groupId);

	public List<Device> queryDeviceInfo();
	
	//public Device assignOwnerForDevice(Long userId, Long deviceId);
	
	public Device getDeviceById(Long id);

	public List<Device> queryUnbindDevices(Integer page, Integer size);

}

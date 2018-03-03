package edu.tsinghua.dmcs.service;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import edu.tsinghua.dmcs.entity.Device;

public interface DeviceService {
	
	
	public Integer addDevice(Device device);
	
	public Integer updateDevice(Device device);
	
	public int deleteDevice(Long id);
    
	public List<Device> queryDeviceByGroupId(Long groupId);

	public List<Device> queryDeviceInfo();
	
	public Device assignOwnerForDevice(Long userId, Long deviceId);
	
	public Device getDeviceById(Long id);

	public List<Device> queryUnbindDevices(Integer page, Integer size);

}

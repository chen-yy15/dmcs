package edu.tsinghua.dmcs.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.tsinghua.dmcs.entity.Device;
import edu.tsinghua.dmcs.entity.User;
import edu.tsinghua.dmcs.mapper.DeviceMapper;
import edu.tsinghua.dmcs.mapper.UserMapper;
import edu.tsinghua.dmcs.service.DeviceService;
import io.swagger.annotations.ApiOperation;

@Component
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	DeviceMapper deviceMapper;
	
	@Autowired
	UserMapper userMapper;
	
	public Integer addDevice(Device device) {
		int num = 0;
		if(device != null) {
			 num = deviceMapper.insert(device);
		}
		return num;
	}
	
	public Integer updateDevice(Device device) {
		int num = 0;
		
		if(device != null) {
			num = deviceMapper.updateByPrimaryKey(device);
		}
		
		return num;
	}
	
	public int deleteDevice(Long id) {
		
		int num = deviceMapper.deleteByPrimaryKey(id);
		
		return num;
	}
    
    @ApiOperation(value="通过群组Id查询设备", notes="")
	@RequestMapping("/queryDeviceByGroupId")
	public List<Device> queryDeviceByGroupId(Long groupId) {
		List<Device> devices = deviceMapper.queryDeviceByGroupId(groupId);
		return devices;
	}
	
    @ApiOperation(value="绑定设备到个人", notes="")
	@RequestMapping("/assignOwnerForDevice")
	public Device assignOwnerForDevice(@RequestParam Long userId, @RequestParam Long deviceId) {
		
		User user = userMapper.selectByPrimaryKey(userId);
		Device d = deviceMapper.selectByPrimaryKey(deviceId);
		if(user == null) {
			return null;
		}
		if(d == null) {
			return null;
		}
		
		// 是否已绑定用户，已绑定抛错
		d.setOwner(userId);
		int count = deviceMapper.updateByPrimaryKey(d);
		if(count == 0) {
			return null;
		}
		return d;
	}

	public Device getDeviceById(Long id) {
		return deviceMapper.selectByPrimaryKey(id);
	}
	


}

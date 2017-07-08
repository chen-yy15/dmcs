package edu.tsinghua.dmcs.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.tsinghua.dmcs.Constants;
import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.entity.Device;
import edu.tsinghua.dmcs.entity.User;
import edu.tsinghua.dmcs.mapper.DeviceMapper;
import edu.tsinghua.dmcs.mapper.UserMapper;

@RestController
public class DeviceRestController {

	@Autowired
	DeviceMapper deviceMapper;
	
	@Autowired
	UserMapper userMapper;
	
	@RequestMapping("/addDevice")
	public Response addDevice(@RequestParam Long id,
			@RequestParam String devimage,
			@RequestParam String devid,
			@RequestParam String name,
			@RequestParam String type,
			@RequestParam String parameters,
			@RequestParam String vendor,
			@RequestParam Date guranteeFrom,
			@RequestParam Long owner) {
		
		Device d = new Device();
		d.setDevimage(devimage);
		d.setDevid(devid);
		d.setName(name);
		d.setType(type);
		d.setParameters(parameters);
		d.setVendor(vendor);
		d.setGuranteeFrom(guranteeFrom);
		d.setOwner(owner);
		int num = deviceMapper.insert(d);
		
		return Response.returnData(num);
	}
	
	@RequestMapping("/updateDevice")
	public Response updateDevice(@RequestParam Long id,
			@RequestParam String devimage,
			@RequestParam String devid,
			@RequestParam String name,
			@RequestParam String type,
			@RequestParam String parameters,
			@RequestParam String vendor,
			@RequestParam Date guranteeFrom,
			@RequestParam Long owner) {
		
		Device d = new Device();
		d.setId(id);
		d.setDevimage(devimage);
		d.setDevid(devid);
		d.setName(name);
		d.setType(type);
		d.setParameters(parameters);
		d.setVendor(vendor);
		d.setGuranteeFrom(guranteeFrom);
		d.setOwner(owner);
		int num = deviceMapper.updateByPrimaryKey(d);
		
		return Response.returnData(num);
	}
	
	@RequestMapping("/deleteDevice")
	public Response deleteDevice(@RequestParam Long id) {
		
		int num = deviceMapper.deleteByPrimaryKey(id);
		
		return Response.returnData(num);
	}
	
	@RequestMapping("/queryDeviceByGroupId")
	public Response queryDeviceByGroupId(@RequestParam Long groupId) {
		List<Device> devices = deviceMapper.queryDeviceByGroupId(groupId);
		return Response.returnData(devices);
	}
	
	@RequestMapping("/assignOwnerForDevice")
	public Response assignOwnerForDevice(@RequestParam Long userId, @RequestParam Long deviceId) {
		
		User user = userMapper.selectByPrimaryKey(userId);
		Device d = deviceMapper.selectByPrimaryKey(deviceId);
		if(user == null) {
			return Response.NEW().setErrcode(Constants.RC_FAIL_USER_NO_EXIST_CODE).setMsg(Constants.RC_FAIL_USER_NO_EXIST_MSG);
		}
		if(d == null) {
			return Response.NEW().setErrcode(Constants.RC_FAIL_DEVICE_NO_EXIST_CODE).setMsg(Constants.RC_FAIL_DEVICE_NO_EXIST_MSG);
		}
		
		// 是否已绑定用户，已绑定抛错
		d.setOwner(userId);
		int count = deviceMapper.updateByPrimaryKey(d);
		if(count == 0) {
			return Response.NEW().setErrcode(Constants.RC_FAIL_DEVICE_UPDATE_CODE).setMsg(Constants.RC_FAIL_DEVICE_UPDATE_MSG);
		}
		return Response.SUCCESS();
	}
	
}

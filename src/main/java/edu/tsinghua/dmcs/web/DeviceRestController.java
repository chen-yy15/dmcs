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
import edu.tsinghua.dmcs.interceptor.DmcsController;
import edu.tsinghua.dmcs.service.DeviceService;
import edu.tsinghua.dmcs.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/device")
public class DeviceRestController {

	@Autowired
	DeviceService deviceService;
	
	@Autowired
	UserService userService;
	
    @ApiOperation(value="添加新设备", notes="")
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
		int num = deviceService.addDevice(d);
		
		return Response.SUCCESS().setData(d);
	}
	
    @ApiOperation(value="更新设备信息", notes="")
	@RequestMapping("/updateDevice")
    @DmcsController(description="更新设备信息")
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
		int num = deviceService.updateDevice(d);
		return Response.SUCCESS().setData(num);
	}
	
    @ApiOperation(value="删除设备", notes="data中删除成功个数")
	@RequestMapping("/deleteDevice")
    @DmcsController(roleAllowed="administrator", description="删除设备")
	public Response deleteDevice(@RequestParam Long id) {
		
		int num = deviceService.deleteDevice(id);
		
		return Response.SUCCESS().setData(num);
	}
    
    @ApiOperation(value="通过群组Id查询设备", notes="")
	@RequestMapping("/queryDeviceByGroupId")
    @DmcsController(loginRequired=true)
	public Response queryDeviceByGroupId(@RequestParam Long groupId) {
		List<Device> devices = deviceService.queryDeviceByGroupId(groupId);
		return Response.SUCCESS().setData(devices);
	}
		
    @ApiOperation(value="绑定设备到个人", notes="")
	@RequestMapping("/assignOwnerForDevice")
    @DmcsController(roleAllowed="administrator", description="绑定设备到用户")
	public Response assignOwnerForDevice(@RequestParam Long userId, @RequestParam Long deviceId) {
    	
		User user = userService.getUserById(userId);
		Device device = deviceService.getDeviceById(deviceId);
		
		if(user == null) {
			return Response.NEW().returnFail(Constants.RC_FAIL_USER_NO_EXIST_CODE, Constants.RC_FAIL_USER_NO_EXIST_MSG, null);
		}
		
    	if(device == null) {
    		return Response.NEW().returnFail(Constants.RC_FAIL_DEVICE_NO_EXIST_CODE, Constants.RC_FAIL_DEVICE_NO_EXIST_MSG, null);
    	}
    	
    	device.setOwner(userId);
    	
    	deviceService.updateDevice(device);

		return Response.SUCCESS().setData(device);
	}
    
    @ApiOperation(value="获得未绑定设备列表", notes="")
	@RequestMapping("/listUnbindDevices")
    @DmcsController(roleAllowed="administrator")
	public Response listUnbindDevices(int page, int size) {
    	
    	List<Device> devices = deviceService.queryUnbindDevices(page, size);
    	
    	return Response.SUCCESS().setData(devices);
    	
    }
    
	
}

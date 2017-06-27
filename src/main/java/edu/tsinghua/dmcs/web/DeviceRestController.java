package edu.tsinghua.dmcs.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.entity.Device;
import edu.tsinghua.dmcs.mapper.DeviceMapper;

@RestController
public class DeviceRestController {

	@Autowired
	DeviceMapper deviceMapper;
	
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
	
}

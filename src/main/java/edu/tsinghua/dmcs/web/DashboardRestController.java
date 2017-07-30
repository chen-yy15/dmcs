package edu.tsinghua.dmcs.web;

import edu.tsinghua.dmcs.entity.UserDeviceDashboard;
import edu.tsinghua.dmcs.service.UserDeviceDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.interceptor.DmcsController;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@RestController
@RequestMapping(value="/dmcs/api/dashboard")
public class DashboardRestController {

	@Autowired
	private UserDeviceDashboardService userDeviceDashboardService;

	@DmcsController(loginRequired=true)
    @ApiOperation(value="获得指定用户设备的参数布局", notes="获得指定用户设备的部局")
	@RequestMapping(value = "/listDeviceLayoutByUser", method = RequestMethod.GET)
	public Response listDeviceLayoutByUser(@RequestParam Long userId, @RequestParam Long deviceId) {
		List<UserDeviceDashboard> udd = userDeviceDashboardService.listDeviceLayoutByUser(userId, deviceId);
		return Response.SUCCESS().setData(udd);
	}

	
	@DmcsController(loginRequired=true, description = "调整指定用户设备的布局")
    @ApiOperation(value="调整指定用户设备的布局", notes="调整指定用户设备的布局")
	@RequestMapping(value = "/updateDeviceLayoutByUser", method = RequestMethod.GET)
	public Response updateDeviceLayoutByUser(@RequestParam Long userId, @RequestParam String layout) {
		// TODO 
		return Response.SUCCESS();
	}
	
	@DmcsController(loginRequired=true)
    @ApiOperation(value="获得指定用户设备的参数数据", notes="获得指定用户设备的参数数据")
	@RequestMapping(value = "/listDeviceParameterValue", method = RequestMethod.GET)
	public Response listDeviceParameterValue(@RequestParam Long userId, @RequestParam Long deviceId) {
		// TODO 
		return Response.SUCCESS();
	}
	
	@DmcsController(loginRequired=true, description = "设置设备数值")
    @ApiOperation(value="设置设备参数数值", notes="设置设备数值")
	@RequestMapping(value = "/setDeviceParameterValue", method = RequestMethod.GET)
	public Response listDeviceParameterValue(@RequestParam Long userId, @RequestParam Long device, @RequestParam Long paramId, @RequestParam Long value) {
		// TODO 
		return Response.SUCCESS();
	}
	
}

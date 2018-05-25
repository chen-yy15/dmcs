package edu.tsinghua.dmcs.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.tsinghua.dmcs.interceptor.DmcsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.tsinghua.dmcs.Constants;
import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.entity.Group;
import edu.tsinghua.dmcs.entity.GroupUserMapping;
import edu.tsinghua.dmcs.entity.User;
import edu.tsinghua.dmcs.service.GroupService;
import edu.tsinghua.dmcs.service.UserService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/dmcs/api/group")
public class GroupRestController {

	private static final Logger logger = LoggerFactory.getLogger(GroupRestController.class);

	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;
	
	@DmcsController
    @ApiOperation(value="查询指定群组是否存在", notes="返回true表示该群组名已存在")
	@RequestMapping(value = "checkGrpExistence", method = RequestMethod.GET)
	public Response checkExistence(@RequestParam String groupName) {
		Group group = groupService.checkExistence(groupName);
		return Response.SUCCESS().returnData(group != null);
	}

	@DmcsController(description="添加组")
    @ApiOperation(value="添加群组", notes="")
	@RequestMapping(value = "/addGroup", method = RequestMethod.GET)
	public Response addGroup(
			@RequestParam String name,
			@RequestParam String description,
			@RequestParam Long owner,
			@RequestParam Integer type,
			@RequestParam String createtime) {
		
		Group g = new Group();
		g.setName(name);
		g.setDescription(description);
		g.setOwner(owner);
		g.setType(type);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sdf.parse(createtime);
		} catch (ParseException e) {
			logger.error("fail to add group ", e);
		}
		g.setCreatetime(d);
		g = groupService.addGroup(g);
		return Response.SUCCESS().returnData(g);
		
	}

	@DmcsController(description = "更新组信息")
    @ApiOperation(value="更新群组信息", notes="")
	@RequestMapping(value = "/updateGroup", method = RequestMethod.GET)
	public Response update(
			@RequestParam Long id,
			@RequestParam String name,
			@RequestParam String description,
			@RequestParam Long owner,
			@RequestParam Integer type,
			@RequestParam String createtime) {
		
		Group g = new Group();
		g.setId(id);
		g.setName(name);
		g.setDescription(description);
		g.setOwner(owner);
		g.setType(type);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sdf.parse(createtime);
		} catch (ParseException e) {
			logger.error("fail to add group ", e);
		}
		g.setCreatetime(d);
		groupService.update(g);
		return Response.SUCCESS().returnData(g);
		
	}

	@DmcsController(description = "添加组成员")
    @ApiOperation(value="为当前用户的指定群增长新成员", notes="")
	@RequestMapping(value = "/addGroupMember", method = RequestMethod.GET)
	public Response addGroupMember(@RequestParam long groupId,
								   @RequestParam long userId) {
    	User u = userService.getUserById(userId);
    	if(u == null) {
    		return Response.NEW().returnFail(Constants.RC_FAIL_USER_NO_EXIST_CODE, Constants.RC_FAIL_USER_NO_EXIST_MSG, null);
    	}
    	Group g = groupService.getGroupById(groupId);
    	if(g == null) {
    		return Response.NEW().returnFail(Constants.RC_FAIL_GROUP_NO_EXIST_CODE, Constants.RC_FAIL_GROUP_NO_EXIST_MSG, null);
    	}
    	
    	GroupUserMapping gum = groupService.addMemberForGroup(groupId, userId, false);
    	
    	return Response.SUCCESS().setData(gum);
    	
    	
    }

    @DmcsController(description = "删除组成员")
    @ApiOperation(value="从指定群组中移除指定用户", notes="")
	@RequestMapping(value = "/removeGroupMember", method = RequestMethod.GET)
	public Response removeGroupMember(@RequestParam long groupId,
									  @RequestParam long userId) {
    	User u = userService.getUserById(userId);
    	if(u == null) {
    		return Response.NEW().returnFail(Constants.RC_FAIL_USER_NO_EXIST_CODE, Constants.RC_FAIL_USER_NO_EXIST_MSG, null);
    	}
    	Group g = groupService.getGroupById(groupId);
    	if(g == null) {
    		return Response.NEW().returnFail(Constants.RC_FAIL_GROUP_NO_EXIST_CODE, Constants.RC_FAIL_GROUP_NO_EXIST_MSG, null);
    	}
    	
    	GroupUserMapping gum = groupService.getGroupUserMapping(groupId, userId);
    	if(gum == null) {
    		return Response.NEW().returnFail(Constants.RC_FAIL_GROUP_USER_NO_EXIST_CODE, Constants.RC_FAIL_GROUP_USER_NO_EXIST_MSG, null);
    	}
    	
    	Integer num = groupService.removeMemberForGroup(groupId, userId);
    	
    	return Response.SUCCESS().setData(num);
    	
    	
    }

	@DmcsController
    @ApiOperation(value="按用户列出所属群组", notes="")
	@RequestMapping(value = "/listGroupByUser", method = RequestMethod.GET)
	public Response listGroupByUser(@RequestParam long userId) {
    	User u = userService.getUserById(userId);
    	if(u == null) {
    		return Response.NEW().returnFail(Constants.RC_FAIL_USER_NO_EXIST_CODE, Constants.RC_FAIL_USER_NO_EXIST_MSG, null);
    	}
    	
    	List<Group> groups = groupService.listGroupByUserId(userId);
    	
    	return Response.SUCCESS().returnData(groups);
    	
    }

}

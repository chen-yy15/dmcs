package edu.tsinghua.dmcs.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping(value="/group")
public class GroupRestController {

	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;
	
    @ApiOperation(value="查询指定群组是否存在", notes="返回true表示该群组名已存在")
	@RequestMapping("checkGrpExistence")
	public Response checkExistence(@RequestParam String groupName) {
		Group group = groupService.checkExistence(groupName);
		return Response.SUCCESS().returnData(group != null);
	}
    
    @ApiOperation(value="添加群组", notes="")
	@RequestMapping("/addGroup")
	public Response addGroup(
			@RequestParam Long id,
			@RequestParam String name,
			@RequestParam String description,
			@RequestParam Long owner,
			@RequestParam Integer type,
			@RequestParam Date createtime) {
		
		Group g = new Group();
		g.setName(name);
		g.setDescription(description);
		g.setOwner(owner);
		g.setType(type);
		g.setCreatetime(new Date());
		g = groupService.addGroup(g);
		return Response.SUCCESS().returnData(g);
		
	}
	

    @ApiOperation(value="更新群组信息", notes="")
	@RequestMapping("/updateGroup")
	public Response update(
			@RequestParam Long id,
			@RequestParam String name,
			@RequestParam String description,
			@RequestParam Long owner,
			@RequestParam Integer type,
			@RequestParam Date createtime) {
		
		Group g = new Group();
		g.setId(id);
		g.setName(name);
		g.setDescription(description);
		g.setOwner(owner);
		g.setType(type);
		g.setCreatetime(new Date());
		groupService.update(g);
		return Response.SUCCESS().returnData(g);
		
	}
    
    @ApiOperation(value="为当前用户的指定群增长新成员", notes="")
	@RequestMapping("/addGroupMember")
	public Response addGroupMember(Long groupId, Long userId) {
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

}

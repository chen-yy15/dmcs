package edu.tsinghua.dmcs.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.entity.Group;
import edu.tsinghua.dmcs.mapper.GroupMapper;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/group")
public class GroupRestController {

	
	@Autowired
	private GroupMapper groupMapper;
	
    @ApiOperation(value="查询指定群组是否存在", notes="")
	@RequestMapping("checkGrpExistence")
	public Response checkExistence(@RequestParam String groupName) {
		Group group = groupMapper.selectByGroupName(groupName);
		return Response.returnData(group != null);
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
		groupMapper.insert(g);
		return Response.returnData(g);
		
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
		groupMapper.updateByPrimaryKey(g);
		return Response.returnData(g);
		
	}

}

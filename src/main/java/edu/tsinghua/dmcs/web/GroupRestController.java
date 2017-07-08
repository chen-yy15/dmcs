package edu.tsinghua.dmcs.web;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.tsinghua.dmcs.Response;
import edu.tsinghua.dmcs.entity.Group;
import edu.tsinghua.dmcs.mapper.GroupMapper;

@RestController
public class GroupRestController {

	
	@Autowired
	private GroupMapper groupMapper;
	
	@RequestMapping("checkGrpExistence")
	public Response checkExistence(@RequestParam String groupName) {
		Group group = groupMapper.selectByGroupName(groupName);
		return Response.returnData(group != null);
	}
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

package edu.tsinghua.dmcs.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import edu.tsinghua.dmcs.entity.Group;
import edu.tsinghua.dmcs.mapper.GroupMapper;
import edu.tsinghua.dmcs.service.GroupService;

@Component
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private GroupMapper groupMapper;
	
	public Group checkExistence(@RequestParam String groupName) {
		Group group = groupMapper.selectByGroupName(groupName);
		return group;
	}
    
	public Group addGroup(
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
		return g;
		
	}
	
	public Group addGroup(Group group) {
		groupMapper.insert(group);
		return group;
		
	}
	

	public Group update(
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
		return g;
		
	}
	
	public Integer update(Group group) {
		int num = groupMapper.updateByPrimaryKey(group);
		return num;
		
	}

}

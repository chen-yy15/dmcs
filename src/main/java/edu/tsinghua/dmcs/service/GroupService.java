package edu.tsinghua.dmcs.service;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestParam;

import edu.tsinghua.dmcs.entity.Group;

public interface GroupService {
	
	public Group checkExistence(@RequestParam String groupName);
    
	public Group addGroup(Group group);
	
	public Integer update(Group group);

	public Group update(
			@RequestParam Long id,
			@RequestParam String name,
			@RequestParam String description,
			@RequestParam Long owner,
			@RequestParam Integer type,
			@RequestParam Date createtime);

}

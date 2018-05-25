package edu.tsinghua.dmcs.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import edu.tsinghua.dmcs.entity.Group;
import edu.tsinghua.dmcs.entity.GroupDeviceMapping;
import edu.tsinghua.dmcs.entity.GroupUserMapping;
import edu.tsinghua.dmcs.mapper.GroupDeviceMappingMapper;
import edu.tsinghua.dmcs.mapper.GroupMapper;
import edu.tsinghua.dmcs.mapper.GroupUserMappingMapper;
import edu.tsinghua.dmcs.service.GroupService;

@Component
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private GroupMapper groupMapper;
	
	@Autowired
	private GroupDeviceMappingMapper groupDeviceMappingMapper;
	
	@Autowired
	private GroupUserMappingMapper groupUserMappingMapper;
	
	public Group checkExistence(@RequestParam String groupName) {
		Group group = groupMapper.selectByGroupName(groupName);
		return group;
	}
    
	@Transactional
	public Group addGroup(
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
	
	@Transactional
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
	
	public Group getGroupById(Long groupId) {
		Group g = groupMapper.selectByPrimaryKey(groupId);
		return g;
	}
	
	public GroupUserMapping addMemberForGroup(Long groupId, Long userId, boolean isOwner) {
		GroupUserMapping gum = new GroupUserMapping();
		gum.setGroupId(groupId);
		gum.setUserId(userId);
		gum.setIsAdmin(Integer.valueOf(0).byteValue());
		gum.setType(0);
		groupUserMappingMapper.insert(gum);
		return gum;
	}
	
	public GroupDeviceMapping addDeviceForGroup(Long groupId, Long deviceId) {
		
		GroupDeviceMapping gdm = new GroupDeviceMapping();
		gdm.setDeviceId(deviceId);
		gdm.setGroupId(groupId);
		gdm.setType(0);
		groupDeviceMappingMapper.insert(gdm);
		
		return gdm;
	}

	public GroupDeviceMapping getGroupDeviceMapping(Long groupId, Long deviceId) {
		// TODO Auto-generated method stub
		return null;
	}

	public GroupUserMapping getGroupUserMapping(Long groupId, Long userId) {
		// TODO Auto-generated method stub
		return groupUserMappingMapper.getGroupUserMapping(groupId, userId);
	}

	public Integer removeMemberForGroup(Long groupId, Long userId) {
		GroupUserMapping gum = groupUserMappingMapper.getGroupUserMapping(groupId, userId);
		return groupUserMappingMapper.deleteByPrimaryKey(gum.getId());
	}

	public List<Group> listGroupByUserId(Long userId) {

		return groupUserMappingMapper.listGroupByUserId(userId);
	}
	
	

}

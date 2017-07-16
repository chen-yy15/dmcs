package edu.tsinghua.dmcs.service;

import java.util.Date;
import java.util.List;

import edu.tsinghua.dmcs.entity.Group;
import edu.tsinghua.dmcs.entity.GroupDeviceMapping;
import edu.tsinghua.dmcs.entity.GroupUserMapping;

public interface GroupService {
	
	public Group checkExistence( String groupName);
    
	public Group addGroup(Group group);
	
	public Integer update(Group group);

	public Group update(
			 Long id,
			 String name,
			 String description,
			 Long owner,
			 Integer type,
			 Date createtime);
	
	public Group getGroupById(Long groupId);
	
	public GroupUserMapping addMemberForGroup(Long groupId, Long userId, boolean isOwner);
	
	public GroupDeviceMapping addDeviceForGroup(Long groupId, Long deviceId);
	
	public GroupDeviceMapping getGroupDeviceMapping(Long groupId, Long deviceId);
	
	public GroupUserMapping getGroupUserMapping(Long groupId, Long userId);
	
	public GroupUserMapping removeMemberForGroup(Long groupId, Long userId);
	
	public List<Group> listGroupByUserId(Long userId); 

}

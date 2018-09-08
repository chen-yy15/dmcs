package edu.tsinghua.dmcs.service.impl;

import edu.tsinghua.dmcs.entity.AdminGroup;
import edu.tsinghua.dmcs.entity.AdminGroupUser;
import edu.tsinghua.dmcs.mapper.AdminGroupMapper;
import edu.tsinghua.dmcs.mapper.AdminGroupUserMapper;
import edu.tsinghua.dmcs.service.AdminGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by caizj on 18-9-6.
 */
@Component
public class AdminGroupServiceImpl implements AdminGroupService {

    @Autowired
    AdminGroupMapper adminGroupMapper;
    @Autowired
    AdminGroupUserMapper adminGroupUserMapper;

    public int addAdminuser(AdminGroup adminGroup) {
        int num = 0;
        if(adminGroup!=null){
            num = adminGroupMapper.insert(adminGroup);
        }
        return num;
    }

    public int updateAdminuser (AdminGroup adminGroup) {
        int num = 0;
        if(adminGroup!=null){
            num = adminGroupMapper.updateByuserid(adminGroup);
        }
        return  num;
    };

    public int deleteByuserid(String userid) {
        return adminGroupMapper.deleteByuserid(userid);
    }
    public AdminGroup checkexistUser(String userid) {
        if(userid ==null)
            return null;
        return adminGroupMapper.selectByuserid(userid);
    }

    public AdminGroup selectUser(String userid) { return adminGroupMapper.selectByuserid(userid); }

    public int checkifhost(String userid){
        int num = 0 ;
        AdminGroup adminGroup = adminGroupMapper.selectByuserid(userid);
        if(adminGroup.getAuthorityNumber()==1023){
            num = 1;
        }
        return num;
    }
    public List<AdminGroupUser> selectadmingroup() {
        return adminGroupUserMapper.queryselect();
    }
}

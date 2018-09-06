package edu.tsinghua.dmcs.service.impl;

import edu.tsinghua.dmcs.entity.AdminGroup;
import edu.tsinghua.dmcs.mapper.AdminGroupMapper;
import edu.tsinghua.dmcs.service.AdminGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by caizj on 18-9-6.
 */
@Component
public class AdminGroupServiceImpl implements AdminGroupService {

    @Autowired
    AdminGroupMapper adminGroupMapper;

    public int addAdminuser(AdminGroup adminGroup) {
        int num = 0;
        if(adminGroup!=null){
            num = adminGroupMapper.insert(adminGroup);
        }
        return num;
    }

    public int deleteAdminuser(int id) {
        int num = adminGroupMapper.deleteByPrimaryKey(id);
        return num;
    }

    public int updateAdminuser (AdminGroup adminGroup) {
        int num = 0;
        if(adminGroup!=null){
            num = adminGroupMapper.updateByuserid(adminGroup);
        }
        return  num;
    };

    public  AdminGroup getuserbyid(int id) {
        return adminGroupMapper.selectByPrimaryKey(id);
    }

    public AdminGroup getuserbyUserid(String userid) {
        return adminGroupMapper.selectByuserid(userid);
    }
}

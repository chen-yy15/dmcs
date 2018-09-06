package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.AdminGroup;
import edu.tsinghua.dmcs.entity.AdminGroupUser;

import java.util.List;

/**
 * Created by caizj on 18-9-6.
 */
public interface AdminGroupService {

    public int addAdminuser(AdminGroup adminGroup);

    public int updateAdminuser (AdminGroup adminGroup);

    public int deleteByuserid(String userid);

    public AdminGroup checkexistUser(String userid);

    public AdminGroup selectUser(String userid);

    public int checkifhost(String userid);

    public List<AdminGroupUser> selectadmingroup();
}

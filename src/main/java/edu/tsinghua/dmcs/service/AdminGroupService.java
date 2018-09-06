package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.AdminGroup;

/**
 * Created by caizj on 18-9-6.
 */
public interface AdminGroupService {

    public int addAdminuser(AdminGroup adminGroup);

    public int deleteAdminuser(int id);

    public int updateAdminuser (AdminGroup adminGroup);

    public  AdminGroup getuserbyid(int id);

    public AdminGroup getuserbyUserid(String userid);

    //public List<AdminGroup> getadmingroup();
}

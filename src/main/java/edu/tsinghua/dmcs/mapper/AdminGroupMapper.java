package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.AdminGroup;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by caizj on 18-9-6.
 */
@Mapper
public interface AdminGroupMapper {

    int deleteByuserid(String userid);

    int insert(AdminGroup record);

    int updateByuserid(AdminGroup record);

    AdminGroup selectByuserid(String userid);

}

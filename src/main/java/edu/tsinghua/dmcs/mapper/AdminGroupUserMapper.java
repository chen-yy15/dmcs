package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.AdminGroupUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by caizj on 18-9-6.
 */
@Mapper
public interface AdminGroupUserMapper {
    List<AdminGroupUser> queryselect();
}

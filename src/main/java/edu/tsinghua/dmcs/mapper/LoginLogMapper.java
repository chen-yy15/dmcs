package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by caizj on 18-9-21.
 */
@Mapper
public interface LoginLogMapper {

    int addLog(LoginLog loginLog);

    int deleteLog(Long logid);

    int deleteByUserid(String userid);

    LoginLog getLog(Long logid);

    List<LoginLog> queryGetLog(String userid);
}

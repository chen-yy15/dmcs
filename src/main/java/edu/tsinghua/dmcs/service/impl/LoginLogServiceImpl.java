package edu.tsinghua.dmcs.service.impl;

import edu.tsinghua.dmcs.entity.LoginLog;
import edu.tsinghua.dmcs.mapper.LoginLogMapper;
import edu.tsinghua.dmcs.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by caizj on 18-9-21.
 */
@Component
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    LoginLogMapper loginLogMapper;

    public int AddLog(LoginLog loginLog){
        int num = 0;
        if(loginLog!=null){
            num = loginLogMapper.addLog(loginLog);
        }
        return num;
    }

    public int DeleteLog(Long logid){
        int num = 0;
        num = loginLogMapper.deleteLog(logid);
        return num;
    }

    public int DeleteByUserid(String userid){
        int num = 0;
        num = loginLogMapper.deleteByUserid(userid);
        return num;
    }

    public LoginLog GetLog(Long logid){
        return loginLogMapper.getLog(logid);
    }

    public List<LoginLog> QueryLog(String userid){
        return loginLogMapper.queryGetLog(userid);
    }
}

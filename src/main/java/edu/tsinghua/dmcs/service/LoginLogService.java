package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.LoginLog;

import java.util.List;

/**
 * Created by caizj on 18-9-21.
 */
public interface LoginLogService {

    public int AddLog(LoginLog loginLog);

    public int DeleteLog(Long logid);

    public int DeleteByUserid(String userid);

    public int UpdateLog(LoginLog loginLog);

    public LoginLog GetLog(Long logid);

    public LoginLog GetLogMax(LoginLog loginLog);

    public List<LoginLog> QueryLog(String userid);
}

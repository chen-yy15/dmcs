package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.SysOperationLog;

import java.util.List;

/**
 * Created by caizj on 18-10-10.
 */
public interface SysOperationService {

    public int DeleteSysOperation(Long logid);

    public int AddOperation(SysOperationLog sysOperationLog);

    public int UpdateOperation(SysOperationLog sysOperationLog);

    public SysOperationLog SelectOperation(Long logid);

    public List<SysOperationLog> GetOperation();
}

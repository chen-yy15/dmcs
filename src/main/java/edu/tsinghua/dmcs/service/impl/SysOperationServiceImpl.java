package edu.tsinghua.dmcs.service.impl;

import edu.tsinghua.dmcs.entity.SysOperationLog;
import edu.tsinghua.dmcs.mapper.SysOperationMapper;
import edu.tsinghua.dmcs.service.SysOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by caizj on 18-10-10.
 */
@Component
public class SysOperationServiceImpl implements SysOperationService{

    @Autowired
    SysOperationMapper sysOperationMapper;

    public int DeleteSysOperation(Long logid){
        int num = 0;
        num = sysOperationMapper.deleteById(logid);
        return num;
    }

    public int AddOperation(SysOperationLog sysOperationLog){
        int num = 0;
        if(sysOperationLog!=null){
            num = sysOperationMapper.addOperation(sysOperationLog);
        }
        return num;
    }

    public int UpdateOperation(SysOperationLog sysOperationLog){
        int num = 0;
        if(sysOperationLog!=null){
            num = sysOperationMapper.updateOperation(sysOperationLog);
        }
        return num;
    }

    public SysOperationLog SelectOperation(Long logid){
        return sysOperationMapper.selectById(logid);
    }

    public List<SysOperationLog> GetOperation(){
        return sysOperationMapper.getOperation();
    }

}

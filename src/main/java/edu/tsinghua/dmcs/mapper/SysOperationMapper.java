package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.SysOperationLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by caizj on 18-10-9.
 */
@Mapper
public interface SysOperationMapper {

    int deleteById(Long logid);

    int addOperation(SysOperationLog sysOperationLog);

    int updateOperation(SysOperationLog sysOperationLog);

    SysOperationLog selectById(Long logid);

    List<SysOperationLog> getOperation();
}

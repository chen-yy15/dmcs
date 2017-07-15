package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.Trace;

public interface TraceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Trace record);

    int insertSelective(Trace record);

    Trace selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Trace record);

    int updateByPrimaryKey(Trace record);
}
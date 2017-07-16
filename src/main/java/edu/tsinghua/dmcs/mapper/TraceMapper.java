package edu.tsinghua.dmcs.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.tsinghua.dmcs.entity.Trace;

@Mapper
public interface TraceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Trace record);

    int insertSelective(Trace record);

    Trace selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Trace record);

    int updateByPrimaryKey(Trace record);
}
package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.FileWindowModule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by caizj on 18-10-9.
 */
@Mapper
public interface FileWindowMapper {

    int deleteById(Long createid);

    int addFileWindow(FileWindowModule fileWindowModule);

    int updateFileWindow(FileWindowModule fileWindowModule);

    int countByModuleId(Integer moduleid);

    FileWindowModule selectById(Long createid);

    List<FileWindowModule> selectByModuleId(Integer moduleid);

    List<FileWindowModule> selectPageList(Integer moduleid); /* 获取对应的moduelid下对应的文章即可 */

    List<FileWindowModule> selectCommonFileWindow();

}

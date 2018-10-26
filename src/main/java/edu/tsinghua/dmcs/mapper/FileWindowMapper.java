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

    int deleteFile_image(String fileimage);

    int addFileWindow(FileWindowModule fileWindowModule);

    int updateFileWindow(FileWindowModule fileWindowModule);

    FileWindowModule selectById(Long createid);

    List<FileWindowModule> selectByModuleId(Integer moduleid);

    List<FileWindowModule> selectCommonFileWindow();

    FileWindowModule selectFile_image(String fileimage);
}

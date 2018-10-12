package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.FileWindowModule;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by caizj on 18-10-9.
 */
@Mapper
public interface FileWindowMapper {

    int deleteById(Long createid);

    int deleteFile_image(String file_image);

    int addFileWindow(FileWindowModule fileWindowModule);

    int updateFileWindow(FileWindowModule fileWindowModule);

    FileWindowModule selectById(Long createid);

    FileWindowModule selectFile_image(String file_image);
}

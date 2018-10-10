package edu.tsinghua.dmcs.mapper;

import edu.tsinghua.dmcs.entity.FileInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by caizj on 18-10-9.
 */
@Mapper
public interface FileInfoMapper {

    int addFile(FileInfo fileInfo);

    int deleteFile(Long fileid);

    int updateFile(FileInfo fileInfo);

    FileInfo selectByFileid(Long fileid);

    List<FileInfo> getFile();
}

package edu.tsinghua.dmcs.service.impl;

import edu.tsinghua.dmcs.entity.FileInfo;
import edu.tsinghua.dmcs.mapper.FileInfoMapper;
import edu.tsinghua.dmcs.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by caizj on 18-10-10.
 */
@Component
public class FileInfoServiceImpl implements FileInfoService{

    @Autowired
    private FileInfoMapper fileInfoMapper;

    public int AddFile(FileInfo fileInfo){
        int num = 0;
        if(fileInfo!=null) {
            num = fileInfoMapper.addFile(fileInfo);
        }
        return num;
    }

    public int DeleteFile(Long fileid){
        int num = 0;
        num = fileInfoMapper.deleteFile(fileid);
        return num;
    }

    public int UpdateFileInfo(FileInfo fileInfo){
        int num = 0;
        if(fileInfo!=null){
            num = fileInfoMapper.updateFile(fileInfo);
        }
        return num;
    }

    public int UpdateFileViewed(FileInfo fileInfo){
        int num = 0;
        if(fileInfo!=null){
            num = fileInfoMapper.updateFile(fileInfo);
        }
        return num;
    }

    public FileInfo SelectFileInfo(Long fileid){
        return fileInfoMapper.selectByFileid(fileid);
    }

    public List<FileInfo> GetFileInfo(){
        return fileInfoMapper.getFile();
    }
}

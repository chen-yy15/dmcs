package edu.tsinghua.dmcs.service.impl;

import edu.tsinghua.dmcs.entity.FileWindowModule;
import edu.tsinghua.dmcs.mapper.FileWindowMapper;
import edu.tsinghua.dmcs.service.FileWindowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by caizj on 18-10-10.
 */
@Component
public class FileWindowServiceImpl implements FileWindowService{

    @Autowired
    FileWindowMapper fileWindowMapper;

    public int DeleteFileWindow(Long createid){
        int num = 0;
        num = fileWindowMapper.deleteById(createid);
        return num;
    }

    public int AddFileWindow(FileWindowModule fileWindowModule){
        int num = 0;
        if(fileWindowModule!=null)
           num = fileWindowMapper.addFileWindow(fileWindowModule);
        return num;
    }

    public int UpdateFileWindow(FileWindowModule fileWindowModule){
        int num = 0;
        if(fileWindowModule!=null){
            num = fileWindowMapper.updateFileWindow(fileWindowModule);
        }
        return num;
    }

    public FileWindowModule SelectFileWindow(Long createid){
        return fileWindowMapper.selectById(createid);
    }

    public FileWindowModule ExistFileWindow(String file_image){
        return fileWindowMapper.selectFile_image(file_image);
    }
}

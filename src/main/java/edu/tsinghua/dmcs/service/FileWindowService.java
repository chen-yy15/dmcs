package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.FileWindowModule;

import java.util.List;

/**
 * Created by caizj on 18-10-10.
 */
public interface FileWindowService {

    public int DeleteFileWindow(Long createid);

    public int AddFileWindow(FileWindowModule fileWindowModule);

    public int UpdateFileWindow(FileWindowModule fileWindowModule);

    public int GetNumberOfModuleid(Integer moduleid);

    public FileWindowModule SelectFileWindow(Long createid);

    public List<FileWindowModule> SelectFileWindowByModule(Integer moduleid);

    public List<FileWindowModule> NoSelectFileWindow();

    public List<FileWindowModule>  SelectPageListByModule(Integer moduleid);

    public List<FileWindowModule>  GetFirstPageList();
}

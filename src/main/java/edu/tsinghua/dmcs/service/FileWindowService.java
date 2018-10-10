package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.FileWindowModule;

/**
 * Created by caizj on 18-10-10.
 */
public interface FileWindowService {

    public int DeleteFileWindow(Long createid);

    public int AddFileWindow(FileWindowModule fileWindowModule);

    public int UpdateFileWindow(FileWindowModule fileWindowModule);

    public FileWindowModule SelectFileWindow(Long createid);
}

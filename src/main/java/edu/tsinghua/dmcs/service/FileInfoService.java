package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.FileInfo;

import java.util.List;

/**
 * Created by caizj on 18-10-10.
 */
public interface FileInfoService {
    public int AddFile(FileInfo fileInfo);

    public int DeleteFile(String fileid);

    public int UpdateFileInfo(FileInfo fileInfo);

    public int UpdateFileViewed(FileInfo fileInfo);

    public FileInfo SelectFileInfo(String fileid);

    public List<FileInfo> GetFileInfo();
}

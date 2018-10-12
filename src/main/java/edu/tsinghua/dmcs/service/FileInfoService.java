package edu.tsinghua.dmcs.service;

import edu.tsinghua.dmcs.entity.FileInfo;

import java.util.List;

/**
 * Created by caizj on 18-10-10.
 */
public interface FileInfoService {
    public int AddFile(FileInfo fileInfo);

    public int DeleteFile(Long fileid);

    public int UpdateFileInfo(FileInfo fileInfo);

    public int UpdateFileViewed(FileInfo fileInfo);

    public FileInfo SelectFileInfo(Long fileid);

    public List<FileInfo> SelectFile(String filetype);

    public List<FileInfo> GetFileInfo();
}

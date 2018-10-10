package edu.tsinghua.dmcs.entity;

import java.util.Date;

/**
 * Created by caizj on 18-10-9.
 */
public class FileInfo {
    private  String fileid ;

    private String  filename ;

    private String  suffixname ;

    private String  filetype ;

    private String  filedescription ;

    private String  filesrc ;

    private Date insertTime ;

    private String insertUser ;

    public String getFileid() {return fileid;}

    public void  setFileid(String fileid) {this.fileid=fileid;}

    public String getFilename() {return filename;}

    public void setFilename(String filename) {this.filename = filename;}

    public String getSuffixname(){return suffixname;}

    public void setSuffixname(String suffixname) {this.suffixname = suffixname;}

    public String getFiletype(){return filetype;}

    public void setFiletype(String filetype) {this.filetype = filetype;}

    public String getFiledescription(){return filedescription;}

    public void setFiledescription(String filedescription) {this.filedescription= filedescription;}

    public String getFilesrc() {return filesrc;}

    public void setFilesrc(String filesrc) {this.filesrc = filesrc;}

    public Date getInsertTime(){return insertTime;}

    public void setInsertTime(Date insertTime) {this.insertTime= insertTime;}

    public String getInsertUser(){return insertUser;}

    public void setInsertUser(String insertUser) {this.insertUser = insertUser;}
}

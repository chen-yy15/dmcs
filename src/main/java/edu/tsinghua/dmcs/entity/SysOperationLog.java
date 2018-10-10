package edu.tsinghua.dmcs.entity;

import java.util.Date;

/**
 * Created by caizj on 18-10-9.
 */
public class SysOperationLog {
    private Long logid;

    private String userid;

    private String fileid;

    private String filefullname;

    private Date  operationtime;

    private String opDesc;

    public Long getLogid() {return logid;}

    public void setLogid(Long logid) {this.logid = logid;}

    public String getUserid() {return userid;}

    public void setUserid(String userid) {this.userid = userid;}

    public String getFileid(){return fileid;}

    public void setFileid(String fileid) {this.fileid = fileid;}

    public String getFilefullname(){return filefullname;}

    public void setFilefullname(String filefullname) {this.filefullname = filefullname;}

    public Date getOperationtime(){return operationtime;}

    public void setOperationtime(Date operationtime) {this.operationtime = operationtime;}

    public String getOpDesc(){return opDesc;}

    public void setOpDesc(String opDesc) {this.opDesc = opDesc;}
}

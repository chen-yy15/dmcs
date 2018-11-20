package edu.tsinghua.dmcs.entity;

import java.util.Date;

/**
 * Created by caizj on 18-10-9.
 */
public class WebInformation {
    private Long  infid;

    private String inftxt;

    private String viewed;

    private String linksrc;

    private String inserUser;

    private Date insertTime;

    private Date outTime;

    public Long getInfid(){return infid;}

    public void setInfid(Long infid) {this.infid = infid;}

    public String getInftxt(){return inftxt;}

    public void setInftxt(String inftxt) {this.inftxt = inftxt;}

    public String getViewed() {return viewed;}

    public void setViewed(String viewed) {this.viewed = viewed;}

    public String getLinksrc() {return linksrc;}

    public void setLinksrc(String linksrc) {this.linksrc=linksrc;}

    public String getInserUser() {return inserUser;}

    public void setInserUser(String inserUser) {this.inserUser = inserUser;}

    public Date getInsertTime(){return insertTime;}

    public void setInsertTime(Date insertTime) {this.insertTime = insertTime;}

    public Date getOutTime(){return outTime;}

    public void setOutTime(Date outTime) {this.outTime = outTime;}

}

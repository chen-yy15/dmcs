package edu.tsinghua.dmcs.entity;

import java.util.Date;

/**
 * Created by caizj on 18-9-21.
 */
public class LoginLog {
    private Long logid;

    private String userid;

    private String loginip;

    private Date logintime;

    private Date loginouttime;

    private String loginoutway;

    public Long getLogid() {return logid;}

    public void setLogid(Long logid) {this.logid=logid;}

    public String getUserid() {return userid;}

    public void setUserid(String userid) {this.userid=userid;}

    public String getLoginip() {return loginip;}

    public void setLoginip(String loginip) {this.loginip = loginip;}

    public Date getLogintime() {return logintime;}

    public void setLogintime(Date logintime) {this.logintime = logintime;}

    public Date getLoginouttime() {return loginouttime;}

    public void setLoginouttime(Date loginouttime) {this.loginouttime=loginouttime;}

    public String getLoginoutway() {return loginoutway;}

    public void setLoginoutway(String loginoutway) {this.loginoutway = loginoutway;}
}

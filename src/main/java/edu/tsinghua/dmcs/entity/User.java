package edu.tsinghua.dmcs.entity;

import java.util.Date;

public class User {
    private Long id;

    private String userid;

    private String username;

    private String currentAuthority;

    private String password;

    private String usersex;

    private String realname;

    private String alias;

    private String avatar;

    private String userEmail;

    private String userEmail_1;

    private String userTelephone;

    private String userTelephone_1;

    private String userworkPlace;

    private String userWeixin;

    private String userQq;

    private Date regtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentAuthority() { return currentAuthority; }

    public void setCurrentAuthority(String currentAuthority) {this.currentAuthority = currentAuthority;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getUsersex() { return usersex;}

    public void setUsersex(String usersex) {this.usersex = usersex; }

    public String getRealname() {return realname;}

    public void setRealname(String realname) {this.realname = realname;}

    public String getAlias() {return alias;}

    public void setAlias(String alias) {this.alias = alias;}

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {this.avatar = avatar;}

    public String getUserEmail() { return userEmail;}

    public void setUserEmail(String userEmail){this.userEmail = userEmail;}

    public String getUserEmail_1() {return userEmail_1;}

    public void  setUserEmail_1(String userEmail_1) {this.userEmail_1=userEmail_1;}

    public String getUserTelephone() {return userTelephone;}

    public void setUserTelephone(String userTelephone) {this.userTelephone = userTelephone;}

    public String getUserTelephone_1() {return userTelephone_1;}

    public void setUserTelephone_1(String userTelephone_1) {this.userTelephone_1 = userTelephone_1;}

    public String getUserworkPlace() {return userworkPlace;}

    public void setUserworkPlace(String userworkPlace) {this.userworkPlace = userworkPlace;}

    public String getUserWeixin () {return userWeixin;}

    public void setUserWeixin(String weixin) {this.userWeixin = this.userWeixin;}

    public String getUserQq () {return userQq;}

    public void setUserQq(String userQq){this.userQq = userQq;}

    public Date getRegtime() {
        return regtime;
    }

    public void setRegtime(Date regtime) {
        this.regtime = regtime;
    }
}